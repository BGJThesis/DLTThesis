package com.example.androidphpmysql.models;

public class UserDetails {

    String name = "";
    String emailAddress = "";
    Integer quadCoins = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getQuadCoins() {
        return quadCoins;
    }

    public void setQuadCoins(Integer quadCoins) {
        this.quadCoins = quadCoins;
    }
}
