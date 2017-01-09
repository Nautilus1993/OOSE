package com.team16.project.registration.email;


import com.team16.project.Model.EmailRegistrationDB;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.*;

/**
* This class is responsible for querying the userLogin relation when registering an account.
* @author  Team 16
*/
public class EmailRegistrationService {
    private EmailRegistrationDB emailRegistrationDB;

    EmailRegistrationService() throws SQLException {
        emailRegistrationDB = new EmailRegistrationDB();
    }


    // This method will query the userLogin table and see if the email in the body already exists
    int verifyEmail(String body) throws ParseException, SQLException, MessagingException {
        return emailRegistrationDB.searchUser(body);
    }
}
