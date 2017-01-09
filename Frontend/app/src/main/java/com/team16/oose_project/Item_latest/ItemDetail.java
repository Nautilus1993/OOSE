package com.team16.oose_project.Item_latest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.team16.oose_project.Item_latest.*;
import com.team16.oose_project.MyAccount.UserProfile;
import com.team16.oose_project.R;
import com.team16.oose_project.core.GuestHomePage;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.team16.oose_project.core.GuestHomePage.userId;

public class ItemDetail extends AppCompatActivity {
    ImageView ivImageView;
    TextView tvItemName;
    TextView tvItemPrice;
    TextView tvIsDelivery;
    TextView tvAddress;
    TextView tvDescription;
    TextView tvAvailableInterval;
    TextView tvCondition;
    Button tvButton;
    String like;
    String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        Intent intent = getIntent();
        String selectedItemId = intent.getStringExtra("itemId");
        itemId = selectedItemId;

        ivImageView = (ImageView) findViewById(R.id.itemImage);
        tvItemName = (TextView) findViewById(R.id.itemName);
        tvItemPrice = (TextView) findViewById(R.id.itemPrice);
        tvIsDelivery = (TextView) findViewById(R.id.isDelivery);
        tvAddress = (TextView) findViewById(R.id.address);
        tvDescription = (TextView) findViewById(R.id.description);
        tvAvailableInterval = (TextView) findViewById(R.id.availableInterval);
        tvCondition = (TextView) findViewById(R.id.condition);
        tvButton = (Button) findViewById(R.id.itemStar);


        // call AsynTask to perform network operation on separate thread
        // this "http://10.0.2.2:4567/" is for GenyMotion
        // new HttpAsyncTask().execute("http://10.0.3.2:4567/itemdetail/1");

        HttpAsyncTask httpAsyncTask = new HttpAsyncTask();
        try {
            String reslut = httpAsyncTask.execute("http://10.0.3.2:4567/itemdetail/"+userId+"/"+selectedItemId).get();

            JSONObject jsonResult = new JSONObject(reslut);

            tvItemName.setText((String) jsonResult.get("name"));
            tvItemPrice.setText((String) jsonResult.get("price") + " USD");
            tvAddress.setText((String) jsonResult.get("pickUpAddress"));
            tvDescription.setText((String) jsonResult.get("description"));
            tvCondition.setText("condition: " + (String) jsonResult.get("condition"));

            if ((String)jsonResult.get("imgPath") != null && ((String)jsonResult.get("imgPath")).length() != 0) {
                Picasso.with(this).load((String) jsonResult.get("imgPath")).into(ivImageView);
            } else {
                Picasso.with(this).load("http://www.swimport.com/plates/whiteblank.jpg").into(ivImageView);
            }

            // get item avialable time interval
            String avialableInterval = "";
            String start = (String) jsonResult.get("avialableDate");
            String end = (String) jsonResult.get("expireDate");

            if (!start.equals("") && !end.equals("")) {
                avialableInterval = "Avialable from " + start + " to " + end;
            }
            tvAvailableInterval.setText(avialableInterval);

            if (String.valueOf(jsonResult.get("isDeliver")).equals("1")) {
                tvIsDelivery.setText("Deliver");
            } else {
                tvIsDelivery.setText("Not Deliver");
            }

            like = String.valueOf(jsonResult.get("like"));

            if (String.valueOf(jsonResult.get("like")).equals("1")) {
                tvButton.setBackgroundResource(R.drawable.item_starred);
            } else {
                tvButton.setBackgroundResource(R.drawable.item_star);
            }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream

            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
            }
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    // helper method to convert input stream to string
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            System.out.println("url: " + urls[0]);
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
        }
    }

    public void itemSubscribe (View v) {
        SubscribeTask subscribeTask = new SubscribeTask();
        subscribeTask.execute("http://10.0.3.2:4567/Subscribe", userId, getIntent().getStringExtra("itemId"));
        Toast.makeText(this, "Successfully subscribed to this category",
                Toast.LENGTH_SHORT).show();
    }

    public void likeThisItem (View view) throws ExecutionException, InterruptedException{
        if (like.equals("1")) {
            tvButton.setBackgroundResource(R.drawable.item_star);
            UpdateWishListTask updateWishListTask = new UpdateWishListTask();
            updateWishListTask.execute("http://10.0.3.2:4567/itemdetail/dislike/"+userId+"/"+itemId);
            like = "0";
        } else {
            tvButton.setBackgroundResource(R.drawable.item_starred);
            UpdateWishListTask updateWishListTask = new UpdateWishListTask();
            updateWishListTask.execute("http://10.0.3.2:4567/itemdetail/like/"+userId+"/"+itemId);
            like = "1";
        }
    }
}