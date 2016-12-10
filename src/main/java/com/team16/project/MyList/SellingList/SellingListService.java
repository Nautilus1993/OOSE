package com.team16.project.MyList.SellingList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.team16.project.Item.Item;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by lxx on 11/9/16.
 */
public class SellingListService {
    private final Logger logger = LoggerFactory.getLogger(SellingListService.class);

    public ArrayList<Item> getSellingList(Request request) throws SellingListServiceException{
        //get the userId from request
        Integer userId = Integer.valueOf(request.params(":sessionId"));

        //connect to db, create sql, query the result
        Connection c = null;
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM Item where itemId in (SELECT itemId FROM SellingList WHERE userId = ?)";
        JSONParser parser = new JSONParser();
        ResultSet rs = null;

        //result list
        ArrayList<Item> sellList = new ArrayList<Item>();


        try {
            c = DriverManager.getConnection("jdbc:sqlite:ProjectDB.db");
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

            while(rs.next()){
                Item currItem = new Item();
                currItem.setItemId(rs.getInt("itemId"));
                currItem.setSellerId(rs.getInt("sellerId"));
                currItem.setName(rs.getString("name"));
                currItem.setPrice(rs.getDouble("price"));
                currItem.setImgLink(rs.getString("imgPath"));
                currItem.setCondition(rs.getString("condition"));
                currItem.setDeliver(rs.getBoolean("isDeliver"));
                if(rs.getString("avialableDate") != null){
                    currItem.setAvialableDate(sdf.parse(rs.getString("avialableDate")));
                }
                if(rs.getString("expireDate") != null){
                    currItem.setExpireDate(sdf.parse(rs.getString("expireDate")));
                }
                sellList.add(currItem);

//                System.out.println("itemId: " + rs.getInt("itemId"));
//                System.out.println("availableDate: " + rs.getString("avialableDate"));
//                System.out.println("pickupAddress: " + rs.getString("pickUpAddress"));
            }


            //return 0;
        }
        catch (Exception e) {
            System.out.println("Failed to fetch data");
            e.printStackTrace();
        } finally{
            try{
                rs.close();
                stmt.close();
                c.close();
            }catch(SQLException e){
                logger.error("SellingListService.addPost: Failed to create new entry", e);
                throw new SellingListServiceException("SellingListService.addPost: Failed to create new entry", e);
            }
        }

        //return the result to response
        return sellList;
    }

    public void addPost(Request request, Response response) throws SellingListServiceException{
        Connection c = null;
        PreparedStatement stmt = null;
        try{
            System.out.println(request.body());

            //get request form info, collect by item object
            Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
            Item item = gson.fromJson(request.body(), Item.class);
            System.out.println("******desc: "+ item.getDesciption());

            //connect to db, create sql, execute query
            String sql = "INSERT INTO item ( sellerId, name, description," +
                    "                        imgPath, postDate, avialableDate," +
                    "                       expireDate, price,category1," +
                    "                       category2,isDeliver,condition," +
                    "                       pickUpAddress, numOfLikes) " +
                    "             VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // ###


            c = DriverManager.getConnection("jdbc:sqlite:ProjectDB.db");
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, item.getSellerId());//how to bind an object to a sql here?
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getDesciption());
            stmt.setString(4, item.getImgLink());
            Date sqlPostDate = new Date(item.getPostDate().getTime());
            stmt.setDate(5, sqlPostDate);
            Date sqlAvailableDate = new Date(item.getAvialableDate().getTime());
            stmt.setDate(6, sqlAvailableDate);
            Date sqlExpireDate = new Date(item.getExpireDate().getTime());
            stmt.setDate(7, sqlExpireDate);
            stmt.setDouble(8, item.getPrice());
            stmt.setString(9, item.getCategory1());
            stmt.setString(10, item.getCategory2());
            stmt.setBoolean(11, item.isDeliver());
            stmt.setString(12, item.getCondition());
            stmt.setString(13, item.getPickUpAddress());
            stmt.setInt(14, 0);

            Integer stat = stmt.executeUpdate();
            System.out.println(stat);//check update status
        }catch(SQLException e) {
            logger.error("SellingListService.addPost: Failed to create new entry", e);
            throw new SellingListServiceException("SellingListService.addPost: Failed to create new entry", e);
        }catch(JsonSyntaxException e){
            logger.error("SellingListService.addPost: Failed to parse json", e);
            throw new SellingListServiceException("SellingListService.addPost: Failed to create new entry", e);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
                c.close();
            }catch (SQLException e){
                logger.error("SellingListService.addPost: Failed to create new entry", e);
                throw new SellingListServiceException("SellingListService.addPost: Failed to create new entry", e);
            }
        }
    }

    public Item editPost(Request request, Response response) throws  SellingListServiceException{
        //get item id from request
        String itemId = request.params(":itemId");

        //get item info from db
        Connection c = null;
        String sql = "SELECT * FROM item WHERE itemId = ? ";
        PreparedStatement stmt = null;
        Item resultItem = null;
        ResultSet rs = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:ProjectDB.db");
            stmt = c.prepareStatement(sql);
            stmt.setString(1, itemId);
            rs = stmt.executeQuery();
            resultItem = (Item)rs;//how to transform result
            System.out.println(rs);//check result
        } catch(SQLException e) {
            logger.error("SellingListService.editPost: Failed to fetch data", e);
            throw new SellingListServiceException("SellingListService.editPost: Failed to fetch data", e);
        }finally{
            try{
                rs.close();
                stmt.close();
                c.close();
            }catch(SQLException e){
                logger.error("SellingListService.editPost: Failed to fetch data", e);
                throw new SellingListServiceException("SellingListService.editPost: Failed to fetch data", e);
            }
        }
        //return item
        return resultItem;
    }


    public void updatePost(Request request, Response response) throws  SellingListServiceException{
        //get item id and item object
        String itemId = request.params(":itemId");
        Item item = new Gson().fromJson(request.body(), Item.class);

        //connect to db, create sql, execute query
        Connection c = null;
        String sql = "UPDATE item SET " +
                "       topLevel = ?, " +
                "       secondLevel = ?, " +
                "       name = ? " +
                "     WHERE itemId = ? ";
        PreparedStatement stmt = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:ProjectDB.db");
            stmt = c.prepareStatement(sql);
            stmt.setString(1, item.getCategory1());//how to bind an object to a sql here?
            stmt.setString(2, item.getCategory2());
            stmt.setString(3, item.getName());
            Integer stat = stmt.executeUpdate();
            System.out.println(stat);//check update status
        } catch(SQLException e) {
            logger.error("SellingListService.updatePost: Failed to update!", e);
            throw new SellingListServiceException("SellingListService.updatePost: Failed to update!", e);
        } finally{
            try{
                stmt.close();
                c.close();
            }catch(SQLException e){
                logger.error("SellingListService.editPost: Failed to fetch data", e);
                throw new SellingListServiceException("SellingListService.editPost: Failed to fetch data", e);
            }
        }
    }



    //-----------------------------------------------------------------------------//
    // Helper Classes and Methods
    //-----------------------------------------------------------------------------//

    public static class SellingListServiceException extends Exception {
        public SellingListServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
