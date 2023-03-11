package com.example.projectcollage.roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


enum Level{
  First,
    Second,
    Third,
    Fourth
}
enum State{
    New,
    Old
}
@Entity(tableName = "students")
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;
    //nationality
    private String uid;
    private String name;
    private String email;
    private Level level;
    private String department;
    private State state;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
