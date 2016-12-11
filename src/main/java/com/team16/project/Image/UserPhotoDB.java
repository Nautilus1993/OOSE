package com.team16.project.Image;

import com.team16.project.core.Bootstrap;

import java.sql.*;
import java.util.HashMap;

public class UserPhotoDB {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public UserPhotoDB() throws SQLException{
        connection = null;
        statement = null;
        resultSet = null;
    }

    public boolean insertUserPhoto(String name, String filePath){
        Connection conn = null;

        try{
            // connect to DB
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Bootstrap.DATABASE);
            connection.setAutoCommit(false);

            System.out.println("Connect database successfully");

            // prepare query
            String sql = "INSERT INTO Image (name, imageLink) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, filePath);
            System.out.println("Query = " + sql);
            statement.executeUpdate();

            statement.close();
            connection.commit();
            connection.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            return false;
        }
    }

}
