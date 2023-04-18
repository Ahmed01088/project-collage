package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Lecturer {
    @SerializedName("id")
    private int lid;//lecturer id
    @SerializedName("firstname")
    private String fName;
    @SerializedName("lastname")
    private String lName;
    @SerializedName("phone_no")
    private String  phoneNumber;
    @SerializedName("email")
    private String email;
    @SerializedName("image")
    private String image;
    @SerializedName("course_id")
    private int courseId;
    @SerializedName("department_id")
    private int departmentId;
    @SerializedName("department_level")
    private String departmentLevel;
    @SerializedName("department_name")
    private String departmentName;
    @SerializedName("national_id")
    private String nationalId;
    @SerializedName("password")
    private String password;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;



    public Lecturer(String firstName, String lastName, String email, String password, String phoneNumber, String nationalId, String code, int departmentId, int courseId) {
        this.fName = firstName;
        this.lName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nationalId = nationalId;
        this.departmentId = departmentId;
        this.courseId = courseId;
    }

    public Lecturer(String firstName, String lastName, String email, String password, String phoneNumber, String nationalId, int departmentId, int courseId) {
        this.fName = firstName;
        this.lName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nationalId = nationalId;
        this.departmentId = departmentId;
        this.courseId = courseId;
    }

    public String getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(String departmentLevel) {
        this.departmentLevel = departmentLevel;
    }

    public int getLid() {
        return lid;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
