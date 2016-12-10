package com.team16.project.Model;

import com.team16.project.core.Bootstrap;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemListDB {
    public ArrayList<HashMap<String,Object>> getItemListInfo(String topCategory, String subCategory){
        Connection conn = null;
        PreparedStatement stm = null;
        ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>();
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(Bootstrap.DATABASE);
            conn.setAutoCommit(false);
            System.out.println("Connect database successfully");
            //stm = conn.createStatement();

            String sql = "SELECT name, itemId, price, category1, category2, isDeliver, imgPath, condition " +
                    "FROM Item " +
                    "WHERE category1 = ? AND category2 = ?";

            stm = conn.prepareStatement(sql);
            stm.setString(1, topCategory);
            stm.setString(2, subCategory);

            ResultSet results = stm.executeQuery();

            if(!results.isBeforeFirst()){
                System.out.println("Item does not exist!");
            }else{
                while (results.next()) {
                    HashMap<String, Object> singleItem = new HashMap<>();
                    singleItem.put("name", results.getString("name"));
                    singleItem.put("price", results.getString("price"));
                    singleItem.put("category1", results.getString("category1"));
                    singleItem.put("category2", results.getString("category2"));
                    singleItem.put("isDeliver", results.getString("isDeliver"));
                    singleItem.put("imgPath", results.getString("imgPath"));
                    singleItem.put("condition", results.getString("condition"));
                    itemList.add(singleItem);
                    System.out.println("Find item: " + results.getString("itemId"));
                }
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
