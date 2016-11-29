package com.team16.project.registration.email;


import com.team16.project.sqlite.EmailRegistrationDB;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.*;

public class EmailRegistrationService {
    private EmailRegistrationDB emailRegistrationDB;

    EmailRegistrationService() throws SQLException {
        emailRegistrationDB = new EmailRegistrationDB();
    }


    int verifyEmail(String body) throws ParseException, SQLException, MessagingException {
        return emailRegistrationDB.searchUser(body);
    }
}