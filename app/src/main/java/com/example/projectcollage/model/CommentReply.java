package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class CommentReply {

    @SerializedName("id")
    private int id;
    @SerializedName("comment_text")
    private String commentText;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("student_id")
    private int studentId;
    @SerializedName("student_affairs_id")
    private int studentAffairsId;
    @SerializedName("lecturer_id")
    private int lecturerId;
    @SerializedName("post_id")
    private int postId;
    @SerializedName("comment_id")
    private int commentId;
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

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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
