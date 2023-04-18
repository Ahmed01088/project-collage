package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("id")
    private int id;
    @SerializedName("student_id")
    private int studentId;
    @SerializedName("rating")
    private String rating;

    public Rating(int uid, String toString) {
        this.studentId = uid;
        this.rating = toString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
