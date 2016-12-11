package com.team16.project.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class shall be created whenever a user posts a new item into our system.
 * This class has several attributes which can help to describe a specific product.
 * Item class has method to edit its attributes.
 * Our app also supports filters and search engine based on these attributes.
 * @author OOSE_Team16
 */

public class Item {
    private int itemId;
    private int sellerId;
    private String category1;
    private String category2;
    private String name;
    private double price;
    private String imgLink;
    private String condition;
    private boolean isDeliver;
    private String pickUpAddress;
    private String description;
    private Date postDate;
    private Date avialableDate;
    private Date expireDate;
    private int numOfLikes;
    private ArrayList<String> contactMethods;


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isDeliver() {
        return isDeliver;
    }
    public void setDeliver(boolean isDeliver) {
        this.isDeliver = isDeliver;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getDesciption() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    public Date getAvialableDate() {
        return avialableDate;
    }

    public void setAvialableDate(Date avialableDate) {
        this.avialableDate = avialableDate;
    }
//    public void setAvialableDate(String avialableDate) {
//        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/mm/dd");
//        try {
//            this.avialableDate = sdf.parse(avialableDate);
//        }catch (ParseException e){
//            e.printStackTrace();
//        }
//    }
    public Date getExpireDate() {
        return expireDate;
    }
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    public void setExpireDate(String expireDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/mm/dd");
        try {
            this.expireDate = sdf.parse(expireDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    public int getNumOfLikes() {
        return numOfLikes;
    }

    public void setNumOfLikes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    public ArrayList<String> getContactMethods() {
        return contactMethods;
    }

    public void setContactMethods(ArrayList<String> contactMethods) {
        this.contactMethods = contactMethods;
    }

    /**
     * This method checks if item's name is valid.
     * @return boolean value.
     */
    public boolean isValidName() {
        return this.name.length() <= 100;
    }

    /**
     * This method checks if item's price is valid.
     * @return boolean value.
     */
    public boolean isValidPrice() {
        return this.price >= 0;
    }

    /**
     * This method checks if item's pickup address is valid.
     * @return boolean value.
     */
    public boolean isValidPickUpAddress() {
        return (this.pickUpAddress.length() <= 100);
    }

    /**
     * Method to edit item's attributes.
     * @param item: item needs to be updated.
     */
    public void edit (Item item) {

    }
}
