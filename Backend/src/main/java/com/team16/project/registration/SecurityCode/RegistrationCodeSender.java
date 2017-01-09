package com.team16.project.registration.SecurityCode;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;


/**
* This class is responsible for sending registration code.
* @author  Team 16
*/
public class RegistrationCodeSender {
    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "587";
    private static final String SENDER = "JHUFurniture@gmail.com";
    private static final String SENDER_USERNAME = "JHUFurniture";
    private static final String SENDER_PASSWORD = "ooseteam16";
    private Properties properties;
    private Message message;
    private String recepient;

    protected RegistrationCodeSender(String recepient, String subject, String text, int registrationCode) throws MessagingException {
        properties = new Properties();
        setProperties();
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_USERNAME, SENDER_PASSWORD);
                    }
                });
        message = new MimeMessage(session);
        this.recepient = recepient;
        try {
            sendMessage(subject, text, registrationCode);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);
    }

    private void sendMessage(String subject, String text, int registrationCode) throws MessagingException {
        try {
            message.setFrom(new InternetAddress(SENDER));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recepient));
            message.setSubject(subject);
            message.setText(text + registrationCode);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
