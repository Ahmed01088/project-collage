package com.example.projectcollage.models;

import android.view.SurfaceView;

public class VideoUser {
    private int uid;
    private String token;
    SurfaceView surfaceView;

    public VideoUser() {
    }

    public VideoUser(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }

    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    public int getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setSurfaceView(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }
}
