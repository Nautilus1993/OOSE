package com.team16.project.subscribe;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by kyo74_000 on 2016/12/10.
 */
public class SubscribeServiceTest {
    SubscribeService subscribeService = new SubscribeService();
    int result = subscribeService.listUser();

    public SubscribeServiceTest() throws SQLException {
    }

    @Test
    public void listUser() throws Exception {
        assertEquals(1, result);
    }

}