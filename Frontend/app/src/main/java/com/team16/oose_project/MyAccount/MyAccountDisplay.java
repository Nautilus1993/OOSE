package com.team16.oose_project.MyAccount;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.team16.oose_project.Image.UserPhotoDisplayTask;
import com.team16.oose_project.R;
import com.team16.oose_project.Subcategory.Balcony;
import com.team16.oose_project.core.GuestHomePage;
import com.team16.oose_project.sellingList.NewItemActivity;
import com.team16.oose_project.sellingList.SellingListDisplayActivity;
import com.team16.oose_project.wishlist.WishList;

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
import java.util.concurrent.ExecutionException;

import static com.team16.oose_project.core.GuestHomePage.userId;


public class MyAccountDisplay extends AppCompatActivity {
    ImageView a_image;
    TextView a_username;
    TextView a_email;
    TextView a_phone;
    TextView a_address;
    TextView a_city;
    TextView a_state;
    TextView a_zipcode;
    Button updatePost;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_display);

        // get reference to the views
        a_image = (ImageView) findViewById(R.id.userimage);
        a_username = (TextView) findViewById(R.id.username);
        a_email = (TextView) findViewById(R.id.email);
        a_phone = (TextView) findViewById(R.id.phone);
        a_address = (TextView) findViewById(R.id.address);
        a_city = (TextView) findViewById(R.id.city);
        a_state = (TextView) findViewById(R.id.state);
        a_zipcode = (TextView) findViewById(R.id.zipcode);
        updatePost = (Button) findViewById(R.id.updateButton);

        //init bottomBar, TODO refactor to tool class
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.getRootView().findViewById(R.id.tab_none).setVisibility(View.GONE);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId == R.id.tab_me) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Intent myProfile = new Intent(MyAccountDisplay.this, MyAccountDisplay.class);
                    startActivity(myProfile);
                    System.out.println("***********Me tab selected");
                }
                if(tabId == R.id.tab_selling){
                    Intent sellingList = new Intent(MyAccountDisplay.this,
                            SellingListDisplayActivity.class);
                    startActivity(sellingList);
                    System.out.println("*********** Selling tab selected");
                }
                if(tabId == R.id.tab_post){
                    Intent post = new Intent(MyAccountDisplay.this,
                            NewItemActivity.class);
                    startActivity(post);
                    System.out.println("*********** Post tab selected");
                }
                if(tabId == R.id.tab_home){
                    Intent guestHomePage = new Intent(MyAccountDisplay.this, GuestHomePage.class);
                    startActivity(guestHomePage);
                    System.out.println("*********** Home tab selected");
                }
                if(tabId == R.id.tab_wishes){
                    Intent wishList = new Intent(MyAccountDisplay.this, WishList.class);
                    startActivity(wishList);
                }

            }
        });

        /**
         * When skipping to "MyAccount" page, this activity will
         * start 2 tasks:
         * "MyAccountDisplayTask"
         * "UserPhotoDisplayTask"
         * Remember to change url1 into session version !!
         */
        /* -----------  MyAccountDisplayTask  -----------*/
        String url = "http://10.0.3.2:4567/MyAccount/" + userId;
        System.out.println("request url = " + url);
        new MyAccountDisplayTask().execute(url);

        /* -----------  UserPhotoDisplayTask  -----------*/
        UserPhotoDisplayTask userPhotoDisplayTask= new UserPhotoDisplayTask();
        try {
            Bitmap bm = userPhotoDisplayTask.execute().get();
            Drawable d = new BitmapDrawable(getResources(), bm);
            a_image.setImageDrawable(d);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void updateProfile(View view) {
        final Context context = this;
        Intent intent = new Intent(context, MyAccountUpdate.class);
        startActivity(intent);
    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();
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

    private class MyAccountDisplayTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            try {
                JSONObject json_result = new JSONObject(result);

                userProfile = new Gson().fromJson(result, UserProfile.class);
                a_username.setText(userProfile.getUsername());
                a_email.setText(userProfile.getEmail());
                a_phone.setText(userProfile.getPhone());
                a_address.setText(userProfile.getAddress());
                a_zipcode.setText(userProfile.getZipCode());
                a_city.setText(userProfile.getCity());
                a_state.setText(userProfile.getState());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

