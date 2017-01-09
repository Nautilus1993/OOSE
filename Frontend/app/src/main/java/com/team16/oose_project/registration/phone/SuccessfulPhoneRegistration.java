package com.team16.oose_project.registration.phone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.team16.oose_project.R;
import com.team16.oose_project.registration.constant.RegistrationConstant;
import com.team16.oose_project.registration.password.PasswordSetup;

import java.util.Timer;
import java.util.TimerTask;

// Activity after user's phone number has been verified
public class SuccessfulPhoneRegistration extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_phone_verification);

        Intent phoneVerification = getIntent();
        String verifiedEmail = phoneVerification.getStringExtra(RegistrationConstant.EMAIL_INPUT);
        String verifiedPhone = phoneVerification.getStringExtra(RegistrationConstant.PHONE_INPUT);
        TextView numberInput =  (TextView) findViewById(R.id.spvNumberInput);
        numberInput.setText(verifiedPhone);

        final Intent passwordSetup = new Intent(this, PasswordSetup.class);
        passwordSetup.putExtra(RegistrationConstant.EMAIL_INPUT, verifiedEmail);
        passwordSetup.putExtra(RegistrationConstant.PHONE_INPUT, verifiedPhone);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                startActivity(passwordSetup);
            }
        }, RegistrationConstant.INTER_PAGE_DELAY);
    }
}
