package com.team16.project.wishes;

public class Wish {
    private String name;
    private String url;
    private String condition;
    private String price;
    private String availableDate;
    private String expirationDate;

    public Wish(String name, String url, String condition, String price, String availableDate, String expirationDate) {
        this.name = name;
        this.url = url;
        this.condition = condition;
        this.price = price;
        this.availableDate = availableDate;
        this.expirationDate = expirationDate;
    }
}
