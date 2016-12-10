package com.team16.project.MyList.WishList;

import com.team16.project.core.Bootstrap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishService {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private JSONParser parser;

    public WishService() {
        connection = null;
        statement = null;
        resultSet = null;
        parser = new JSONParser();
    }

    public List<Wish> listWishes(String body)  {
        String query = "SELECT * " + "FROM WishList NATURAL JOIN Item NATURAL JOIN UserDetail " + "WHERE WishList.userId = ?";
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String userID =  (String) jsonObject.get("toBeVerified");

        try {
            connection =  DriverManager.getConnection(Bootstrap.DATABASE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.setString(1, userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Wish> wishes = new ArrayList<Wish>();

        try {
            while (resultSet.next()) {
                wishes.add(new Wish(resultSet.getString("name"), resultSet.getString("imgPath"),
                        resultSet.getString("condition"), resultSet.getString("price"), resultSet.getString("avialableDate"), resultSet.getString("expireDate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            postQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wishes;
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
