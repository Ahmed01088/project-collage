package com.example.projectcollage.model;


import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String message;

    public Notification() {
    }

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
