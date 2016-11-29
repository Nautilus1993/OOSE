package com.team16.project.Login;

import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.SQLException;

import com.team16.project.sqlite.LoginDB;

public class LoginService {
    private LoginDB loginDB;

    LoginService() throws SQLException {
        loginDB = new LoginDB();
    }

    int verifyLogin(String body) throws ParseException, SQLException, MessagingException {
        return loginDB.searchUser(body);
    }
}


