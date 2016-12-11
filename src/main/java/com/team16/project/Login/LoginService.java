package com.team16.project.Login;


import com.team16.project.Model.LoginDB;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.SQLException;

public class LoginService {
    private LoginDB loginDB;

    LoginService() throws SQLException {
        loginDB = new LoginDB();
    }

    String verifyLogin(String body) throws ParseException, SQLException, MessagingException {
        return loginDB.searchUser(body);
    }
}


