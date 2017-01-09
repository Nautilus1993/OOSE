package com.team16.oose_project.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

public class VerificationHttpResponse {
    private HttpResponse httpResponse;

    public HttpEntity getEntity() {
        return httpResponse.getEntity();
    }

    public VerificationHttpResponse(HttpClient httpClient, HttpPost httpPost) throws IOException {
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
