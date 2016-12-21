package com.team16.project.registration.email;


import com.team16.project.Model.EmailRegistrationDB;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.*;
/**
 * This class is for checking existing email.
 * @author OOSE_Team16
 */
public class EmailRegistrationService {
    private EmailRegistrationDB emailRegistrationDB;

    EmailRegistrationService() throws SQLException {
        emailRegistrationDB = new EmailRegistrationDB();
    }


    // This method checks if the input email address is already in database.
    int verifyEmail(String body) throws ParseException, SQLException, MessagingException {
        return emailRegistrationDB.searchUser(body);
    }
}
