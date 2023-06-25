package com.example.projectcollage.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.display.VirtualDisplay;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityLiveStreemBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Realtime;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.ScreenCaptureParameters;
import io.agora.rtc2.video.VideoCanvas;
import io.agora.rtc2.video.VideoEncoderConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveStreamActivity extends AppCompatActivity{
    private static final int DEFAULT_SHARE_FRAME_RATE = 15;
    ActivityLiveStreemBinding binding;
    public static final int PERMISSION_REQ_ID=22;
    private int remoteUid = 0; // Stores the uid of the remote user
    public static final String[] REQUESTED_PERMISSIONS={
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };
    private boolean isVideoMute=true;
    private boolean isAudioMute=true;
    private VirtualDisplay mVirtualDisplay;
    private Surface mSurfaceView;
    private boolean isSharingScreen=false;
    private Intent fgServiceIntent;

    private boolean checkSelfPermission(){
        return ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED;
    }
    private int uid = 0;
    private boolean isJoined = false;

    private RtcEngine agoraEngine;
    //SurfaceView to render local video in a Container.
    private SurfaceView localSurfaceView;
    //SurfaceView to render Remote video in a Container.
    private SurfaceView remoteSurfaceView;
    private SharedPreferences sharedPreferences;
    private String token;
    private String channelName;
    private int tokenRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLiveStreemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        getWindow().setStatusBarColor(getColor(R.color.statesBar));
        sharedPreferences=getSharedPreferences(com.example.projectcollage.utiltis.Constants.DATA,MODE_PRIVATE);
        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        setupVideoSDKEngine();
        binding.joinButton.setOnClickListener(v -> {
            if (isJoined) {
                leaveChannel();
                binding.joinButton.setText(R.string.join);
                isJoined = false;
            } else {
                joinChannel();
                binding.joinButton.setText(R.string.leave);
                isJoined = true;
            }
        });
        binding.openCloseCameraVideoLive.setOnClickListener(view -> {
            if (isJoined){
                if (isVideoMute){
                    agoraEngine.muteLocalVideoStream(true);
                    binding.openCloseCameraVideoLive.setImageResource(R.drawable.ic_videocam_off);
                    isVideoMute=false;
                }else {
                    agoraEngine.muteLocalVideoStream(false);
                    binding.openCloseCameraVideoLive.setImageResource(R.drawable.ic_videocam);
                    isVideoMute=true;
                }
            }

        });
        binding.switchCamera.setOnClickListener(view -> {
            if (isJoined){
                agoraEngine.switchCamera();
            }
        });
        binding.openCloseSoundLive.setOnClickListener(view -> {
            if(isJoined){
                if (isAudioMute){
                    agoraEngine.muteLocalAudioStream(true);
                    binding.openCloseSoundLive.setImageResource(R.drawable.ic_mic_off);
                    isAudioMute=false;
                }else {
                    agoraEngine.muteLocalAudioStream(false);
                    binding.openCloseSoundLive.setImageResource(R.drawable.ic_mic);
                    isAudioMute=true;
                }
            }


        });
    }
    void showMessage(String message) {
        runOnUiThread(() ->
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }
    private void setupVideoSDKEngine() {
        try {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = com.example.projectcollage.utiltis.Constants.AGORA_APP_ID;
            config.mEventHandler = mRtcEventHandler;
            agoraEngine = RtcEngine.create(config);
            agoraEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(
                    VideoEncoderConfiguration.VD_640x360,
                    VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                    VideoEncoderConfiguration.STANDARD_BITRATE,
                    VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
            ));
            // By default, the video module is disabled, call enableVideo to enable it.
            agoraEngine.enableVideo();
        } catch (Exception e) {
            showMessage(e.toString());
        }
    }
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {

        @Override
        // Listen for the remote host joining the channel to get the uid of the host.
        public void onUserJoined(int uid, int elapsed) {
            showMessage("Remote user joined " + uid);
            remoteUid = uid;
            // Set the remote video view
            runOnUiThread(() -> setupRemoteVideo(uid));
        }


        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            isJoined = true;
            showMessage("Joined Channel " + channel);
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            showMessage("Remote user offline " + uid + " " + reason);
            runOnUiThread(() -> remoteSurfaceView.setVisibility(View.GONE));
        }
        // Listen for the event that the token is about to expire

    };
    private void setupRemoteVideo(int uid) {
        remoteSurfaceView = new SurfaceView(getBaseContext());
        remoteSurfaceView.setZOrderMediaOverlay(true);
        binding.remoteVideoViewContainer.addView(remoteSurfaceView);
        agoraEngine.setupRemoteVideo(new VideoCanvas(remoteSurfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
        // Display RemoteSurfaceView.
        remoteSurfaceView.setVisibility(View.VISIBLE);
    }
    private void setupLocalVideo() {
        // Create a SurfaceView object and add it as a child to the FrameLayout.
        localSurfaceView = new SurfaceView(getBaseContext());
        binding.localVideoViewContainer.addView(localSurfaceView);
        // Call setupLocalVideo with a VideoCanvas having uid set to 0.
        agoraEngine.setupLocalVideo(new VideoCanvas(localSurfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0));
    }
    public void joinChannel() {
        if (checkSelfPermission()) {
            ChannelMediaOptions options = new ChannelMediaOptions();

            // For a Video call, set the channel profile as COMMUNICATION.
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION;
            // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
            // Display LocalSurfaceView.
            setupLocalVideo();
            localSurfaceView.setVisibility(View.VISIBLE);
            // Start local preview.
            agoraEngine.startPreview();
            // Join the channel with a temp token.
            // You need to specify the user ID yourself, and ensure that it is unique in the channel.
            agoraEngine.joinChannel(com.example.projectcollage.utiltis.Constants.AGORA_TOKEN, com.example.projectcollage.utiltis.Constants.AGORA_CHANNEL_NAME, uid, options);
        } else {
            Toast.makeText(getApplicationContext(), "Permissions was not granted", Toast.LENGTH_SHORT).show();
        }
    }
    public void leaveChannel() {
        if (!isJoined) {
            showMessage("Join a channel first");
        } else {
            agoraEngine.leaveChannel();
            showMessage("You left the channel");
            // Stop remote video rendering.
            if (remoteSurfaceView != null) remoteSurfaceView.setVisibility(View.GONE);
            // Stop local video rendering.
            if (localSurfaceView != null) localSurfaceView.setVisibility(View.GONE);
            isJoined = false;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        agoraEngine.stopPreview();
        agoraEngine.leaveChannel();
        if (sharedPreferences.getString(com.example.projectcollage.utiltis.Constants.USER_TYPE, "").equals(com.example.projectcollage.utiltis.Constants.USER_TYPES[1])){
            int classroomId=getIntent().getIntExtra(com.example.projectcollage.utiltis.Constants.CLASSROOM_ID,0);
           closeLive(classroomId);
        }

        // Destroy the engine in a sub-thread to avoid congestion
        new Thread(() -> {
            RtcEngine.destroy();
            agoraEngine = null;
        }).start();
    }
    //share screen


    private void closeLive(int classroomId){
        Call<Data<Realtime>>call= RetrofitClientLaravelData.getInstance().getApiInterface().getFinishLive(classroomId);
        call.enqueue(new Callback<Data<Realtime>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Realtime>> call, @NonNull Response<Data<Realtime>> response) {
                if (response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "تم اغلاق البث", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(), "حدث خطأ ما", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Realtime>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "حدث خطأ ما", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
