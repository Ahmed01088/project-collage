package com.example.projectcollage.models;

import android.view.SurfaceView;

public class VideoUser {
    SurfaceView surfaceView;

    public VideoUser() {
    }

    public VideoUser(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }

    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    public void setSurfaceView(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }
}
