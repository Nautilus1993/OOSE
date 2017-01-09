package com.team16.oose_project.MyAccount;

public class UserProfile {
    private String username;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String image;
    // image is a long string (encoded bitmap), for upload


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipcode(String zipcode) {
        this.zipCode = zipcode;
    }

    public String getImage() {
        /**
         * Todo: here need to receive file path from MyAccountDisplay
         * Check if file exist or not.
         * If photo file already exist, send server last modified time
         * If no user photo, directly send request
         *
         * File Exist:  --> {image : "DD-HH-MM"}
         * File not exist --> {image : ""}
         *
         * Server need to check this field before return response.
         */
        //System.out.println("Photo Path = " + photoPath);

        /**
         *
         */
        return image;
    }

    public void setImage(String imagePath) {
        this.image = imagePath;
    }
}