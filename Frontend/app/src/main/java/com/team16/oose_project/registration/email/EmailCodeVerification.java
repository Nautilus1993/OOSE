package com.team16.oose_project.registration.email;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.team16.oose_project.R;
import com.team16.oose_project.registration.constant.RegistrationConstant;
import com.team16.oose_project.registration.validation.CodeValidation;

/**
* Reigistration code verification activity
* @author  Team 16
*/
public class EmailCodeVerification extends AppCompatActivity implements CodeValidation{
    private String registrationCode;
    private String emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_code_verification);

        Intent intent = getIntent();
        registrationCode  = intent.getStringExtra(EmailRegistrationConstant.EMAIL_REGISTRATION_CODE);
        emailInput = intent.getStringExtra(RegistrationConstant.EMAIL_INPUT);
    }

    // This method starts the next acitivty if the code entered matches the code user has received
    public void verifyEmailCode(View v) {
        EditText codeInput = (EditText) findViewById(R.id.ecvCodeInput);
        String codeToBeVerified = codeInput.getText().toString();
        if (validateCode(codeToBeVerified)) {
            Intent successfulEmailRegistration = new Intent(this, SuccessfulEmailRegistration.class);
            successfulEmailRegistration.putExtra(RegistrationConstant.EMAIL_INPUT, emailInput);
            startActivity(successfulEmailRegistration);
        }
    }

    // This method verifies if too codes are equal
    @Override
    public boolean validateCode(String code) {
        if (code.equals(registrationCode)) {
            return true;
        } else {
            Toast.makeText(this, RegistrationConstant.INCORRECT_CODE,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
