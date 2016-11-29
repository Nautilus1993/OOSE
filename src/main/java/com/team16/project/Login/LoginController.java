package com.team16.project.Login;

import java.sql.SQLException;

import static spark.Spark.post;

import java.sql.*;

import static spark.Spark.*;

public class LoginController {
    private static final String LOGIN_API = "/login/email";
    private LoginService loginService;

    public LoginController() throws SQLException {
        loginService = new LoginService();
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(LOGIN_API, (req, res) ->
        {
            return loginService.verifyLogin(req.body());
        });
    }
}

