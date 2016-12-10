package com.team16.project.Image;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team16.project.core.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static spark.Spark.*;

public class UserPhotoController {
    private static final String USER_IMAGE_API = "/image";
    private final Logger logger = LoggerFactory.getLogger(UserPhotoController.class);
    private UserPhotoService userPhotoService;

    public UserPhotoController(){
        this.userPhotoService = new UserPhotoService();
        setupEndpoints();
    }

    private void setupEndpoints(){
        post(USER_IMAGE_API + "/upload", "application/json", (req, res)->{
            try{
                String imageReceived = req.body();

                // transfer a string to a json
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(imageReceived);
                JsonObject json = element.getAsJsonObject();

                // get info from json object
                String name = json.get("name").getAsString();
                String image = json.get("image").getAsString();

                boolean result = userPhotoService.uploadUserPhoto(name, image);
                System.out.println("result = " + result);
                return Collections.EMPTY_MAP;
            }catch (Exception e){
                e.printStackTrace();
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        get(USER_IMAGE_API + "/download/:imagename", "application/json", (req, res) -> {
            try{
                String imageName = req.params(":imagename");
                System.out.println("request image name: " + imageName);
                return this.userPhotoService.downloadUserPhoto(imageName);
            }catch(Exception e){
                e.printStackTrace();
                return  Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

    }
}
