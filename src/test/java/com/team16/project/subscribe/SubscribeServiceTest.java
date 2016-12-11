package com.team16.project.subscribe;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by kyo74_000 on 2016/12/10.
 */
public class SubscribeServiceTest {
    @Test
    public void subscribe() throws Exception {

    }

    SubscribeService subscribeService = new SubscribeService();


    public SubscribeServiceTest() throws SQLException, ParseException {
        int result = subscribeService.subscribe("{\"userId\":\"1\", \"itemId\":\"1\"}");
        assertEquals(1, result);
    }

    @Test
    public void listUser() throws Exception {
        int result = subscribeService.listUser();
        assertEquals(1, result);
    }

}