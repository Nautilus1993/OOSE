package com.team16.oose_project.sellingList;

import java.util.Date;

/**
 * Created by lxx on 11/21/16.
 */

public class SellingItem {
    private String itemId;
    private String sellerId;
    private String name;
    private String icon;
    private String price;
    private String category1;
    private String category2;
    private String condition;
    private boolean isDeliver;
    private Date avialableDate;
    private Date expireDate;
    private String[] contactMethods;
    private String description;

    // Constructor
    public SellingItem(){};

    public SellingItem(String name, String icon, String price, String condition,
                       Boolean isDeliver, Date avialableDate, String[] contacts){
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.condition = condition;
        this.isDeliver = isDeliver;
        this.avialableDate = avialableDate;
        this.contactMethods = contacts;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String[] getContactMethods() {
        return contactMethods;
    }

    public void setContactMethods(String[] contactMethods) {
        this.contactMethods = contactMethods;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public void setDeliver(boolean deliver) {
        this.isDeliver = deliver;
    }

    public Date getAvialableDate() {
        return avialableDate;
    }

    public void setAvialableDate(Date avialableDate) {
        this.avialableDate = avialableDate;
    }

    public String[] getContacts() {
        return contactMethods;
    }

    public void setContacts(String[] contacts) {
        this.contactMethods = contacts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
