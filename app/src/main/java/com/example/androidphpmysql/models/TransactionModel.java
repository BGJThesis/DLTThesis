package com.example.androidphpmysql.models;

public class TransactionModel {
    public Integer quadCoins;
    public String user1;
    public String user2;

    public TransactionModel(Integer quadCoins, String user1, String user2) {
        this.quadCoins = quadCoins;
        this.user1 = user1;
        this.user2 = user2;
    }
}
