package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Classroom {
   @SerializedName("id")
    private int id;
    @SerializedName("course_id")
    private int courseId;
    @SerializedName("lecturer_id")
    private int lecturerId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("course_name")
    private String  courseName;

    public Classroom(int courseId, int LecturerId) {
        this.courseId = courseId;
        this.lecturerId = LecturerId;
    }

    public String getCourseName() {
        return courseName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
