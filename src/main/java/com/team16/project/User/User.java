package com.team16.project.User;

/**
 * System creates User class at the first time a user choose to login.
 * @author OOSE_Team16
 */
public class User extends Guest {
    private String username;
    private String password;
    private String email;
    private String phoneNum;
    private String address;
    private String city;
    private String state;
    private String imgLink;
    private String facebook;
    private String zipCode;

    public String getImgLink() {
        return imgLink;
    }
    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
    public String getFacebook() {
        return facebook;
    }
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

//    private WishList wishlist;
//    private SellList sellList;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
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
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
//    public WishList getWishlist() {
//        return wishlist;
//    }
//    public void setWishlist(WishList wishlist) {
//        this.wishlist = wishlist;
//    }
//    public SellList getSellList() {
//        return sellList;
//    }
//    public void setSellList(SellList sellList) {
//        this.sellList = sellList;
//    }

    /**
     * Method to check if user's name is valid.
     * @return boolean value.
     */
    public boolean isValidUsername() {
        if (this.username.length() <= 20)
            return true;
        else
            return false;
    }

    /**
     * Method to check if user's password is valid.
     * @return boolean value.
     */
    public boolean isValidPwd() {
        if (this.password.length() <= 50)
            return true;
        else
            return false;
    }

    /**
     * Method to check if user's email is valid.
     * @return boolean value.
     */
    public boolean isValidEmail() {
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        // Overall length of the email must be >3 and <= 100.
        if (this.email.length() <= 100 && this.email.length() > 3) {
            flag1 = true;
        }
        // The email must include "@"
        if (this.email.contains("@")) {
            flag2 = true;
        }
        /**
         * The substring before "@" must contain a combination of
         * Upper Case, Lower Case, 0-9, and "_" symbols.
         */
        if(this.email.matches("[A-Z a-z 0-9 _]+@.*")) {
            flag3 = true;
        }
        return flag1 && flag2 && flag3;
    }

    /**
     * Method to check if user's phone is valid.
     * @return boolean value.
     */
    public boolean isValidPhone() {
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        // Overall length of the email must be <= 14.
        if (this.phoneNum.length() <= 14) {
            flag1 = true;
        }
        // The phone number must start with + of numeric number
        if (this.phoneNum.startsWith("+")
                || Character.isDigit(this.phoneNum.charAt(0))) {
            flag2 = true;
        }
        // The phone number must end with 11 to 13 numeric numbers
        if (this.phoneNum.matches("[0-9]{11,13}$")) {
            flag3 = true;
        }
        return flag1 && flag2 && flag3;
    }

    /**
     * Check Valid US Postal ZIP codes.
     * Allow both the five-digit and nine-digit formats.
     * A valid postal code should match 12345 and 12345-6789.
     * @return boolean value.
     */
    public boolean isValidZipCode() {
        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        return this.zipCode.matches(regex);
    }

}

