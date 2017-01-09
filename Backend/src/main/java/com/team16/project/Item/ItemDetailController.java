package com.team16.project.Item;

import com.team16.project.core.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;

import static spark.Spark.*;


public class ItemDetailController {
    private static final String ITEM_DETAIL_API = "/itemdetail";
    private ItemDetailService itemDetailService;
    private final Logger logger = LoggerFactory.getLogger(ItemDetailController.class);

    public ItemDetailController(){
        this.itemDetailService = new ItemDetailService();
        setupEndpoints();
    }

    private void setupEndpoints(){
        get(ITEM_DETAIL_API + "/:userId/:itemId", "application/json", (request, response) -> {
            String userId = request.params(":userId");
            String itemId = request.params(":itemId");
            try{
                HashMap<String, Object> item = itemDetailService.getItemDetailInfo(itemId, userId);
                System.out.println("Item detail info: " + item);         
                return item;
            }catch(ItemDetailService.itemDetailServiceException ex) {
                logger.error(String.format("Incorrect itemId: %s", itemId));
                response.status(404);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        post(ITEM_DETAIL_API + "/like/:userId/:itemId", "application/json", (request, response) -> {
            String userId = request.params(":userId");
            String itemId = request.params(":itemId");
            try{
                itemDetailService.addLikeToWishList(itemId, userId);
                System.out.println("WishList Updates Successfully!");
                return Collections.EMPTY_MAP;
            }catch(ItemDetailService.itemDetailServiceException ex) {
                logger.error(String.format("Incorrect itemId: %s", itemId));
                response.status(404);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        post(ITEM_DETAIL_API + "/dislike/:userId/:itemId", "application/json", (request, response) -> {
            String userId = request.params(":userId");
            String itemId = request.params(":itemId");
            try{
                itemDetailService.deleteLikeFromWishList(itemId, userId);
                System.out.println("WishList Updates Successfully!");
                return Collections.EMPTY_MAP;
            }catch(ItemDetailService.itemDetailServiceException ex) {
                logger.error(String.format("Incorrect itemId: %s", itemId));
                response.status(404);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());
    }
}
