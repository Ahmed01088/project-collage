package com.example.projectcollage.models;

public class Student extends User{
    private String uid;
    private String name;
    private String level;
    private String state;
    private String department;
    private String email;

    public Student() {
    }

    public Student(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public Student(String uid, String name, String level) {
        this.uid = uid;
        this.name = name;
        this.level = level;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
