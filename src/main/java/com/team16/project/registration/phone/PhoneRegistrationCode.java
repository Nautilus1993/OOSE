package com.team16.project.registration.phone;

import com.team16.project.registration.SecurityCode.RegistrationCodeSender;

import javax.mail.MessagingException;
/**
 * Whenever an instance of this class is created, a text message containing registration code will be sent to user.
 * @author OOSE_Team16
 */
class PhoneRegistrationCode extends RegistrationCodeSender{
    private static final String SUBJECT = "Phone Registration Code";
    private static final String TEXT = "Your phone registration code is ";

    PhoneRegistrationCode(String recepient, int registrationCode) throws MessagingException {
        super(recepient, SUBJECT, TEXT, registrationCode);
    }
}
