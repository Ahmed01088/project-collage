package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Reaction {
    @SerializedName("id")
    private Integer id;
    @SerializedName("post_id")
    private Integer postId;
    @SerializedName("student_id")
    private Integer studentId;
    @SerializedName("lecturer_id")
    private Integer lecturerId;
    @SerializedName("student_affair_id")
    private Integer studentAffairId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Integer getStudentAffairId() {
        return studentAffairId;
    }

    public void setStudentAffairId(Integer studentAffairId) {
        this.studentAffairId = studentAffairId;
    }
}
