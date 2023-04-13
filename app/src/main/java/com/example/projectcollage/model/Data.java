package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Data <T>{
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;
    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
