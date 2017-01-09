package com.team16.oose_project.registration.email;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.team16.oose_project.R;
import com.team16.oose_project.registration.constant.RegistrationConstant;
import com.team16.oose_project.registration.validation.FormatValidation;
import com.team16.oose_project.registration.verification.registrationVerificationTask;

import java.util.concurrent.ExecutionException;

/**
* Email registration activity
* @author  Team 16
*/
public class EmailRegistration extends AppCompatActivity implements FormatValidation{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
    }

    // This method verifies if the input email is already in the database.
    public void verifyEmail(View v) throws ExecutionException, InterruptedException {
        EditText emailInput = (EditText) findViewById(R.id.evEmailInput);
        String emailToBeVerified = emailInput.getText().toString();
        if (validateFormat(emailToBeVerified)) {
            String verificationResult = new registrationVerificationTask().execute(EmailRegistrationConstant.EMAIL_VERIFICATION_API, emailToBeVerified).get();
            analyzeResult(verificationResult, emailToBeVerified);
        }
    }

    // This method starts the next activity if the enterd email does not exist in the database.
    private void analyzeResult(String result, String email) {
        if (result.equals(EmailRegistrationConstant.EMAIL_EXISTS)) {
            Toast.makeText(this, EmailRegistrationConstant.EMAIL_EXISTS_MSG,
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Intent emailCodeVerification = new Intent(this, EmailCodeVerification.class);
            emailCodeVerification.putExtra(RegistrationConstant.EMAIL_INPUT , email);
            emailCodeVerification.putExtra(EmailRegistrationConstant.EMAIL_REGISTRATION_CODE, result);
            startActivity(emailCodeVerification);
        }
    }

    @Override
    public boolean validateFormat(String input) {
        if (input.contains(EmailRegistrationConstant.EMAIL_DOMAIN)) {
            return true;
        } else {
            Toast.makeText(this, EmailRegistrationConstant.INVALID_EMAIL,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}


