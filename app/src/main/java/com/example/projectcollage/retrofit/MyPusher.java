package com.example.projectcollage.retrofit;

import android.app.Application;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;

public class MyPusher extends Application {
    private static Pusher pusher;
    private static final String PUSHER_APP_KEY = "your_app_key";
    private static final String PUSHER_APP_CLUSTER = "your_app_cluster";

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Pusher
        PusherOptions options = new PusherOptions()
                .setCluster(PUSHER_APP_CLUSTER)
                .setEncrypted(true); // enable HTTPS
        pusher = new Pusher(PUSHER_APP_KEY, options);
        pusher.connect();
    }

    public synchronized Pusher getPusher() {
        return pusher;
    }
}
