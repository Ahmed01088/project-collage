package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Data <T>{
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;
    @SerializedName("status")
    private int status;
    @SerializedName("quiz_time")
    private int quiz_time;

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

    public int getQuiz_time() {
        return quiz_time;
    }

    public void setQuiz_time(int quiz_time) {
        this.quiz_time = quiz_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
