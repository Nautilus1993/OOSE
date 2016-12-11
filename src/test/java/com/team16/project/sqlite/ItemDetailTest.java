package com.team16.project.sqlite;

import com.team16.project.Model.ItemDetailDB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ItemDetailTest {
	ItemDetailDB itemDetailDBTest;
	
    @Before
    public void setUp() throws Exception {
    	itemDetailDBTest = new ItemDetailDB();
    }

    @After
    public void tearDown() throws Exception {};
    
    @Test
    // test exited item
    public void showItemDetailInfoTest1() {
    	HashMap<String, Object> expect = new HashMap<String, Object>();    	
    
    	expect.put("name", "MALM single bed");
    	expect.put("price", "150.0");
    	expect.put("category1", "1");
    	expect.put("category2", "1");
    	expect.put("condition", "Perfect");
    	expect.put("isDeliver", "1");              
    	expect.put("pickUpAddress", "10 Art Museum Dr, Baltimore, MD 21218");
		expect.put("imgPath", "http://images.cb2.com/is/image/CB2/CeciliaQueenBedSHS16_1x1/$web_spilllarge3$/160718121026/cecilia-queen-bed.jpg");
		expect.put("description", "Adjustable bed sides allow you to use mattresses of different thicknesses. "
    			+ "16 slats of layer-glued birch adjust to your body weight and increase the suppleness of the mattress.");
    	expect.put("avialableDate", "2016-12-31");
    	expect.put("expireDate", "2017-01-31");
    			
    	HashMap<String, Object> result = itemDetailDBTest.getItemDetailInfo("1"); 
    	System.out.println(result);
    	
        assertNotNull(result); 
        assertEquals(expect, result);
    }
    
    @Test
    // test exited item
    public void showItemDetailInfoTest2() {
    	HashMap<String, Object> expect = new HashMap<String, Object>();    	
    
    	expect.put("name", "MALM single bed");
    	expect.put("price", "150.0");
    	expect.put("category1", "1");
    	expect.put("category2", "1");
    	expect.put("condition", "Perfect");
    	expect.put("isDeliver", "true");              
    	expect.put("pickUpAddress", "10 Art Museum Dr, Baltimore, MD21218");
		expect.put("imgPath", "http://images.cb2.com/is/image/CB2/CeciliaQueenBedSHS16_1x1/$web_spilllarge3$/160718121026/cecilia-queen-bed.jpg");
    	expect.put("description", "Adjustable bed sides allow you to use mattresses of different thicknesses. "
    			+ "16 slats of layer-glued birch adjust to your body weight and increase the suppleness of the mattress.");
    	expect.put("avialableDate", "2016-12-31");
    	expect.put("expireDate", "2017-01-31");
    			
    	HashMap<String, Object> result = itemDetailDBTest.getItemDetailInfo("100"); 	
    	assertTrue(result.isEmpty()); 
    }
}
