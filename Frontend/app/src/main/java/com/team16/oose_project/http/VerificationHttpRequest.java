package com.team16.oose_project.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.UnsupportedEncodingException;

public class VerificationHttpRequest {
    private HttpClient httpClient;
    private HttpPost httpPost;
    private StringEntity stringEntity;

    public VerificationHttpRequest(String api, String requestBody) throws UnsupportedEncodingException {
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost(api);
        try {
            stringEntity = new StringEntity(requestBody);
            httpPost.setEntity(stringEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public HttpPost getHttpPost() {
        return httpPost;
    }
}
