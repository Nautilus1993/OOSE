package com.team16.project.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;


public class ItemPhotoService {
    public static String itemPhotoDir = "./images/itemphotos/";
    private ItemPhotoDB itemPhotoDB;
    public static int itemNumber;

    private final Logger logger = LoggerFactory.getLogger(ItemPhotoService.class);

    public ItemPhotoService(){
        this.itemPhotoDB = new ItemPhotoDB();
        itemNumber = itemPhotoDB.getCurrentItemNumber();
        System.out.println("current item number = " + itemNumber);
    }

    public boolean uploadItemPhoto(String userId, String image) throws IOException{
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageBytes = decoder.decodeBuffer(image);
        logger.debug("Decoded upload data : " + imageBytes.length);

        BufferedImage imageBuf = ImageIO.read(new ByteArrayInputStream(imageBytes));

        if(image == null){
            logger.debug("Buffered image is null");
        }

        String uploadFile = itemPhotoDir + "1.png";
        File f = new File(uploadFile);
        if(f.exists()){
            // file exist overwrite.
            ImageIO.write(imageBuf, "png", f);
        }else{
            f.createNewFile();
            ImageIO.write(imageBuf, "png", f);
        }
        return itemPhotoDB.newItemPhoto(userId, uploadFile);
    }
}
