package com.example.projectcollage.models;

import com.google.gson.annotations.SerializedName;

public class Hero {
    @SerializedName("name")
    private String superName;
    @SerializedName("realname")
    private String realName;
    @SerializedName("team")
    private String team;

    public Hero(String superName, String realName, String team) {
        this.superName = superName;
        this.realName = realName;
        this.team = team;
    }

    public String getSuperName() {
        return superName;
    }

    public String getRealName() {
        return realName;
    }

    public String getTeam() {
        return team;
    }
}
