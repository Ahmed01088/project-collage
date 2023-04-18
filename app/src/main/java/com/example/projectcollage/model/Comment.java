package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("id")
    private int id;
    @SerializedName("comment_text")
    private String commentText;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("student_id")
    private Integer studentId;
    @SerializedName("student_affairs_id")
    private Integer studentAffairsId;
    @SerializedName("lecturer_id")
    private Integer lecturerId;
    @SerializedName("post_id")
    private int postId;
    @SerializedName("person_name")
    private String personName;
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

    public String getCommentText() {
        return commentText;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentAffairsId() {
        return studentAffairsId;
    }

    public void setStudentAffairsId(Integer studentAffairsId) {
        this.studentAffairsId = studentAffairsId;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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
