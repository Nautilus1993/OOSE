package com.team16.oose_project.wishlist;

import android.os.AsyncTask;

import com.team16.oose_project.http.VerificationHttpRequest;
import com.team16.oose_project.http.VerificationHttpResponse;
import com.team16.oose_project.http.VerificationHttpResponseBuffer;
import com.team16.oose_project.http.VerificationRequestBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
* This task sends a request to the server to return all wishes of a particular user.
* @author  Team 16
*/
public class WishTask extends AsyncTask<String, Void, String> {
    private VerificationHttpRequest verificationHttpRequest;
    private VerificationHttpResponse verificationHttpResponse;
    private VerificationHttpResponseBuffer verificationHttpResponseBuffer;

    @Override
    protected String doInBackground(String... params) {
        String api = params[0];
        String userID = params[1];

        VerificationRequestBody verificationRequestBody = new VerificationRequestBody();
        verificationRequestBody.addEmailToRequestBody(userID);
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
