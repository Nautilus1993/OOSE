package com.team16.project.User.MyList;

import com.team16.project.Item.Item;
import com.team16.project.core.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by lxx on 11/9/16.
 */
public class SellingListController {

    private final SellingListService service;
    private static final String API_CONTEXT = "/sellList";
    private final Logger logger = LoggerFactory.getLogger(SellingListController.class);

    /**
     *  Constructor
     */
    public SellingListController(SellingListService service) throws
            SellingListService.SellingListServiceException{
        this.service = service;
        actions();
    }

    public void actions(){
        //TEST API
        get("/helloworld", "application/json", (request, response) -> {
            try {
                System.out.println("Request received!!!!");
                ArrayList<Item> itemList = new ArrayList<Item>();
                Item item = new Item();
                item.setName("BED");
                item.setPrice(10);
                item.setCondition("Like new");
                item.setDeliver(Boolean.TRUE);
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy/mm/dd");
                item.setAvialableDate(fmt.parse("2016/11/18"));
                item.setExpireDate(fmt.parse("2016/11/31"));
                ArrayList<String> contacts = new ArrayList<String>();
                contacts.add("text");
                contacts.add("wechat");
                item.setContactMethods(contacts);
                itemList.add(item);
                Item item1 = new Item();
                item1.setName("Brand New Chair");
                item1.setPrice(123.5);
                item1.setCondition("New");
                SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy/mm/dd");
                item1.setAvialableDate(fmt1.parse("2016/10/25"));
                item1.setContactMethods(new ArrayList<>());
                item1.setImgLink("http://123.com/456.png");
                itemList.add(item1);
                return itemList;
            } catch(Exception e){
                logger.error("Failed to create new entry");
                response.status(500);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());


        //Display an user's selling list
        get(API_CONTEXT + "/display/:sessionId", "application/json", (request, response) -> {
            try {
                System.out.println("Request received!!!!");
                ArrayList<Item> sellList = service.getSellingList(request);
                return sellList;
            } catch(Exception e){
                logger.error("Failed to create new entry");
                response.status(500);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        //Post an item
        post(API_CONTEXT + "/post/:sessionId", "application/json", (request, response) -> {
            try {
                service.addPost(request, response);
                response.status(200);
            } catch(SellingListService.SellingListServiceException e){
                logger.error("Failed to add new post");
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        //Edit an posted item
        post(API_CONTEXT + "/edit/:itemId", "application/json", (request, response) -> {
            try{
                Item item = service.editPost(request, response);
                response.status(200);
                return item;
            }catch(SellingListService.SellingListServiceException e){
                logger.error("failed to get item info");
                response.status(500);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        //update an posted item
        post(API_CONTEXT + "/update/:itemId", "application/json", (request, response) -> {
            try{
                service.updatePost(request, response);
                response.status(200);
            }catch(SellingListService.SellingListServiceException e){
                logger.error("failed to get item info");
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

    }
}
