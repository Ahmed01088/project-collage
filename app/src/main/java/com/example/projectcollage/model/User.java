package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("national_id")
    private String nationalId;
    @SerializedName("password")
    private String password;

    public User(String nationalId, String password) {
        this.nationalId = nationalId;
        this.password = password;
    }

    public User(String name, String email, String nationalId, String password) {
        this.name = name;
        this.email = email;
        this.nationalId = nationalId;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
