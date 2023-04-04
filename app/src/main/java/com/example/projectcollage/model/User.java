package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("national_id")
    private String nationalId;
    @SerializedName("password")
    private String password;
    @SerializedName("id")
    private Integer uid;

    public User(String nationalId, String password) {
        this.nationalId = nationalId;
        this.password = password;
    }

    public User(String name, String email,String nationalId, String password) {
        this.name = name;
        this.email = email;
        this.nationalId = nationalId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer  uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
}
