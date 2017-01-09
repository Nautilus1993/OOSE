package com.team16.oose_project.registration.password;

/**
* Constant holder class. 
* @author  Team 16
*/
class PasswordConstant {
    static final String CREATE_USER_API = "http://10.0.3.2:4567/registration/createuser";
    static final int MINIMUM_PASSWORD_LENGTH = 8;
    static final int ASTERISK = 42;
    static final String PASSWORD = "password";
    static final String HIDDEN_PASSWORD = "hiddenPassword";
    static final String PASSWORD_MISMATCH = "Password confirmation doesn't match!";
    static final String PASSWORD_LENGTH_MSG = "Password length must be greater than ";
    static final String CREATE_USER_FAIL = "0";
    static final String FAIL_CREATE_USER_MSG = "Fail to create user";
}
