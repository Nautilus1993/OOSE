package com.team16.project.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by DaChen on 12/10/16.
 */
public class ItemListDBTest {
    ItemListDB itemListDBTest;

    @Before
    public void setUp() throws Exception {
        itemListDBTest = new ItemListDB();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void getItemListInfo() throws Exception {
        assertEquals(new ArrayList<HashMap<String, Object>>(), itemListDBTest.getItemListInfo("100", "100"));
    }

}