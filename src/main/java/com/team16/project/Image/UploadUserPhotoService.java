package com.team16.project.Image;

import com.team16.project.Model.UserPhotoDB;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadUserPhotoService {
    private UserPhotoDB userPhotoDB;
    private final Logger logger = LoggerFactory.getLogger(UploadUserPhotoService.class);

    public UploadUserPhotoService(){
        this.userPhotoDB = new UserPhotoDB();
    }

    public boolean insertUserPhoto(String name, String image){
        return userPhotoDB.insertUserPhoto(name, image);
    }

}
