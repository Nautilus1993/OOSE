package com.team16.oose_project.sellingList;

/**
 * Created by lxx on 11/13/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.team16.oose_project.Item_latest.ItemDetail;
import com.team16.oose_project.R;
import com.team16.oose_project.core.GuestHomePage;
import com.team16.oose_project.wishlist.RemoveTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;



public class SellingListDisplayActivity extends Activity{
    ListView listView;
    SellingListAdapter adapter;
    final String userId = GuestHomePage.userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_selling_list);

        // init the item listView
        listView = (ListView) findViewById(R.id.selling_list);

        //get dataArray to be loaded
//        ArrayList<SellingItem> dataList = new ArrayList<SellingItem>();
//        dataList.add(0, new SellingItem("Bed","http://123.com/pic.png", "$150", "New",
//                Boolean.TRUE, new Date(01/01/1990), new String[1]));
//        dataList.add(1, new SellingItem("Chair","http://123.com/pic.png", "$150", "New",
//                Boolean.TRUE, new Date(02/02/2000), new String[2]));



        //get reference to the views
        //setListViews(dataList);

        // Use customized adapter to load data and display
        // adapter = new SellingListAdapter(this, dataList);
        //listView.setAdapter(adapter);

        //retrieve user sellingList data and show response on the view

        getDataList(userId);

        //set onclick listener to listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //get the selected item's id
                SellingItem selectedItem = (SellingItem) adapter.getItem(position);
                String selectedItemId = selectedItem.getItemId();

                //to ItemDetail page
                Intent toItemDetail = new Intent(SellingListDisplayActivity.this, ItemDetail.class);
                toItemDetail.putExtra("itemId", selectedItemId);
                startActivity(toItemDetail);
            }
        });
    }


    public void getDataList(String userId){
        //set API url and userId
        String url = "http://10.0.3.2:4567/sellList/display/" + userId;

        //call AsynTask to perform network operation on separate thread
        new DisplaySellingList().execute(url);
    }

    public void setListViews(ArrayList dataArray){
        // Use customized adapter to load particular data and execute display
        this.adapter = new SellingListAdapter(this, dataArray);
        listView.setAdapter(adapter);
    }


    public void onEditClicked(View view) {
        //get item id
        String itemId = getButtonBelonedItemId(view);

        //to updateItem activity
        Intent updateItem = new Intent(SellingListDisplayActivity.this, UpdateItemActivity.class);
        updateItem.putExtra("itemId", itemId);
        startActivity(updateItem);
    }

    public void onRemoveClicked(View view) {
        //get item id
        String itemId = getButtonBelonedItemId(view);

        //set API url and userId
        String url = "http://10.0.3.2:4567/sellList/remove/" + itemId;

        //call AsynTask to perform network operation on separate thread
        new RemovePost().execute(url);

        //refresh
        adapter.notifyDataSetChanged();
    }

    public String getButtonBelonedItemId(View view){
        ListView belongedListView = (ListView) view.getParent().getParent();
        int position = belongedListView.getPositionForView(view);
        SellingItem selectedItem = (SellingItem) adapter.getItem(position);
        String selectedItemId = selectedItem.getItemId();
        return selectedItemId;
    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            //create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            //make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            //receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            //convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            System.out.println(e);
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }


    // convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    private class DisplaySellingList extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            //System.out.println("*****Request response:" + GET(urls[0]));
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Here is your selling list!", Toast.LENGTH_SHORT).show();

            ArrayList<SellingItem> userSellingList= new ArrayList<SellingItem>();
            try{
                Type type = new TypeToken<ArrayList<SellingItem>>(){}.getType();
                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
                userSellingList = gson.fromJson(result, type);
            }catch(Exception e){
                e.printStackTrace();
            }
            setListViews(userSellingList);
        }
    }

    private class RemovePost extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            //System.out.println("*****Edit post Request response:" + GET(urls[0]));
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Your item is Removed!", Toast.LENGTH_SHORT).show();
            getDataList(userId);
        }
    }

}
