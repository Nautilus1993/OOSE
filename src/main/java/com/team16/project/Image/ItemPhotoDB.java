package com.team16.project.Image;

import com.team16.project.core.Bootstrap;

import java.sql.*;

import static com.team16.project.Image.ItemPhotoService.itemNumber;

public class ItemPhotoDB {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public ItemPhotoDB(){
        connection = null;
        statement = null;
        resultSet = null;
    }

    public int getCurrentItemNumber(){
        int itemNumber = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Bootstrap.DATABASE);
            connection.setAutoCommit(false);

            String sql = "select count(itemId) from Item";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            itemNumber = resultSet.getInt("count(itemId)");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemNumber;
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

            String sql = "UPDATE Item SET imgPath = ? WHERE itemId = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, filePath);
            statement.setInt(2, itemNumber);
            statement.executeUpdate();
            System.out.println("Post an item: image stored in " + filePath );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
