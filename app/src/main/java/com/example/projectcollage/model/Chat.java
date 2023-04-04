package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Chat {

    @SerializedName("id")
    private int id;
    @SerializedName("student_sender_id")
    private int studentSenderId;
    @SerializedName("student_affairs_sender_id")
    private int studentAffairsSenderId;
    @SerializedName("lecturer_sender_id")
    private int lecturerSenderId;
    @SerializedName("student_reciver_id")
    private int studentReciverId;
    @SerializedName("student_affairs_reciver_id")
    private int studentAffairsReciverId;
    @SerializedName("lecturer_reciver_id")
    private int lecturerReciverId;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("created_at")
    private String createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentSenderId() {
        return studentSenderId;
    }

    public void setStudentSenderId(int studentSenderId) {
        this.studentSenderId = studentSenderId;
    }

    public int getStudentAffairsSenderId() {
        return studentAffairsSenderId;
    }

    public void setStudentAffairsSenderId(int studentAffairsSenderId) {
        this.studentAffairsSenderId = studentAffairsSenderId;
    }

    public int getLecturerSenderId() {
        return lecturerSenderId;
    }

    public void setLecturerSenderId(int lecturerSenderId) {
        this.lecturerSenderId = lecturerSenderId;
    }

    public int getStudentReciverId() {
        return studentReciverId;
    }

    public void setStudentReciverId(int studentReciverId) {
        this.studentReciverId = studentReciverId;
    }

    public int getStudentAffairsReciverId() {
        return studentAffairsReciverId;
    }

    public void setStudentAffairsReciverId(int studentAffairsReciverId) {
        this.studentAffairsReciverId = studentAffairsReciverId;
    }

    public int getLecturerReciverId() {
        return lecturerReciverId;
    }

    public void setLecturerReciverId(int lecturerReciverId) {
        this.lecturerReciverId = lecturerReciverId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
