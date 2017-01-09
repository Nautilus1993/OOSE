package com.team16.oose_project.Item_latest;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ItemListDisplayTask extends AsyncTask<String, Void, String> {
    private final String itemType;
    private HttpClient httpClient;
    private HttpPost httpPost;
    private StringEntity stringEntity;
    private JSONObject requestBody;
    private HttpResponse httpResponse;
    private BufferedReader bufferedReader;
    private StringBuffer stringBuffer;

    public ItemListDisplayTask (String itemType) {
        this.itemType = itemType;
        requestBody = new JSONObject();
    }



    @Override
    protected String doInBackground(String... params) {
        //load data from server
        String api = params[0];

        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost(api);

        try {
            requestBody.put("itemType",itemType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            stringEntity = new StringEntity(requestBody.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(stringEntity);

        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            stringBuffer = new StringBuffer("");

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();

            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
