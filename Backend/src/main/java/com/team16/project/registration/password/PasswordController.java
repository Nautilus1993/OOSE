package com.team16.project.registration.password;

import java.sql.SQLException;

import static spark.Spark.post;

/**
* This controller sets the API for setting up password
* @author  Team 16
*/
public class PasswordController {
    private static final String CREATE_USER_API = "/registration/createuser";
    private PasswordService passwordService;

    public PasswordController() throws SQLException {
        passwordService = new PasswordService();
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(CREATE_USER_API, (req, res) ->
        {
            return passwordService.createUser(req.body());
        });
    }
}

