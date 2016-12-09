package com.team16.project.sqlite;

import com.team16.project.core.Bootstrap;

import java.sql.*;
import java.util.HashMap;

public class ItemListDB {
    public HashMap<String, Object> getItemListInfo(String topCategory, String subCategory){
        Connection conn = null;
        PreparedStatement stm = null;
        HashMap<String, Object> itemList = new HashMap<String, Object>();
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(Bootstrap.DATABASE);
            conn.setAutoCommit(false);
            System.out.println("Connect database successfully");
            //stm = conn.createStatement();

            String sql = "SELECT name, itemId, price, category1, category2, isDeliver " +
                    "FROM Item " +
                    "WHERE category1 = ? AND category2 = ?";

            stm = conn.prepareStatement(sql);
            stm.setString(1, topCategory);
            stm.setString(2, subCategory);

            ResultSet results = stm.executeQuery();

            if(!results.isBeforeFirst()){
                System.out.println("Item does not exist!");
            }else{
                itemList.put("name", results.getString("name"));
                itemList.put("price", results.getString("price"));
                itemList.put("category1", results.getString("category1"));
                itemList.put("category2", results.getString("category2"));
                itemList.put("isDeliver", results.getString("isDeliver"));
                System.out.println("Find item: " + results.getString("itemId"));
            }

            results.close();
            stm.close();
            conn.close();
            return itemList;

        }catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
        return itemList;
    }
}
