package com.team16.oose_project.sellingList;

/**
 * Created by lxx on 11/13/16.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.team16.oose_project.R;
import com.team16.oose_project.core.GuestHomePage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

import static com.team16.oose_project.sellingList.SellingListDisplayActivity.GET;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class UpdateItemActivity extends NewItemActivity{
    protected String itemId;
    private String url = "http://10.0.3.2:4567/sellList/update/";
    private String message = "Item updated!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemId = getIntent().getStringExtra("itemId");
        new DisplayPostedItem().execute();
    }


    public void setItemData(SellingItem item){
        nameTextField.setText(item.getName());
        priceTextField.setText(item.getPrice());
        descTextField.setText(item.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if(item.getAvialableDate() != null) {
            avaiDateTextField.setText(sdf.format(item.getAvialableDate())); //with datepicker?
        }
        if(item.getExpireDate() != null) {
            expDateTextField.setText(sdf.format(item.getExpireDate()));//with datepicker?
        }
        topCatSpinner.setSelection(((ArrayAdapter)topCatSpinner.getAdapter()).getPosition(item.getCategory1()));
        subCatSpinner.setSelection(((ArrayAdapter)subCatSpinner.getAdapter()).getPosition(item.getCategory2()));
        conditionSpinner.setSelection(((ArrayAdapter)conditionSpinner.getAdapter()).getPosition(item.getCondition()));

        if(TRUE == item.isDeliver()){
            deliverRadioGroup.check(R.id.input_delivery_yes);
        }else if(FALSE == item.isDeliver()){
            deliverRadioGroup.check(R.id.input_delivery_no);
        }else{
        }

        String[] contacts = item.getContacts();
        for(String contact : contacts) {
            if("email".equals(contact)){
                setCheck(emailButton);
                addContact(emailButton);
                continue;
            }
            if("sns".equals(contact)){
                setCheck(snsButton);
                addContact(snsButton);
                continue;
            }
            if("text".equals(contact)){
                setCheck(textButton);
                addContact(textButton);
                continue;
            }
            if("phone".equals(contact)){
                setCheck(phoneButton);
                addContact(phoneButton);
                continue;
            }
        }
    }

    public void setCheck(ImageButton button){
        button.setColorFilter(Color.argb(0, 0, 0, 0));//light up
        button.setSelected(TRUE);
    }


    private class DisplayPostedItem extends AsyncTask<String, Void, String> {
        String url = "http://10.0.3.2:4567/sellList/edit/" + itemId;

        @Override
        protected String doInBackground(String... String) {
            //System.out.println("*****Request response:" + GET(url));
            return GET(url);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Here is your selling list!", Toast.LENGTH_SHORT).show();

            SellingItem item = new SellingItem();
            try{
                //Type type = new TypeToken<SellingItem>(){}.getType();
                Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
                item = gson.fromJson(result, SellingItem.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            setItemData(item);
        }
    }

    protected String getUrl(){
        return url;
    }

    protected String getMessage(){
        return message;
    }


}
