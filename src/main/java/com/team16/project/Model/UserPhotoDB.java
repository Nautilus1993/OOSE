package com.team16.project.Model;

import com.team16.project.core.Bootstrap;

import java.sql.*;
import java.util.HashMap;

public class UserPhotoDB {
    public boolean insertUserPhoto(String name, String image){
        Connection conn = null;
        Statement stm = null;

        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(Bootstrap.DATABASE);
            conn.setAutoCommit(false);
            System.out.println("Connect database successfully");
            stm = conn.createStatement();

            // query to insert an user photo
            String sql = "INSERT INTO Image (name, image) VALUES ("+ name + ", " + image + ");";
            //System.out.println(sql);
            //stm.executeUpdate(sql);


            stm.close();
            conn.commit();
            conn.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            return false;
        }
    }
}
