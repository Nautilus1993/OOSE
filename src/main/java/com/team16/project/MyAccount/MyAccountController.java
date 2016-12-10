package com.team16.project.MyAccount;

import com.team16.project.core.JsonTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;



public class MyAccountController {

    private static final String API_CONTEXT = "";

    public final MyAccountService myAccountService;

    private final Logger logger = LoggerFactory.getLogger(MyAccountController.class);

    public MyAccountController(MyAccountService myAccountService) {
        this.myAccountService = myAccountService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        /**
         * show a user's profile information
         */
        get(API_CONTEXT + "/MyAccount/:sessionId", "application/json", (request, response) -> {
            try {
                HashMap<String, Object>  result = myAccountService.showUserDetailInfo(request.params(":sessionId"));
                return myAccountService.showUserDetailInfo(request.params(":sessionId"));
            } catch (MyAccountService.myAccountServiceException ex) {
                logger.error(String.format("Incorrect userId: %s", request.params(":sessionId")));
                response.status(404);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());

        /**
         * update an user's profile detail Info
         */
        post(API_CONTEXT + "/MyAccount/AccountModify/:sessionId", "application/json", (request, response) -> {
            try {
                return myAccountService.updateAccountInfo(request.params(":sessionId"), request.body());

            } catch (MyAccountService.myAccountServiceException ex) {
                logger.error(String.format("Failed to update accountInfo with sessionId: %s", request.params(":sessionId")));
                response.status(405);
                return Collections.EMPTY_MAP;
            }
        }, new JsonTransformer());
    }
}


