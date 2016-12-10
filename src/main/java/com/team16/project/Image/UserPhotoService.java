package com.team16.project.Image;

import com.team16.project.Model.UserPhotoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class UserPhotoService {
    public static String userPhotoDir = "./images/userphoto/";
    private UserPhotoDB userPhotoDB;
    private final Logger logger = LoggerFactory.getLogger(UserPhotoService.class);

    public UserPhotoService(){
        this.userPhotoDB = new UserPhotoDB();
    }

    public boolean insertUserPhoto(String name, String image) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageBytes = decoder.decodeBuffer(image);
        logger.debug("Decoded upload data : " + imageBytes.length);



        BufferedImage imageBuf = ImageIO.read(new ByteArrayInputStream(imageBytes));

        if(image == null){
            logger.debug("Buffered image is null");
        }

        String uploadFile = userPhotoDir + "user.jpg";
        logger.debug("Image file path = " + uploadFile);
        File f = new File(uploadFile);
        ImageIO.write(imageBuf, "png", f);

        return userPhotoDB.insertUserPhoto(name, image);
    }


}
