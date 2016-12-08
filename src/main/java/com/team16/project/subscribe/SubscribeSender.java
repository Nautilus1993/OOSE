package com.team16.project.subscribe;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SubscribeSender {
    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "587";
    private static final String SENDER = "JHUFurniture@gmail.com";
    private static final String SENDER_USERNAME = "JHUFurniture";
    private static final String SENDER_PASSWORD = "ooseteam16";
    private Properties properties;
    private Message message;
    private String recipient;

    protected SubscribeSender(String recipient, String subject, String text, String category) throws MessagingException {
        properties = new Properties();
        setProperties();
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_USERNAME, SENDER_PASSWORD);
                    }
                });
        message = new MimeMessage(session);
        this.recipient = recipient;
        try {
            sendMessage(subject, text, category);
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

    private void sendMessage(String subject, String text, String category) throws MessagingException {
        try {
            message.setFrom(new InternetAddress(SENDER));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(text + category);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
