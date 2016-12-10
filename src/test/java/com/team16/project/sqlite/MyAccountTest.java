package com.team16.project.sqlite;

import com.team16.project.Model.MyAccountDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.team16.project.User.User;

public class MyAccountTest {
	MyAccountDB myAccountTest;
	
    @Before
    public void setUp() throws Exception {
    	myAccountTest = new MyAccountDB();
    }

    @After
    public void tearDown() throws Exception {};
    
    @Test
    public void searchUserDetailInfoTest() {
    	HashMap<String, Object> expect = new HashMap<String, Object>();    	
    	expect.put("userId", "1");
    	expect.put("username", "yuhang");
    	expect.put("email", "ywang289@jhu.edu");
    	expect.put("phone", "400-000-0000");
    	expect.put("address", "500 w University Pkwy");
    	expect.put("zipCode", "21210");   
        		
    	HashMap<String, Object> result = myAccountTest.searchUserDetailInfo("1"); 
    	System.out.println(result);
    	
        assertNotNull(result); 
        assertEquals(expect, result);
    }
    
    @Test
    public void updateAccountInfoTest1() {
    	// check if a user is existed
    	assertFalse(myAccountTest.updateAccountInfo("9", new User()));
    	
    	// check if update is completed correctly
    	String update = "{\"username\": \"yuhang\","
    			+ "\"email\": \"ywang289@jhu.edu\", "
    			+ "\"phoneNum\": \"410-000-0000\", "
    			+ "\"address\":\"500 w University Pkwy\", "
    			+ "\"zipCode\": \"21230\"}";    	
    	User user = new Gson().fromJson(update, User.class);
    	
    	myAccountTest.updateAccountInfo("1", user);
    	assertTrue(myAccountTest.searchUserDetailInfo("1").get("zipCode").equals("21230"));
    	assertTrue(myAccountTest.searchUserDetailInfo("1").get("phone").equals("410-000-0000"));
    }   
    
    @Test
    public void updateAccountInfoTest2() {
    	// check if a user is existed
    	assertFalse(myAccountTest.updateAccountInfo("9", new User()));
    	
    	// check if update is completed correctly
    	String update = "{\"username\": \"yuhang\","
    			+ "\"email\": \"ywang289@jhu.edu\", "
    			+ "\"phoneNum\": \"400-000-0000\", "
    			+ "\"address\":\"500 w University Pkwy\", "
    			+ "\"zipCode\": \"21210\"}";    	
    	User user = new Gson().fromJson(update, User.class);
    	
    	myAccountTest.updateAccountInfo("1", user);
    	assertTrue(myAccountTest.searchUserDetailInfo("1").get("zipCode").equals("21210"));
    	assertTrue(myAccountTest.searchUserDetailInfo("1").get("phone").equals("400-000-0000"));
    }
}
