package com.team16.project.Image;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static spark.Spark.*;

public class ItemPhotoController {
    private static final String ITEM_IMAGE_API = "/itemimage";
    private final Logger logger = LoggerFactory.getLogger(ItemPhotoController.class);
    private ItemPhotoService itemPhotoService;

    public ItemPhotoController(){
        this.itemPhotoService = new ItemPhotoService();
        setupEndpoints();
    }

    private void setupEndpoints(){
        /* --------  1 : For Seller upload item photo  -------  */
        post(ITEM_IMAGE_API + "/upload/:sessionId", "application/json", (req, res) -> {
            try{
                String imageReceived = req.body();

                // Transfer a string to a json
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(imageReceived);
                JsonObject json = element.getAsJsonObject();

                // get info from json object
                String itemImage = json.get("image").getAsString();


                System.out.println("Item Image Length = " + itemImage.length());
            }catch (Exception e){
                e.printStackTrace();
            }
            return Collections.EMPTY_MAP;
        });

        // Todo:
        /* --------  2 : ItemDetail display item image  -------  */

        /* --------  3 : ItemList display list of images  -------  */
    }
}
