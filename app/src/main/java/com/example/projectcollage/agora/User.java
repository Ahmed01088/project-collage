package com.example.projectcollage.agora;

public class User {
    public User(int uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public final int uid;
    public final String name;

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

}