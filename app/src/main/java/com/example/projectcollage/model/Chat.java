package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Chat {
    @SerializedName("id")
    private int id;
    @SerializedName("student_sender_id")
    private Integer studentSenderId;
    @SerializedName("student_affairs_sender_id")
    private Integer studentAffairsSenderId;
    @SerializedName("lecturer_sender_id")
    private Integer lecturerSenderId;
    @SerializedName("student_reciver_id")
    private Integer studentReciverId;
    @SerializedName("student_affairs_reciver_id")
    private Integer studentAffairsReciverId;
    @SerializedName("lecturer_reciver_id")
    private Integer lecturerReciverId;
    @SerializedName("reciver_name")
    private String reciverName;
    @SerializedName("reciver_image")
    private String reciverImage;
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
    public Integer getStudentSenderId() {
        return studentSenderId;
    }

    public void setStudentSenderId(Integer studentSenderId) {
        this.studentSenderId = studentSenderId;
    }

    public Integer getStudentAffairsSenderId() {
        return studentAffairsSenderId;
    }

    public void setStudentAffairsSenderId(Integer studentAffairsSenderId) {
        this.studentAffairsSenderId = studentAffairsSenderId;
    }

    public Integer getLecturerSenderId() {
        return lecturerSenderId;
    }

    public void setLecturerSenderId(Integer lecturerSenderId) {
        this.lecturerSenderId = lecturerSenderId;
    }

    public String getReciverImage() {
        return reciverImage;
    }

    public void setReciverImage(String reciverImage) {
        this.reciverImage = reciverImage;
    }

    public Integer getStudentReciverId() {
        return studentReciverId;
    }

    public void setStudentReciverId(Integer studentReciverId) {
        this.studentReciverId = studentReciverId;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public Integer getStudentAffairsReciverId() {
        return studentAffairsReciverId;
    }

    public void setStudentAffairsReciverId(Integer studentAffairsReciverId) {
        this.studentAffairsReciverId = studentAffairsReciverId;
    }

    public Integer getLecturerReciverId() {
        return lecturerReciverId;
    }

    public void setLecturerReciverId(Integer lecturerReciverId) {
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
