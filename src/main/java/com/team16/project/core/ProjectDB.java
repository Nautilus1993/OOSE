package com.team16.project.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProjectDB {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    public ProjectDB(){
        this.connection = null;
        this.statement = null;
        this.resultSet = null;
    }



}
