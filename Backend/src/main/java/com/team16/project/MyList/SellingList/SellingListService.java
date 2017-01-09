package com.team16.project.MyList.SellingList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.team16.project.Item.Item;
import com.team16.project.subscribe.SubscribeService;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


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
        String sql = "SELECT * FROM Item WHERE sellerId = ?";
        JSONParser parser = new JSONParser();
        ResultSet rs = null;

        //result list
        ArrayList<Item> sellList = new ArrayList<Item>();

        //db
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
                    currItem.setAvialableDate(rs.getDate("avialableDate"));
                }
                if(rs.getString("expireDate") != null){
                    currItem.setExpireDate(rs.getDate("expireDate"));
                }
                sellList.add(currItem);
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
                logger.error("SellingListService.getSellingList: Failed to load entry", e);
                throw new SellingListServiceException("SellingListService.getSellingList: Failed to load entry", e);
            }
        }

        //return the result to response
        return sellList;
    }

    public void addPost(Request request, Response response) throws SellingListServiceException{
        Connection c = null;
        PreparedStatement stmt = null;
        try{
            //System.out.println("*********add post request received!" + request.body());

            //get request form info, collect by item object
            Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
            Item item = gson.fromJson(request.body(), Item.class);

            //connect to db, create sql, execute query
            String sql = "INSERT INTO item ( sellerId, name, description," +
                    "                        imgPath, postDate, avialableDate," +
                    "                       expireDate, price,category1," +
                    "                       category2,isDeliver,condition," +
                    "                       pickUpAddress, numOfLikes, contactOptions) " +
                    "             VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // ###


            c = DriverManager.getConnection("jdbc:sqlite:ProjectDB.db");
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, item.getSellerId());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getDesciption());
            stmt.setString(4, item.getImgLink());
            Date sqlPostDate = new Date(item.getPostDate().getTime());
            stmt.setDate(5, sqlPostDate);
            if(item.getAvialableDate() != null) {
                Date sqlAvailableDate = new Date(item.getAvialableDate().getTime());
                stmt.setDate(6, sqlAvailableDate);
            }
            if(item.getExpireDate() != null) {
                Date sqlExpireDate = new Date(item.getExpireDate().getTime());
                stmt.setDate(7, sqlExpireDate);
            }
            stmt.setDouble(8, item.getPrice());
            stmt.setString(9, item.getCategory1());
            stmt.setString(10, item.getCategory2());
            stmt.setBoolean(11, item.isDeliver());
            stmt.setString(12, item.getCondition());
            stmt.setString(13, item.getPickUpAddress());
            stmt.setInt(14, 0);
            stmt.setString(15, item.getContactMethods().toString());

            Integer stat = stmt.executeUpdate();
            new SubscribeService().listUser(item.getCategory1(), item.getCategory2());
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
        //System.out.println("***********edit request received!");

        //get item info from db
        Connection c = null;
        String sql = "SELECT * FROM item WHERE itemId = ? ";
        PreparedStatement stmt = null;
        Item resultItem = null;
        ResultSet rs = null;

        try {
            //get item id from request
            Integer itemId = Integer.valueOf(request.params(":itemId"));
            c = DriverManager.getConnection("jdbc:sqlite:ProjectDB.db");
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, itemId);
            rs = stmt.executeQuery();

            // load data to result set
            while(rs.next()){
                resultItem = new Item();
                resultItem.setItemId(rs.getInt("itemId"));
                resultItem.setSellerId(rs.getInt("sellerId"));
                resultItem.setName(rs.getString("name"));
                resultItem.setPrice(rs.getDouble("price"));
                resultItem.setCategory1(rs.getString("category1"));
                resultItem.setCategory2(rs.getString("category2"));
                resultItem.setImgLink(rs.getString("imgPath"));
                resultItem.setCondition(rs.getString("condition"));
                resultItem.setDeliver(rs.getBoolean("isDeliver"));
                resultItem.setDescription(rs.getString("description"));
                if(rs.getString("avialableDate") != null){
                    resultItem.setAvialableDate(rs.getDate("avialableDate"));
                }
                if(rs.getString("expireDate") != null){
                    resultItem.setExpireDate(rs.getDate("expireDate"));
                }
                resultItem.setNumOfLikes(rs.getInt("numOfLikes"));
                ArrayList<String> contacts = new Gson().fromJson(rs.getString("contactOptions"), ArrayList.class);
                resultItem.setContactMethods(contacts);
            }

        } catch(SQLException e) {
            e.printStackTrace();
            logger.error("SellingListService.editPost: Failed to fetch data", e);
            throw new SellingListServiceException("SellingListService.editPost: Failed to fetch data", e);
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            try{
                rs.close();
                stmt.close();
                c.close();
            }catch(SQLException e){
                logger.error("SellingListService.editPost: Failed to fetch data", e);
                throw new SellingListServiceException("SellingListService.editPost: Failed to fetch data", e);
            }
        }
        //return item object
        return resultItem;
    }

    public void updatePost(Request request, Response response) throws SellingListServiceException{
        Connection c = null;
        PreparedStatement stmt = null;
        try{
            //System.out.println("****update request received!" + request.body());

            //get request form info, collect by item object
            Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
            Item item = gson.fromJson(request.body(), Item.class);

            //connect to db, create sql, execute query
            String sql = "UPDATE item set sellerId = ?, name = ?, description = ?," +
                    "                       imgPath = ?, postDate =?, avialableDate = ?," +
                    "                       expireDate = ?, price = ?, category1 = ?," +
                    "                       category2 = ?, isDeliver = ?, condition = ?," +
                    "                       pickUpAddress = ?, numOfLikes = ?, contactOptions = ? ";


            c = DriverManager.getConnection("jdbc:sqlite:ProjectDB.db");
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, item.getSellerId());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getDesciption());
            stmt.setString(4, item.getImgLink());
            Date sqlPostDate = new Date(item.getPostDate().getTime());
            stmt.setDate(5, sqlPostDate);
            if(item.getAvialableDate() != null) {
                Date sqlAvailableDate = new Date(item.getAvialableDate().getTime());
                stmt.setDate(6, sqlAvailableDate);
            }
            if(item.getExpireDate() != null) {
                Date sqlExpireDate = new Date(item.getExpireDate().getTime());
                stmt.setDate(7, sqlExpireDate);
            }
            stmt.setDouble(8, item.getPrice());
            stmt.setString(9, item.getCategory1());
            stmt.setString(10, item.getCategory2());
            stmt.setBoolean(11, item.isDeliver());
            stmt.setString(12, item.getCondition());
            stmt.setString(13, item.getPickUpAddress());
            stmt.setInt(14, 0);
            stmt.setString(15, item.getContactMethods().toString());

            Integer stat = stmt.executeUpdate();
            new SubscribeService().listUser(item.getCategory1(), item.getCategory2());
        }catch(SQLException e) {
            logger.error("SellingListService.updatePost: Failed to update entry", e);
            throw new SellingListServiceException("SellingListService.updatePost: Failed to update entry", e);
        }catch(JsonSyntaxException e){
            logger.error("SellingListService.updatePost: Failed to parse json", e);
            throw new SellingListServiceException("SellingListService.updatePost: Failed to update entry", e);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
                c.close();
            }catch (SQLException e){
                logger.error("SellingListService.updatePost: Failed to update entry", e);
                throw new SellingListServiceException("SellingListService.updatePost: Failed to update entry", e);
            }
        }
    }


    public void removePost(Request request, Response response) throws SellingListServiceException{
        //System.out.println("remove request received!");
        Connection c = null;
        PreparedStatement stmt = null;

        try{
            //get itemId from request info
            int itemId = Integer.valueOf(request.params(":itemId"));

            //connect to db, create sql, execute query
            String sql = "DELETE FROM Item WHERE itemId = ?";

            c = DriverManager.getConnection("jdbc:sqlite:ProjectDB.db");
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, itemId);

            Integer stat = stmt.executeUpdate();
        }catch(SQLException e) {
            logger.error("SellingListService.removePost: Failed to remove entry", e);
            throw new SellingListServiceException("SellingListService.removePost: Failed to remove entry", e);
        }catch(JsonSyntaxException e){
            logger.error("SellingListService.removePost: Failed to parse json", e);
            throw new SellingListServiceException("SellingListService.removePost: Failed to remove entry", e);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
                c.close();
            }catch (SQLException e){
                logger.error("SellingListService.removePost: Failed to remove entry", e);
                throw new SellingListServiceException("SellingListService.removePost: Failed to remove entry", e);
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
