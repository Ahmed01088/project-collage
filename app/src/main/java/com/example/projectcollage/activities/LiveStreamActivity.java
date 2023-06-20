package com.example.projectcollage.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.display.VirtualDisplay;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityLiveStreemBinding;
import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;

public class LiveStreamActivity extends AppCompatActivity{
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLiveStreemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        getWindow().setStatusBarColor(getColor(R.color.statesBar));
        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        setupVideoSDKEngine();
        binding.joinButton.setOnClickListener(v -> {
            if (isJoined) {
                leaveChannel();
                binding.joinButton.setText(R.string.join);
            } else {
                joinChannel();
                binding.joinButton.setText(R.string.leave);
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

        // Destroy the engine in a sub-thread to avoid congestion
        new Thread(() -> {
            RtcEngine.destroy();
            agoraEngine = null;
        }).start();
    }
    //share screen
   /* public void shareScreen(View view) {
        if (!isSharingScreen) { // Start sharing
            // Ensure that your Android version is Lollipop or higher.
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    fgServiceIntent = new Intent(this, MainActivity.class);
                    startForegroundService(fgServiceIntent);
                }
                // Get the screen metrics
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                // Set screen capture parameters
                ScreenCaptureParameters screenCaptureParameters = new ScreenCaptureParameters();
                screenCaptureParameters.captureVideo = true;
                screenCaptureParameters.videoCaptureParameters.width = metrics.widthPixels;
                screenCaptureParameters.videoCaptureParameters.height = metrics.heightPixels;
                screenCaptureParameters.videoCaptureParameters.framerate = DEFAULT_SHARE_FRAME_RATE;
                screenCaptureParameters.captureAudio = true;
                screenCaptureParameters.audioCaptureParameters.captureSignalVolume = 50;

                // Start screen sharing
                agoraEngine.startScreenCapture(screenCaptureParameters);
                isSharingScreen = true;
                startScreenSharePreview();
                // Update channel media options to publish the screen sharing video stream
                updateMediaPublishOptions(true);
                binding.ShareScreenButton.setText("Stop Screen Sharing");
            }
        } else { // Stop sharing
            agoraEngine.stopScreenCapture();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (fgServiceIntent!=null) stopService(fgServiceIntent);
            }
            isSharingScreen = false;
            binding.ShareScreenButton.setText("Start Screen Sharing");

            // Restore camera and microphone publishing
            updateMediaPublishOptions(false);
            setupLocalVideo();
        }
    }

    private void startScreenSharePreview() {
        // Create render view by RtcEngine
        SurfaceView surfaceView = new SurfaceView(getBaseContext());
        if (binding.localVideoViewContainer.getChildCount() > 0) {
            binding.localVideoViewContainer.removeAllViews();
        }
        // Add SurfaceView to the local FrameLayout
        binding.localVideoViewContainer.addView(surfaceView,
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

        // Setup local video to render your local camera preview
        agoraEngine.setupLocalVideo(new VideoCanvas(surfaceView, Constants.RENDER_MODE_FIT, 0));

        agoraEngine.startPreview(Constants.VideoSourceType.VIDEO_SOURCE_SCREEN_PRIMARY);
    }
    void updateMediaPublishOptions(boolean publishScreen) {
        ChannelMediaOptions mediaOptions = new ChannelMediaOptions();
        mediaOptions.publishCameraTrack = !publishScreen;
        mediaOptions.publishMicrophoneTrack = !publishScreen;
        mediaOptions.publishScreenCaptureVideo = publishScreen;
        mediaOptions.publishScreenCaptureAudio = publishScreen;
        agoraEngine.updateChannelMediaOptions(mediaOptions);
    }*/

}
