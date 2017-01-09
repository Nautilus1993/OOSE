package com.team16.oose_project.registration.phone;

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
* Phone verification activity
* @author  Team 16
*/
public class PhoneRegistration extends AppCompatActivity implements FormatValidation{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

    }

    // If the entered phone number is valid, go to the next activity.
    public void verifyPhone(View v) throws ExecutionException, InterruptedException {
        EditText numberInput =  (EditText)findViewById(R.id.pvNumberInput);
        String phoneToBeVerified = numberInput.getText().toString();
        if (validateFormat(phoneToBeVerified)) {
            String verificationResult = new registrationVerificationTask().execute(PhoneRegistrationConstant.PHONE_VERIFICATION_API, phoneToBeVerified).get();
            Intent phoneCodeVerification = new Intent(this, PhoneCodeVerification.class);
            Intent intent = getIntent();
            String message = intent.getStringExtra(RegistrationConstant.EMAIL_INPUT);
            phoneCodeVerification.putExtra(RegistrationConstant.EMAIL_INPUT, message);
            phoneCodeVerification.putExtra(RegistrationConstant.PHONE_INPUT, phoneToBeVerified);
            phoneCodeVerification.putExtra(PhoneRegistrationConstant.PHONE_REGISTRATION_CODE, verificationResult);
            startActivity(phoneCodeVerification);
        }
    }

    // This method tests if the entered phone number is in a valid format
    @Override
    public boolean validateFormat(String input) {
        if (input.length() == PhoneRegistrationConstant.PHONE_NUMBER_LENGTH) {
            return true;
        } else {
            Toast.makeText(this, PhoneRegistrationConstant.INVALID_PHONE,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
