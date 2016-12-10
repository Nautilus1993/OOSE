package com.team16.project.MyList.WishList;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kyo74_000 on 2016/12/10.
 */
public class WishServiceTest {
    WishService wishService = new WishService();
    List<Wish> result = new ArrayList<Wish>();

    @Test
    public void listWishes() throws Exception {
        assertEquals(result, wishService.listWishes("{\"toBeVerified\":\"50000\"}"));

    }

}
