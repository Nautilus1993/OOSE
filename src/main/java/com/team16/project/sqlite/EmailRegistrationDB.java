package com.team16.project.sqlite;

import com.team16.project.core.Bootstrap;
import com.team16.project.registration.SecurityCode.RegistrationCodeGenerator;
import com.team16.project.registration.email.EmailRegistrationCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.*;

public class EmailRegistrationDB {

    private static final String TO_BE_VERIFIED = "toBeVerified";
    private static final String USER_EMAIL = "email";
    private JSONParser parser;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public EmailRegistrationDB() throws SQLException {
        parser = new JSONParser();
        connection = null;
        statement = null;
        resultSet = null;
    }

    private void postQuery() throws SQLException {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int searchUser(String body) throws ParseException, SQLException, MessagingException {
        String query = "SELECT email " + "FROM UserLogin " + "WHERE email = ?";
        JSONObject jsonObject = (JSONObject) parser.parse(body);
        String emailToBeVerified =  (String) jsonObject.get(TO_BE_VERIFIED);

        try {
            connection =  DriverManager.getConnection(Bootstrap.DATABASE);
            statement = connection.prepareStatement(query);
            statement.setString(1, emailToBeVerified);
            resultSet = statement.executeQuery();
            resultSet.getString(USER_EMAIL);
            postQuery();
            return 0;
        } catch (SQLException e) {
            postQuery();
            RegistrationCodeGenerator registrationCodeGenerator = new RegistrationCodeGenerator();
            EmailRegistrationCode emailRegistrationCode = new EmailRegistrationCode(emailToBeVerified,
                    registrationCodeGenerator.getRegistrationCode());
            return registrationCodeGenerator.getRegistrationCode();
            //return 1;
        }
    }
}

