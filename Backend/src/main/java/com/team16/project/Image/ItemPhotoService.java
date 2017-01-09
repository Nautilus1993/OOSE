package com.team16.project.Image;

import com.team16.project.Model.ItemPhotoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


public class ItemPhotoService {
    public static String itemPhotoDir = "./images/itemphotos/";
    private ItemPhotoDB itemPhotoDB;

    private final Logger logger = LoggerFactory.getLogger(ItemPhotoService.class);

    public ItemPhotoService(){
        this.itemPhotoDB = new ItemPhotoDB();
    }

    /**
     * uploadItemPhoto: for new user to upload their photo,
     * or existing user to update their photo.
     * @param userId
     * @param image
     * @return
     * @throws IOException
     */
    public boolean uploadItemPhoto(String userId, String image) throws IOException{
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageBytes = decoder.decodeBuffer(image);
        BufferedImage imageBuf = ImageIO.read(new ByteArrayInputStream(imageBytes));

        if(image == null){
            logger.debug("Buffered image is null");
        }

        String filepath = itemPhotoDir + itemPhotoDB.generateItemPhotoName() + ".png";
        System.out.println("New item photo path = " + filepath);
        File f = new File(filepath);
        if(f.exists()){
            // file exist overwrite.
            ImageIO.write(imageBuf, "png", f);
        }else{
            f.createNewFile();
            ImageIO.write(imageBuf, "png", f);
        }
        return itemPhotoDB.newItemPhoto(userId, filepath);
    }

    /**
     * getItemPhoto : return the string format of the given itemId
     * @param itemId
     * @return
     */
    public String getItemPhoto(String itemId){
        String itemImage = "";
        String filepath = itemPhotoDB.getItemFilePath(itemId);
        File f = new File(filepath);
        BASE64Encoder encoder = new BASE64Encoder();
        try{
            FileInputStream fs = new FileInputStream(f);
            byte imageBytes[] = new byte[(int) f.length()];
            fs.read(imageBytes);
            itemImage = encoder.encode(imageBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemImage;
    }
}