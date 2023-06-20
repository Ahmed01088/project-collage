package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Department {
    @SerializedName("id")
    private int did;//department id
    @SerializedName("name")
    private String name;
    @SerializedName("department_code")
    private String departmentCode;
    @SerializedName("semester")
    private String semester;
    @SerializedName("level")
    private String level;
    @SerializedName("year")
    private String year;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public Department() {
    }

    public Department(String nameDepartment, String departmentCode, String level, String semester) {
        this.name = nameDepartment;
        this.departmentCode = departmentCode;
        this.level = level;
        this.semester = semester;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
