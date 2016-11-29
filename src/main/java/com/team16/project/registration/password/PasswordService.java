package com.team16.project.registration.password;

import com.team16.project.sqlite.PasswordDB;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.SQLException;

public class PasswordService {
    private PasswordDB passwordDB;

    PasswordService() throws SQLException {
        passwordDB = new PasswordDB();
    }


    int createUser(String body) throws ParseException, SQLException, MessagingException {
        return passwordDB.insertUser(body);
    }
}

