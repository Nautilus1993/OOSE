package com.team16.project.Image;

import com.team16.project.Model.UserPhotoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class UserPhotoService {
    public static String userPhotoDir = "./images/userphoto/";
    private UserPhotoDB userPhotoDB;
    private final Logger logger = LoggerFactory.getLogger(UserPhotoService.class);

    public UserPhotoService(){
        this.userPhotoDB = new UserPhotoDB();
    }

    public boolean uploadUserPhoto(String name, String image) throws IOException {
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

    public String downloadUserPhoto(String name){
        String filepath = userPhotoDir + name;
        String encodedImageStr = "";

        /**
         * define image file encoder : image file --> string
         */
        BASE64Encoder encoder = new BASE64Encoder();

        /**
         * read an image file from local file system, before reading image should be compressed.
         */
        File f = new File(filepath);
        try {
            FileInputStream fs = new FileInputStream(f);
            byte imageBytes[] = new byte[(int) f.length()];
            fs.read(imageBytes);
            encodedImageStr = encoder.encode(imageBytes);
        } catch (FileNotFoundException e) {
            // catch this exception when new a FileInputStream with an invalid file path.
            e.printStackTrace();
        } catch (IOException e) {
            // for fs.read(imageBytes)
            e.printStackTrace();
        }
        System.out.println("encoded image = " + encodedImageStr);
        return encodedImageStr;
    }
}