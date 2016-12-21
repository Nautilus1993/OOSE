package com.team16.project.Image;

import com.team16.project.MyAccount.MyAccountController;
import com.team16.project.core.Bootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;

public class UserPhotoDB {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    private final Logger logger = LoggerFactory.getLogger(MyAccountController.class);

    public UserPhotoDB(){
        connection = null;
        statement = null;
        resultSet = null;
    }

    public boolean insertUserPhoto(String userId, String filePath){
        int uid = Integer.parseInt(userId);

        try{
            // connect to DB
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Bootstrap.DATABASE);
            connection.setAutoCommit(false);

            System.out.println("UserPhotoDB: Connect database successfully");

            /**
             * Hangbao: To decide which statement to use (insert or update),
             *        check if the imagepath is null or not for a given userId.
             */
            String sql = "SELECT userId, imgLink FROM UserDetail WHERE userId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, uid);
            resultSet = statement.executeQuery();


            if(resultSet.getString("imgLink") == null){
                /**
                 * The first time user upload a photo.
                 */
                sql = "UPDATE UserDetail SET imgLink = ?  WHERE userId = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, filePath);
                statement.setInt(2, uid);

                statement.executeUpdate();
                logger.info("User No." + userId +" upload a new photo.");
            }else{
                /**
                 * User update existing photo. We do not need to handle DB in this case.
                 * Because the new image will overwrite photo file in ./images/userphotos
                 */
                logger.info("User No." + userId +" change a photo.");
            }

            statement.close();
            connection.commit();
            connection.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return false;
    }

}
