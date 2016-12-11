package com.team16.project.Model;

import com.team16.project.core.Bootstrap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.*;

public class LoginDB {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private JSONParser parser;
    private static final String TO_BE_VERIFIED = "toBeVerified";
    private static final String PASSWORD_TO_BE_VERIFIED = "passwordToBeVerified";
    private static final String USER_EMAIL = "email";

    public LoginDB() throws SQLException {
        connection = null;
        statement = null;
        resultSet = null;
        parser = new JSONParser();
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

    public String searchUser(String body) throws ParseException, SQLException, MessagingException {
        String query = "SELECT userId " + "FROM UserLogin WHERE email = ? AND password = ?";
        JSONObject jsonObject = (JSONObject) parser.parse(body);
        String emailToBeVerified = (String) jsonObject.get(TO_BE_VERIFIED);
        String passwordToBeVerified = (String) jsonObject.get(PASSWORD_TO_BE_VERIFIED);

        try {
            connection =  DriverManager.getConnection(Bootstrap.DATABASE);
            statement = connection.prepareStatement(query);
            statement.setString(1, emailToBeVerified);
            statement.setString(2, passwordToBeVerified);
            resultSet = statement.executeQuery();

            String sqlResult = resultSet.getString("userId");
            postQuery();
            return sqlResult;
        } catch (SQLException e) {
            postQuery();
            return "0";
        }
    }
}

