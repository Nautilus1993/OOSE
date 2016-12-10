package com.team16.project.Model;

import com.team16.project.core.Bootstrap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.*;

public class PasswordDB {
    private Connection connection;
    private Statement statement;
    private JSONParser parser;
    private static final String TO_BE_VERIFIED = "toBeVerified";
    private static final String PASSWORD_TO_BE_VERIFIED = "passwordToBeVerified";
    private static final String PHONE_TO_BE_VERIFIED = "phoneToBeVerified";
    private static final String USER_EMAIL = "email";

    public PasswordDB() throws SQLException {
        connection = null;
        statement = null;
        parser = new JSONParser();
    }

    private void postQuery() throws SQLException {
        try {
            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertUser(String body) throws ParseException, SQLException, MessagingException {

        JSONObject jsonObject = (JSONObject) parser.parse(body);
        String emailToBeInserted = (String) jsonObject.get(TO_BE_VERIFIED);
        String passwordToBeInserted = (String) jsonObject.get(PASSWORD_TO_BE_VERIFIED);
        String phoneInserted = (String) jsonObject.get(PHONE_TO_BE_VERIFIED);

        int userId = -1;
        int ContactId = -1;

        System.out.println(emailToBeInserted);
        System.out.println(passwordToBeInserted);

        String query1 = "INSERT INTO UserLogin (password, email) VALUES (\'"+ passwordToBeInserted + "\', \'" + emailToBeInserted + "\');";
        String query2 = "INSERT INTO ContactInfo (email, phone) VALUES (\'" + emailToBeInserted + "\', \'" + phoneInserted + "\');";
        String query3 = "SELECT userId FROM UserLogin WHERE email = \'" + emailToBeInserted + "\';";
        String query4 = "SELECT ContactId FROM ContactInfo WHERE email = \'" + emailToBeInserted +"\';";
        //String query5 = "INSERT INTO UserDetail (userId, contactId) VALUES (\'" + userId + "\', \'" + ContactId + "\';";


        try {
            connection =  DriverManager.getConnection(Bootstrap.DATABASE);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);

            ResultSet getUserId = statement.executeQuery(query3);
            ResultSet getContactId = statement.executeQuery(query4);

            userId = getUserId.getInt(1);
            ContactId = getContactId.getInt(1);
            String query5 = "INSERT INTO UserDetail (userId, contactId) VALUES (" + userId + ", " + ContactId + ");";
            System.out.println(query5);
            statement.executeUpdate(query5);
            postQuery();
            return 1;
        } catch (SQLException e) {
            postQuery();
            return 0;
        }
    }

}


