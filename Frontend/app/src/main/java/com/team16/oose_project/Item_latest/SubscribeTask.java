package com.team16.oose_project.Item_latest;

import android.os.AsyncTask;

import com.team16.oose_project.http.VerificationHttpRequest;
import com.team16.oose_project.http.VerificationHttpResponse;
import com.team16.oose_project.http.VerificationHttpResponseBuffer;
import com.team16.oose_project.http.VerificationRequestBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
* This task sends a request to the server to add a new subscription
* @author  Team 16
*/
public class SubscribeTask extends AsyncTask<String, Void, String> {
    private VerificationHttpRequest verificationHttpRequest;
    private VerificationHttpResponse verificationHttpResponse;
    private VerificationHttpResponseBuffer verificationHttpResponseBuffer;

    @Override
    protected String doInBackground(String... params) {
        String api = params[0];
        String userId = params[1];
        String itemId = params[2];

        VerificationRequestBody verificationRequestBody = new VerificationRequestBody();
        verificationRequestBody.addUserToRequestBody(userId);
        verificationRequestBody.addItemToRequestBody(itemId);
        verificationRequestBody.convertRequestToString();

        try {
            verificationHttpRequest = new VerificationHttpRequest(api, verificationRequestBody.getRequestBodyString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            verificationHttpResponse = new VerificationHttpResponse(verificationHttpRequest.getHttpClient(), verificationHttpRequest.getHttpPost());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            verificationHttpResponseBuffer = new VerificationHttpResponseBuffer(verificationHttpResponse.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            verificationHttpResponseBuffer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return verificationHttpResponseBuffer.getBufferContent();
    }

    @Override
    protected void onPostExecute(String result){

    }
}
