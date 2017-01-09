package com.team16.oose_project.registration.email;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.team16.oose_project.R;
import com.team16.oose_project.registration.constant.RegistrationConstant;
import com.team16.oose_project.registration.phone.PhoneRegistration;

import java.util.Timer;
import java.util.TimerTask;

// Activity after email verification is successful
public class SuccessfulEmailRegistration extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_email_verification);

        Intent intent = getIntent();
        String verifiedEmail = intent.getStringExtra(RegistrationConstant.EMAIL_INPUT);
        TextView emailInput =  (TextView) findViewById(R.id.sevEmailInput);
        emailInput.setText(verifiedEmail);

        final Intent phoneRegistration = new Intent(this, PhoneRegistration.class);
        phoneRegistration.putExtra(RegistrationConstant.EMAIL_INPUT, verifiedEmail);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                startActivity(phoneRegistration);
            }
        }, RegistrationConstant.INTER_PAGE_DELAY);
    }
}
