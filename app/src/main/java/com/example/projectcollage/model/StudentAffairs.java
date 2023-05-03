package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class StudentAffairs {
    @SerializedName("id")
    private int said;// student affairs id
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("national_id")
    private String nationalId;
    @SerializedName("email")
    private String email;
    @SerializedName("phone_no")
    private String phoneNumber;
    @SerializedName("password")
    private String password;
    @SerializedName("fcm_token")
    private String fcmToken;
    @SerializedName("image")
    private String image;
    @SerializedName("admin_id")
    private int adminId;
    @SerializedName("date_added")
    private String dateAdded;
    @SerializedName("responsible_level")
    private String responsibleLevel;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("created_at")
    private String createdAt;

    public StudentAffairs(String firstname, String lastname, String nationalId, String email, String phoneNumber, String image, int adminId, String password, String level, String dateAdded) {
           this.firstName = firstname;
            this.lastName = lastname;
            this.nationalId = nationalId;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.image = image;
            this.adminId = adminId;
            this.password = password;
            this.responsibleLevel = level;
            this.dateAdded = dateAdded;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSaid() {
        return said;
    }

    public void setSaid(int said) {
        this.said = said;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getResponsibleLevel() {
        return responsibleLevel;
    }

    public void setResponsibleLevel(String responsibleLevel) {
        this.responsibleLevel = responsibleLevel;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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
