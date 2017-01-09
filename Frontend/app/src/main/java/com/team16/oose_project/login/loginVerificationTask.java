package com.team16.oose_project.login;

import android.os.AsyncTask;
import android.util.Log;

import com.team16.oose_project.http.VerificationHttpRequest;
import com.team16.oose_project.http.VerificationHttpResponse;
import com.team16.oose_project.http.VerificationHttpResponseBuffer;
import com.team16.oose_project.http.VerificationRequestBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class loginVerificationTask extends AsyncTask<String, Void, String> {
    private VerificationHttpRequest verificationHttpRequest;
    private VerificationHttpResponse verificationHttpResponse;
    private VerificationHttpResponseBuffer verificationHttpResponseBuffer;
    private String passwordToBeVerified;

    @Override
    protected String doInBackground(String... params) {
        String api = params[0];
        String emailToBeVerified = params[1];
        passwordToBeVerified = params[2];


        VerificationRequestBody verificationRequestBody = new VerificationRequestBody();
        verificationRequestBody.addEmailToRequestBody(emailToBeVerified);
        verificationRequestBody.addPasswordToRequestBody(passwordToBeVerified);
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
