package com.example.projectcollage.roomdatabase;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "lecturers")
public class Lecturer {
    @PrimaryKey(autoGenerate = true)
    private int id;
    //nationality
    private String lid;
    private String name;
    private String phoneNumber;
    private String email;


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

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }
}
