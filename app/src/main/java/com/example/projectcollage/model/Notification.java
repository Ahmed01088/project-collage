package com.example.projectcollage.model;


import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String message;

    @SerializedName("type")
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
