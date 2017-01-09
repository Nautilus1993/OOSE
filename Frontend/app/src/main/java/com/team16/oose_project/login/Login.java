package com.team16.oose_project.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.team16.oose_project.R;
import com.team16.oose_project.core.GuestHomePage;
import com.team16.oose_project.registration.email.EmailRegistration;


import java.util.concurrent.ExecutionException;

import static com.team16.oose_project.core.GuestHomePage.userId;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void verifyLogin(View v) throws ExecutionException, InterruptedException {
        EditText enterEmail = (EditText) findViewById(R.id.enterEmail);
        EditText enterPassword = (EditText) findViewById(R.id.enterPassword);

        String loginEmail = enterEmail.getText().toString();
        String loginPassword = enterPassword.getText().toString();

        if (validateEmailFormat(loginEmail)) {
            String loginResult = new loginVerificationTask().execute(LoginConstant.EMAIL_LOGIN_API, loginEmail, loginPassword).get();

            Log.d("loginResult", loginResult);

            analyzeResult(loginResult);
        }
    }

    private void analyzeResult(String loginResult) {

        if (loginResult.equals(LoginConstant.LOGIN_FAIL)) {
            Toast.makeText(this, LoginConstant.LOGIN_FAIL_MSG,
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Intent guestHomePage = new Intent(this, GuestHomePage.class);
            Log.d("userId Before: ", userId);
            userId = loginResult;
            Log.d("userId After: ", userId);
            startActivity(guestHomePage);
        }
    }

    public boolean validateEmailFormat(String input) {
        if (input.contains(LoginConstant.EMAIL_DOMAIN)) {
            return true;
        } else {
            Toast.makeText(this, LoginConstant.INVALID_EMAIL,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validPasswordSize(int passwordSize){
        if (passwordSize < LoginConstant.MINIMUM_PASSWORD_LENGTH) {
            Toast.makeText(this, LoginConstant.PASSWORD_LENGTH_MSG + LoginConstant.MINIMUM_PASSWORD_LENGTH,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

}

