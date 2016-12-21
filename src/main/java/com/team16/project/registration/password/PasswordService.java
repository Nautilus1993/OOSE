package com.team16.project.registration.password;

import com.team16.project.Model.PasswordDB;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.SQLException;
/**
 * This class is responsible for registering username and password
 * @author OOSE_Team16
 */
public class PasswordService {
    private PasswordDB passwordDB;

    PasswordService() throws SQLException {
        passwordDB = new PasswordDB();
    }

    // This method will creates a new user with the input user name and password in the body.
    int createUser(String body) throws ParseException, SQLException, MessagingException {
        return passwordDB.insertUser(body);
    }
}

