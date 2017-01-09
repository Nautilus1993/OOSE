package com.team16.project.sqlite

import com.team16.project.Model.PasswordDB
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PasswordDBTest {
    PasswordDB passwordDB;

    @Before
    public void setUp() throws Exception {
        passwordDB = new PasswordDB();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void postQuery() throws Exception {}

    @Test
    public void insertUser() throws Exception {
        assertEquals(0, passwordDB.insertUser("{\"toBeVerified\":\"2672921697@qq.com\", \"passwordToBeVerified\":\"12121212\", \"phoneToBeVerified\":\"4105228082\"}"));
    }
}

