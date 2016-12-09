package com.team16.project.registration.password;

import com.team16.project.Model.PasswordDB;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kyo74_000 on 2016/12/7.
 */
public class PasswordServiceTest {
    PasswordDB passwordDB;

    @Before
    public void setUp() throws Exception {
        passwordDB = new PasswordDB();
    }

    @Test
    public void createUser() throws Exception {
        assertEquals(0, passwordDB.insertUser("{\"toBeVerified\":\"2672921697@qq.com\", \"passwordToBeVerified\":\"12121212\", \"phoneToBeVerified\":\"4105228082\"}"));
    }

}