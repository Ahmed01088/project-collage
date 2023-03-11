package com.example.projectcollage.models;

import android.app.Notification;

import com.google.gson.annotations.SerializedName;

public class NotificationRequest {
    @SerializedName("to")
    private String token;
    @SerializedName("notification")
    private Notification notification;

}
