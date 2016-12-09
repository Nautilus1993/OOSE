package com.team16.project.Item;

import com.team16.project.core.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;

import static spark.Spark.post;


public class ItemDetailController {
    private static final String ITEM_DETAIL_API = "/itemdetail";
    private ItemDetailService itemDetailService;
    private final Logger logger = LoggerFactory.getLogger(ItemDetailController.class);

    public ItemDetailController(){
        this.itemDetailService = new ItemDetailService();
        setupEndpoints();
    }

    private void setupEndpoints(){
        post(ITEM_DETAIL_API + "/:sessionId", "application/json", (request, response) -> {
        //get(ITEM_DETAIL_API + "/:sessionId", "application/json", (request, response) -> {
            String itemId = request.params(":sessionId");
            try{
                HashMap<String, Object> item = itemDetailService.getItemDetailInfo(itemId);
                System.out.println("Item detail info: " + item);
                return item;
            }catch(ItemDetailService.itemDetailServiceException ex) {
                logger.error(String.format("Incorrect itemId: %s", itemId));
                response.status(404);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

    }


}
