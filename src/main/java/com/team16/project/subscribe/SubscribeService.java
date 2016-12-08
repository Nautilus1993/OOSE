package com.team16.project.subscribe;

import com.team16.project.core.Bootstrap;

import javax.mail.MessagingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscribeService {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public SubscribeService() {
        connection = null;
        statement = null;
        resultSet = null;
    }

    public int listUser() throws SQLException {
        String query = "SELECT email, category1, category2 " +
                "FROM UserDetail, ContactInfo, Subscribe " +
                "WHERE UserDetail.userId = Subscribe.userId AND UserDetail.contactId = ContactInfo.contactId";

        try {
            connection =  DriverManager.getConnection(Bootstrap.DATABASE);
            statement = connection.prepareStatement(query);
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
                SubscribeSender subscribeSender = new SubscribeSender(emails.get(i), "JHUFurniture Subscribe", "Checkout your scribe: ", cat1s.get(i) + "-" + cat2s.get(i));
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
