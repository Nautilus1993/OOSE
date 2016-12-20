package com.team16.project.subscribe;

import com.team16.project.core.Bootstrap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscribeService {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private JSONParser parser;

    public SubscribeService() {
        parser = new JSONParser();
        connection = null;
        statement = null;
        resultSet = null;
    }

    public int subscribe(String body) throws ParseException, SQLException {
        String query = "SELECT category1, category2 " + "FROM item " + "WHERE itemId = ?";
        String insert = "INSERT INTO Subscribe (userId, category1, category2) VALUES (?, ?, ?)";
        JSONObject jsonObject = (JSONObject) parser.parse(body);
        String itemId = (String) jsonObject.get("itemId");
        String userId =  (String) jsonObject.get("userId");

        try {
            connection =  DriverManager.getConnection(Bootstrap.DATABASE);
            statement = connection.prepareStatement(query);
            statement.setString(1, itemId);
            resultSet = statement.executeQuery();
            String category1 = resultSet.getString("category1");
            String category2 = resultSet.getString("category2");
            statement = connection.prepareStatement(insert);
            statement.setString(1, userId);
            statement.setString(2, category1);
            statement.setString(3, category2);
            statement.executeUpdate();
            postQuery();
            System.out.print("Successful");
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            postQuery();
            return 0;
        }
    }

    public int listUser(String cat1, String cat2) throws SQLException {
        String query = "SELECT email, category1, category2 " +
                "FROM UserDetail, ContactInfo, Subscribe " +
                "WHERE UserDetail.userId = Subscribe.userId AND UserDetail.contactId = ContactInfo.contactId " +
                "AND Subscribe.category1 = ? AND Subscribe.category2 = ?";

        try {
            connection =  DriverManager.getConnection(Bootstrap.DATABASE);
            statement = connection.prepareStatement(query);
            statement.setString(1, cat1);
            statement.setString(2, cat2);
            resultSet = statement.executeQuery();
            List<String> emails = new ArrayList<String>();
            List<String> cat1s = new ArrayList<String>();
            List<String> cat2s = new ArrayList<String>();
            while (resultSet.next()) {
                emails.add(resultSet.getString("email"));
                cat1s.add(resultSet.getString("category1"));
                cat2s.add(resultSet.getString("category2"));
            }

            for (int i = 0; i < emails.size(); i++) {
                SubscribeSender subscribeSender = new SubscribeSender(emails.get(i),
                        "JHUFurniture Subscribe", "A new item in your subscribed category is posted - ",
                        "top category: " + cat1s.get(i) + " - sub category: " + cat2s.get(i));
            }
            postQuery();

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            postQuery();
            return 0;
        } catch (MessagingException e) {
            e.printStackTrace();
            postQuery();
            return 0;
        }
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
}
