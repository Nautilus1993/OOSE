package com.team16.project.Image;

import com.team16.project.core.Bootstrap;

import java.sql.*;

public class ItemPhotoDB {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public ItemPhotoDB(){
        connection = null;
        statement = null;
        resultSet = null;
    }

    public String generateItemPhotoName(){
        String photoName = "";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Bootstrap.DATABASE);
            connection.setAutoCommit(false);

            String sql = "SELECT MAX(itemId) FROM Item";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            Integer itemNumber = resultSet.getInt("MAX(itemId)") + 1;
            photoName = itemNumber.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return photoName;
    }

    /**
     * This function is used when user post an item
     * @param userId
     * @param filePath
     * @return
     */
    public boolean newItemPhoto(String userId, String filePath){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Bootstrap.DATABASE);
            connection.setAutoCommit(false);

            // get itemId (new item id should be the largest in database)
            String sql = "SELECT MAX(itemId) FROM Item";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            String itemId = resultSet.getString("MAX(itemId)");

            sql = "UPDATE Item SET imgPath = ? WHERE itemId = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, filePath);
            statement.setString(2, itemId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getItemFilePath(String itemId){
        String filePath = "";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Bootstrap.DATABASE);
            connection.setAutoCommit(false);

            // get itemId (new item id should be the largest in database)
            String sql = "SELECT imgPath FROM Item WHERE itemId = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, itemId);
            resultSet = statement.executeQuery();
            filePath = resultSet.getString("imgPath");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
