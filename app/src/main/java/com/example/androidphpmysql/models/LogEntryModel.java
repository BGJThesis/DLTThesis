package com.example.androidphpmysql.models;

public class LogEntryModel {
    String user1 = "";
    String user2 = "";
    Integer quadCoins = 0;

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public Integer getQuadCoins() {
        return quadCoins;
    }

    public void setQuadCoins(Integer quadCoins) {
        this.quadCoins = quadCoins;
    }
}
