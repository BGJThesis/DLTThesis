package com.example.androidphpmysql;

import android.app.Application;

public class UserDetails extends Application {

    String name;
    String emailAddress;
    String quadCoins;

    public UserDetails(String name, String emailAddress, String quadCoins) {
        this.name = name;
        this.emailAddress = emailAddress;
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

    public String getQuadCoins() {
        return quadCoins;
    }

    public void setQuadCoins(String quadCoins) {
        this.quadCoins = quadCoins;
    }
}
