package com.team16.project.MyList.SellingList;


import com.team16.project.Item.Item;

import java.util.ArrayList;

/**
 * System creates SellList class at the first time a user posts an item.
 * SellList class has method for adding, deleting and showing items in user's selling list.
 * @author OOSE_Team16
 */
public class SellList {
    private String userId;
    private ArrayList<Integer> itemsForSell;

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setItemsForSell(ArrayList<Integer> itemsForSell) {
        this.itemsForSell = itemsForSell;
    }

    /**
     * User adds items to their sell list.
     * @param item item to be added.
     */
    public void add (Item item) {
    }

    /**
     * User deletes items to their sell list.
     * @param itemId item to be deleted.
     */
    public void delete (String itemId) {
    }

    public ArrayList<Integer> getSellingList () {
        return itemsForSell;
    }
}
