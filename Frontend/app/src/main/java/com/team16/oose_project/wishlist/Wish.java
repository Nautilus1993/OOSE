package com.team16.oose_project.wishlist;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
* Data model for single wish
* @author  Team 16
*/
public class Wish {
    private String name;
    private String url;
    private String condition;
    private String price;
    private String availableDate;
    private String expirationDate;
    private String itemId;

    public String getItemId() {
        return itemId;
    }

    public Wish(String name, String url, String condition, String price, String availableDate, String expirationDate, String itemId) {
        this.name = name;
        this.url = url;
        this.condition = condition;
        this.price = price;
        this.availableDate = availableDate;
        this.expirationDate = expirationDate;
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getCondition() {
        return condition;
    }

    public String getPrice() {
        return price;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public static ArrayList<Wish> fromJson(JSONArray jsonArray) {
        ArrayList<Wish> wishes = new ArrayList<Wish>(jsonArray.length());
        // Process each result in json array, decode and convert to ItemAdaptor object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject wishJson = null;
            try {
                wishJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Wish wish = new Gson().fromJson(wishJson.toString(), Wish.class);
            if (wish != null) {
                wishes.add(wish);
            }
        }
        return wishes;
    }
}
