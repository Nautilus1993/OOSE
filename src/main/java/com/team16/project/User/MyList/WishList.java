package com.team16.project.User.MyList;
import java.util.ArrayList;

/**
 * System creates Wishlist class whenever a user stars an item.
 * Wishlist class has method for adding, deleting and showing items in user's wishlist.
 * @author OOSE_Team16
 */
public class WishList {
    private int userId;
    private ArrayList<Integer> itemsIdLiked;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public ArrayList<Integer> getItemsIdLiked() {
        return itemsIdLiked;
    }
    public void setItemsIdLiked(ArrayList<Integer> itemsIdLiked) {
        this.itemsIdLiked = itemsIdLiked;
    }

}
