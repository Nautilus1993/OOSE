package com.team16.project.registration.email;
import com.team16.project.registration.SecurityCode.RegistrationCodeSender;

import javax.mail.*;
/**
 * Whenever a new instance of this class is created, an email containing registration code will be sent to user.
 * @author OOSE_Team16
 */
public class EmailRegistrationCode extends RegistrationCodeSender {
    private static final String SUBJECT = "Email Registration Code";
    private static final String TEXT = "Your email registration code is ";

    public EmailRegistrationCode(String recepient, int registrationCode) throws MessagingException {
        super(recepient, SUBJECT, TEXT, registrationCode);
    }
}
