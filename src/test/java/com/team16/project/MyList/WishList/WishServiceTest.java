package com.team16.project.MyList.WishList;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WishServiceTest {
    WishService wishService = new WishService();

    @Test
    public void removeWish() throws Exception {
        int result = wishService.removeWish("{\"userId\":\"0\", \"itemId\":\"0\"}");
        assertEquals(1, result);
    }

    @Test
    public void listWishes() throws Exception {
        List<Wish> wishes = new ArrayList<Wish>();
        List<Wish> result = wishService.listWishes("{\"userId\":\"0\"}");
        assertEquals(wishes, result);
    }

}