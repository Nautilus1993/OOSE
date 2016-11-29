package com.team16.project.sqlite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmailRegistrationDBTest {
    EmailRegistrationDB emailRegistrationDB;

    @Before
    public void setUp() throws Exception {
        emailRegistrationDB = new EmailRegistrationDB();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void postQuery() throws Exception {}

    @Test
    public void searchUser() throws Exception {
        assertEquals(0, emailRegistrationDB.searchUser("{\"toBeVerified\":\"ywang289@jhu.edu\"}"));
    }

}

