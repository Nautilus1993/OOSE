package com.team16.project.User;

/**
 * System will create a Guest class when a user opens our app.
 * Attribute isLogin is always boolean value false so that our app can provide limited service to users in
 * guest mode.
 * @author OOSE_Team16
 */
public class Guest {
    private String userId;
    private boolean isLogin;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

