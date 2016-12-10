package com.team16.project.Image;

import com.team16.project.core.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static spark.Spark.*;

public class UploadUserPhotoController {
    private static final String USER_IMAGE_API = "/uploadimage";
    private final Logger logger = LoggerFactory.getLogger(UploadUserPhotoController.class);

    public UploadUserPhotoController(){
        setupEndpoints();
    }

    private void setupEndpoints(){
        post(USER_IMAGE_API, "application/json", (req, res)->{
            try{
                String imageReceived = req.body();
                System.out.println("Received UserPhoto from Client: " + imageReceived);
                return Collections.EMPTY_MAP;
            }catch (Exception e){
                e.printStackTrace();
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());
    }
}
