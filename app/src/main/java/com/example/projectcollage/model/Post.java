package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("id")
    private int id;
    @SerializedName("content")
    private String content;
    @SerializedName("image")
    private String image;
    @SerializedName("posted_at")
    private String postedAt;
    @SerializedName("student_id")
    private int studentId;
    @SerializedName("student_affairs_id")
    private int studentAffairsId;
    @SerializedName("lecturer_id")
    private int lecturerId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentAffairsId() {
        return studentAffairsId;
    }

    public void setStudentAffairsId(int studentAffairsId) {
        this.studentAffairsId = studentAffairsId;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
