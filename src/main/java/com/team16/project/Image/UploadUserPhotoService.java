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
import java.util.Base64;

public class UploadUserPhotoService {
    public static String userPhotoDir = "";
    private UserPhotoDB userPhotoDB;
    private final Logger logger = LoggerFactory.getLogger(UploadUserPhotoService.class);

    public UploadUserPhotoService(){
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

        String uploadFile = "./user.png";
        logger.debug("Image file path = " + uploadFile);
        File f = new File(uploadFile);
        ImageIO.write(imageBuf, "png", f);

        return userPhotoDB.insertUserPhoto(name, image);
    }

}
