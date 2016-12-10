package com.team16.project.Model;

import com.team16.project.User.User;
import com.team16.project.core.Bootstrap;

import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * This class is used to interact with database.
 * @author OOSE_Team16
 */
public class MyAccountDB {
    /**
     * Method to show user's detail info. in the database.
     * @param userId : userId.
     * @return If the user exists, return a hashMap containing required information, otherwise return an empty hashMap.
     */
    public HashMap<String, Object> searchUserDetailInfo(String userId){
        Connection conn = null;
        Statement sta = null;
        HashMap<String, Object> userDetail = new HashMap<String, Object>();
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(Bootstrap.DATABASE);
            conn.setAutoCommit(false);
            System.out.println("Connect database successfully");
            sta = conn.createStatement();

            String sql = "SELECT username, email, address, phone, zipCode "
                    + "FROM UserDetail INNER JOIN ContactInfo "
                    + "ON UserDetail.contactId = ContactInfo.contactId "
                    + "WHERE userId = '" + userId + "';";

            ResultSet results = sta.executeQuery(sql);

            if (!results.isBeforeFirst()) {
                System.out.println("The user dose not exist!");
            } else {
                userDetail.put("userId", userId);
                userDetail.put("username", results.getString("username"));
                userDetail.put("email", results.getString("email"));
                userDetail.put("phone", results.getString("phone"));
                userDetail.put("address", results.getString("address"));
                userDetail.put("zipCode", results.getString("zipCode"));
            	/*
            	userDetail.put("city", results.getString("city"));
            	userDetail.put("state", results.getString("state"));
            	userDetail.put("imgLink", results.getString("imgLink"));
            	userDetail.put("facebook", results.getString("facebook"));
            	*/
                System.out.println("Find UserDetail");
            }
            results.close();
            sta.close();
            conn.close();
            return userDetail;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
        return userDetail;
    }

    /**
     * Method to update user's detail info. in the database.
     * If the contact info. of the user exists, we update the information in database
     * If the user didn't fill the detail, we insert a new tuple to the database
     * @param sessionId : userId.
     * @return return true if success, otherwise false.
     */

    public Boolean updateAccountInfo(String sessionId, User user) {
        Connection conn = null;
        PreparedStatement sta_udetail = null;
        PreparedStatement sta_contact = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(Bootstrap.DATABASE);
            conn.setAutoCommit(true);
            System.out.println("Connect database successfully");

            // update userDetail table
            String sql_uDetail = "UPDATE UserDetail SET username = ? WHERE userId = " + sessionId + ";";
            sta_udetail = conn.prepareStatement(sql_uDetail);
            sta_udetail.setString(1, user.getUsername());

            int rowsUpdated_uDetail = sta_udetail.executeUpdate();


            // update ContactInfo table
            String sql_contact = "UPDATE ContactInfo SET email = ?, phone = ?, address = ?, zipCode = ? "
                    + "WHERE contactId IN (SELECT contactId FROM UserDetail WHERE userId = " + sessionId + ");";

            sta_contact = conn.prepareStatement(sql_contact);
            sta_contact.setString(1, user.getEmail());
            sta_contact.setString(2, user.getPhoneNum());
            sta_contact.setString(3, user.getAddress());
            sta_contact.setString(4, user.getZipCode());

            int rowsUpdated_contact = sta_contact.executeUpdate();

            sta_contact.close();
            conn.close();

            if (rowsUpdated_uDetail > 0 && rowsUpdated_contact > 0) {
                System.out.println("An existing user info. was updated successfully!");
                return true;
            } else {
                System.out.println("Update Fail");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
        return false;
    }
}

