package com.team16.project.ItemList;

import com.team16.project.core.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;

import static spark.Spark.post;


public class ItemListController {
    private static final String ITEM_LIST_API = "/itemlist";
    private ItemListService itemListService;
    private final Logger logger = LoggerFactory.getLogger(ItemListController.class);

    public ItemListController(){
        this.itemListService = new ItemListService();
        setupEndpoints();
    }

    private void setupEndpoints(){
        post(ITEM_LIST_API + "/:TopCategory/:SubCategory", "application/json", (request, response) -> {
            String topCategory = request.params(":TopCategory");
            String subCategory = request.params(":SubCategory");
            try{
                HashMap<String, Object> item = itemListService.getItemListInfo(topCategory, subCategory);
                System.out.println("Item detail info: " + item);
                return item;
            }catch(ItemListService.itemListServiceException ex) {
                logger.error(String.format("Incorrect item category"));
                response.status(404);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());
    }


}
