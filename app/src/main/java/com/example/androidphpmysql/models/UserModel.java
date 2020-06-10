package com.example.androidphpmysql.models;

public class UserModel {
    String name;
    String emailAddress;
    String password;
    Integer quadCoins;

    public UserModel(String name, String emailAddress, String password, Integer quadCoins) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
        this.quadCoins = quadCoins;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getQuadCoins() {
        return quadCoins;
    }

    public void setQuadCoins(Integer quadCoins) {
        this.quadCoins = quadCoins;
    }
}
