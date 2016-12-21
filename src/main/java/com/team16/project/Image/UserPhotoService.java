package com.team16.project.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;

public class UserPhotoService {
    public static String userPhotoDir = "./images/userphotos/";
    private UserPhotoDB userPhotoDB;
    private final Logger logger = LoggerFactory.getLogger(UserPhotoService.class);

    public UserPhotoService(){
        this.userPhotoDB = new UserPhotoDB();
    }

    /**
     * For user to upload photo from "Me" page. Store the image as PNG format.
     * @param userId
     * @param image
     * @return
     * @throws IOException
     * @throws UserPhotoServiceException
     */
    public boolean uploadUserPhoto(String userId, String image) throws IOException, UserPhotoServiceException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageBytes = decoder.decodeBuffer(image);
        logger.debug("Decoded upload data : " + imageBytes.length);

        BufferedImage imageBuf = ImageIO.read(new ByteArrayInputStream(imageBytes));

        if(image == null){
            logger.debug("Buffered image is null");
        }

        String uploadFile = userPhotoDir + userId +".png";
        System.out.println("Image file path = " + uploadFile);
        File f = new File(uploadFile);
        if(f.exists()){
            ImageIO.write(imageBuf, "png", f);
        }else {
            f.createNewFile();
            ImageIO.write(imageBuf, "png", f);
        }
        return userPhotoDB.insertUserPhoto(userId, uploadFile);
    }


    /**
     * @param userId
     * @return
     * @throws UserPhotoServiceException
     *
     * Hangbao: Each time user login and visited "Me" page,
     * App send the latest_modified time for a user_X's photo, server will
     * compared it with the date of "./images/userphoto/X.png". If they are
     * same, then server acknowledge client just use its' local photo. Otherwise,
     * server send the image in string format back to client(Android app).
     *
     * The reason for using "latest update time" as response is to save the
     * bandwidth and reduce the unnecessary network traffic. Here we assume
     * that user would not frequently change the photo. Thus App can always
     * get user's photo locally, instead of downloading it from server.
     */
    public String downloadUserPhoto(String userId, String time) throws UserPhotoServiceException{
        // here can also get photo file path from database.
        String filepath = userPhotoDir + userId + ".png";
        String encodedImageStr = "";

        /**
         * Get photo file last modified time
         * If not equal to time from frontend ,return null
         */
        File f = new File(filepath);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy-HH-mm");
        String modifiedTime = format.format(f.lastModified());
        if (time == modifiedTime){
            System.out.println("File modified time is same, user old file.");
            return null;
        }

        //define image file encoder : image file --> string
        BASE64Encoder encoder = new BASE64Encoder();

         //read an image file from local file system
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
        return encodedImageStr;
    }


    public class UserPhotoServiceException extends Exception{
        public UserPhotoServiceException(String message, Throwable cause){
            super(message, cause);
        }
    }
}