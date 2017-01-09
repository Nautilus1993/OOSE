package com.team16.oose_project.registration.password;

import android.os.AsyncTask;
import android.util.Log;

import com.team16.oose_project.http.VerificationHttpRequest;
import com.team16.oose_project.http.VerificationHttpResponse;
import com.team16.oose_project.http.VerificationHttpResponseBuffer;
import com.team16.oose_project.http.VerificationRequestBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
* This class is responsible for sending a request to create a new user when email verification passes.
* @author  Team 16
*/
public class CreateUserTask extends AsyncTask<String, Void, String> {
    private VerificationHttpRequest verificationHttpRequest;
    private VerificationHttpResponse verificationHttpResponse;
    private VerificationHttpResponseBuffer verificationHttpResponseBuffer;

    @Override
    protected String doInBackground(String... params) {
        String api = params[0];
        String verifiedEmail = params[1];
        String verifiedPhone = params[2];
        String verifiedPassword = params[3];


        VerificationRequestBody verificationRequestBody = new VerificationRequestBody();
        verificationRequestBody.addEmailToRequestBody(verifiedEmail);
        verificationRequestBody.addPhoneToRequestBody(verifiedPhone);
        verificationRequestBody.addPasswordToRequestBody(verifiedPassword);
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
