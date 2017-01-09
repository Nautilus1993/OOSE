package com.team16.oose_project.registration.password;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.team16.oose_project.R;
import com.team16.oose_project.core.GuestHomePage;
import com.team16.oose_project.registration.constant.RegistrationConstant;

import java.util.concurrent.ExecutionException;

/**
* This class is responsible for sending a request to create a new user after the password has been set up.
* @author  Team 16
*/
public class SuccessfulPasswordSetup extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_password_setup);

        Intent passwordSetup = getIntent();
        String message = passwordSetup.getStringExtra(PasswordConstant.HIDDEN_PASSWORD);
        TextView passwordInput =  (TextView) findViewById(R.id.spsPasswordInput);
        passwordInput.setText(message);

        Intent phoneVerification = getIntent();
        String verifiedEmail = phoneVerification.getStringExtra(RegistrationConstant.EMAIL_INPUT);
        String verifiedPhone = phoneVerification.getStringExtra(RegistrationConstant.PHONE_INPUT);
        String verifiedPassword = phoneVerification.getStringExtra(PasswordConstant.PASSWORD);

        CreateUserTask createUserTask = new CreateUserTask();
        try {
            String result = createUserTask.execute(PasswordConstant.CREATE_USER_API, verifiedEmail, verifiedPhone, verifiedPassword).get();
            analyzeResult(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // If the server has successfully created a user, go to homepage. Otherwise, display an error message.
    private void analyzeResult(String result) {
        if (result.equals(PasswordConstant.CREATE_USER_FAIL)) {
            Toast.makeText(this, PasswordConstant.FAIL_CREATE_USER_MSG,
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Intent guestHomePage = new Intent(this, GuestHomePage.class);
            startActivity(guestHomePage);
        }
    }
}
