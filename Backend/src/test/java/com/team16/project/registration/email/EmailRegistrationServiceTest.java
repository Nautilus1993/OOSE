package com.team16.project.registration.email;

import com.team16.project.Model.EmailRegistrationDB;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmailRegistrationServiceTest {
    EmailRegistrationDB emailRegistrationDB;
    @Before
    public void setUp() throws Exception {
        emailRegistrationDB = new EmailRegistrationDB();
    }

    @Test
    public void searchUser() throws Exception {
        assertEquals(0, emailRegistrationDB.searchUser("{\"toBeVerified\":\"ywang289@jhu.edu\"}"));
        assertFalse(0 == emailRegistrationDB.searchUser("{\"toBeVerified\":\"fail@jhu.edu\"}"));
    }
}