package com.team16.project.sqlite

import com.team16.project.Model.LoginDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginDBTest {
    LoginDB loginDBTest;

    @Before
    public void setUp() throws Exception {
        loginDBTest = new LoginDB();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void postQuery() throws Exception {}

    @Test
    public void searchUser() throws Exception {
        assertEquals(1, loginDBTest.searchUser("{\"toBeVerified\":\"ywang289@jhu.edu\", \"passwordToBeVerified\":\"11111111\"}"));
    }
}
