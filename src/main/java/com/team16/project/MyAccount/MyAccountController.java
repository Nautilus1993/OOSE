package com.team16.project.MyAccount;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.xml.internal.ws.client.BindingProviderProperties;
import com.team16.project.Image.UserPhotoService;
import com.team16.project.core.JsonTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;



public class MyAccountController {

    private static final String API_CONTEXT = "/MyAccount";

    /**
     * Hangbao: here we need 2 services to separately deal with
     *          user's basic information and his photo.
     */
    public final MyAccountService myAccountService;
    public final UserPhotoService userPhotoService;

    private final Logger logger = LoggerFactory.getLogger(MyAccountController.class);

    public MyAccountController() {
        this.myAccountService = new MyAccountService();
        this.userPhotoService = new UserPhotoService();
        setupEndpoints();
    }

    private void setupEndpoints() {
        /**
         * show a user's profile information
         */
        get(API_CONTEXT + "/:sessionId", "application/json", (request, response) -> {
            try {
                String userId = request.params(":sessionId");
                HashMap<String, Object>  result = myAccountService.showUserDetailInfo(request.params(":sessionId"));
                return myAccountService.showUserDetailInfo(userId);
            } catch (MyAccountService.myAccountServiceException ex) {
                logger.error(String.format("Incorrect userId: %s", request.params(":sessionId")));
                response.status(404);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        /**
         * update an user's profile detail Info, include his photo
         */
        post(API_CONTEXT + "/AccountModify/:sessionId", "application/json", (request, response) -> {
            try {
                String userId = request.params(":sessionId");
                /**
                 * Hangbao: Get image string from request body
                 */
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(request.body());
                JsonObject json = element.getAsJsonObject();
                String image = json.get("image").getAsString();

                /**
                 * Hangbao: flag1 & flag2 check if user profile has been updated.
                 */
                Boolean flag1 = userPhotoService.uploadUserPhoto(userId, image);
                Boolean flag2 = myAccountService.updateAccountInfo(request.params(":sessionId"), request.body());
                if(flag1 && flag2){
                    logger.info("Successfully Updated No."+ userId + " user's profile ");
                    return true;
                }
            } catch (MyAccountService.myAccountServiceException ex) {
                logger.error(String.format("Failed to update accountInfo with sessionId: %s", request.params(":sessionId")));
                response.status(405);

            } catch (UserPhotoService.UserPhotoServiceException ex){
                logger.error("User Photo Service Exception: upload user photo failed.");
                response.status(405);
            }

            return Collections.EMPTY_MAP;
        }, new JsonTransformer());
    }
}


