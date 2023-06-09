package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Course {
    @SerializedName("id")
    private int cid;//course id
    @SerializedName("name")
    private String name;
    @SerializedName("course_code")
    private String courseCode;
   @SerializedName("department_id")
    private int departmentId;
   @SerializedName("department_name")
    private String departmentName;
    @SerializedName("level")
    private String level;
    @SerializedName("semester")
    private String semester;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public Course() {
    }

    public Course(String courseName, String courseCode, String courseLevel, String courseSemester, int departmentId) {
                this.name = courseName;
                this.courseCode = courseCode;
                this.level = courseLevel;
                this.semester = courseSemester;
                this.departmentId = departmentId;

    }

    public int getCid() {
        return cid;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }


    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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
