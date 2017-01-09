package com.team16.oose_project.http;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class VerificationRequestBody {
    private static final String TO_BE_VERIFIED = "toBeVerified";
    private static final String PASSWORD_TO_BE_VERIFIED = "passwordToBeVerified";
    private static final String PHONE_TO_BE_VERIFIED = "phoneToBeVerified";
    private static final String USER_ID = "userId";
    private static final String ITEM_ID = "itemId";
    private JSONObject requestBody;
    private String requestBodyString;

    public VerificationRequestBody() {
        this.requestBody = new JSONObject();
        this. requestBodyString = "";
    }

    public void convertRequestToString() {
        requestBodyString = requestBody.toString();
    }

    public void addEmailToRequestBody(String body) {
        try {
            requestBody.put(TO_BE_VERIFIED, body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addPasswordToRequestBody(String body) {
        try {
            requestBody.put(PASSWORD_TO_BE_VERIFIED, body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addPhoneToRequestBody(String body) {
        try {
            requestBody.put(PHONE_TO_BE_VERIFIED, body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addUserToRequestBody(String body) {
        try {
            requestBody.put(USER_ID, body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addItemToRequestBody(String body) {
        try {
            requestBody.put(ITEM_ID, body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getRequestBodyString() {
        return requestBodyString;
    }
}
