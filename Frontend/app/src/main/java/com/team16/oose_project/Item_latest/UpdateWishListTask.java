package com.team16.oose_project.Item_latest;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;

public class UpdateWishListTask extends AsyncTask<String, Void, String> {
    private HttpClient httpClient;
    private HttpPost httpPost;
    private HttpResponse httpResponse;

    @Override
    protected String doInBackground(String... params) {
        //load data from server
        String api = params[0];

        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost(api);

        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
