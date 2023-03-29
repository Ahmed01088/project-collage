package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
