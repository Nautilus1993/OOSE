package com.team16.project.MyAccount;

import com.google.gson.Gson;
import com.team16.project.User.User;
import com.team16.project.Model.MyAccountDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class MyAccountService {
    private final MyAccountDB projectDB;
    private final Logger logger = LoggerFactory.getLogger(MyAccountService.class);

    public MyAccountService(MyAccountDB projectDB) {
        this.projectDB = projectDB;
    }

    /**
     * Find a user's detail info. corresponding to a given sessionId
     * @param userId
     * @return If the user exists, return a hashMap containing required information, otherwise return an empty hashMap.
     *
     */
    public HashMap<String, Object> showUserDetailInfo(String userId) throws myAccountServiceException{
        return projectDB.searchUserDetailInfo(userId);
    }

    /**
     * Update the specified account entry with new information
     * the email and phone must not be missing
     * re-confirmation is needed if the user change email address or phone number
     * @param sessionId
     * @param body
     * @return the updated User detail
     *
     */
    public HashMap<String, Object> updateAccountInfo(String sessionId, String body) throws myAccountServiceException {
        User user = new User();
        try {
            user = new Gson().fromJson(body, User.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("create user success!");

    	/* email and phone must not missing! (check in the front end)
    	   if email or phone is updated, we need to send verification. (do here)
    	   how to throw exception?
    	 */
        Boolean isUpdate = projectDB.updateAccountInfo(sessionId, user);

        if (isUpdate) {
            return null;
            // return showUserDetailInfo(sessionId);
        } else {
            return null;
        }
    }

    public class myAccountServiceException extends Exception {
        public myAccountServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}