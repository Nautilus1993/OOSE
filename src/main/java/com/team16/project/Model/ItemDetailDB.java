package com.team16.project.Model;

import com.team16.project.core.Bootstrap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

public class ItemDetailDB {
    public HashMap<String, Object> getItemDetailInfo(String itemId){
        Connection conn = null;
        Statement stm = null;
        HashMap<String, Object> itemDetail = new HashMap<String, Object>();
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(Bootstrap.DATABASE);
            conn.setAutoCommit(false);
            System.out.println("Connect database successfully");
            stm = conn.createStatement();

            String sql = "SELECT name, itemId, price, category1, category2, "
            		+ "condition, isDeliver, pickUpAddress, description, "
            		+ "avialableDate, expireDate "
                    + "FROM Item "
                    + "WHERE itemId = '" + itemId + "';";
            
            ResultSet results = stm.executeQuery(sql);

            if(!results.isBeforeFirst()){
                System.out.println("Item does not exist!");
            }else{
                itemDetail.put("name", results.getString("name"));
                itemDetail.put("price", results.getString("price"));
                itemDetail.put("category1", results.getString("category1"));
                itemDetail.put("category2", results.getString("category2"));
                itemDetail.put("condition", results.getString("condition"));
                itemDetail.put("isDeliver", results.getString("isDeliver"));              
                itemDetail.put("pickUpAddress", results.getString("pickUpAddress"));
                itemDetail.put("description", results.getString("description"));
                itemDetail.put("avialableDate", results.getString("avialableDate"));
                itemDetail.put("expireDate", results.getString("expireDate"));
                
                System.out.println("Find item: " + results.getString("itemId"));
            }
            results.close();
            stm.close();
            conn.close();
            return itemDetail;

        }catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
        return itemDetail;
    }
}
