package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("id")
    private int uid;//user id
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
    @SerializedName("level")
    private String level;
    @SerializedName("state")
    private String state;
    @SerializedName("department_code")
    private String departmentCode;
    @SerializedName("national_id")
    private String nationalId;
    @SerializedName("department_id")
    private int departmentId;
    @SerializedName("department_name")
    private String departmentName;
    @SerializedName("fcm_token")
    private String fcmToken;
    @SerializedName("password")
    private String password;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    public Student(String firstname, String lastname, String email, String password, String nationalId, String level, String department, String state, String phoneNumber, int departmentId) {
        this.fName = firstname;
        this.lName = lastname;
        this.email = email;
        this.password = password;
        this.nationalId = nationalId;
        this.level = level;
        this.departmentCode = department;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.departmentId = departmentId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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


    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setlName(String lName) {
        this.lName = lName;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
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

    public Student() {
    }


}
