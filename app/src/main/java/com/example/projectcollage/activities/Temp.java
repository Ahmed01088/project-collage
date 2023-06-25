package com.example.projectcollage.activities;

import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projectcollage.R;
import com.example.projectcollage.models.VideoUser;

import java.util.ArrayList;
import java.util.Locale;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;
import io.agora.rtc2.video.VideoEncoderConfiguration;

public class Temp {
    private void shareScreen(){
//        if (!isSharingScreen){
//            if (Build.VERSION.SDK_INT>Build.VERSION_CODES.Q) {
//                fgServiceIntent = new Intent(this, MainActivity.class);
//                startForegroundService(fgServiceIntent);
//            }
//            DisplayMetrics metrics=new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            ScreenCaptureParameters screenCaptureParameters=new ScreenCaptureParameters();
//            screenCaptureParameters.captureVideo=true;
//            screenCaptureParameters.videoCaptureParameters.width=metrics.widthPixels;
//            screenCaptureParameters.videoCaptureParameters.height=metrics.heightPixels;
//            screenCaptureParameters.videoCaptureParameters.framerate=DEFAULT_SHARE_FRAME_RATE;
//            screenCaptureParameters.captureAudio=true;
//            screenCaptureParameters.audioCaptureParameters.captureSignalVolume=50;
//            agoraEngine.startScreenCapture(screenCaptureParameters);
//            isSharingScreen=true;
//
//        }
    }
//
//private void fetchToken(int uid,String channelName,int tokenRole){
//    String URLString="";
//    OkHttpClient client=new OkHttpClient();
//    Request request=new Request.Builder()
//            .url(URLString)
//            .header("Content-Type", "application/json;charset=UTF-8")
//            .get()
//            .build();
//    Call call=client.newCall(request);
//    call.enqueue(new Callback() {
//        @Override
//        public void onFailure(@NonNull Call call, @NonNull IOException e) {
//            Toast.makeText(LiveStreamActivity.this, ""+e, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//            if (response.isSuccessful()){
//                Gson gson=new Gson();
//                String result=response.body().string();
//                Map map=gson.fromJson(result, Map.class);
//                String _token=map.get("rtcToken").toString();
//
//                Toast.makeText(LiveStreamActivity.this, ""+_token, Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    });   }

//        binding.joinButton.setOnClickListener(view -> {
//            if (!isJoined){
//                joinChannel();
//                isJoined=true;
//                binding.joinButton.setText("مغادره");
//            }else {
//                leaveChannel();
//                isJoined=false;
//                binding.joinButton.setText("دخول");
//
//            }
//        });
//    private void actionButtons(){
//        binding.openCloseCameraVideoLive.setOnClickListener(view -> closeOpenCamera());
//        binding.openCloseSoundLive.setOnClickListener(view -> closeOpenAudio());
//        binding.switchCamera.setOnClickListener(view -> switchCamera());
//
//    }
//    private void switchCamera(){
//        agoraEngine.switchCamera();
//    }
//    private void closeOpenCamera(){
//        if (isCameraOpen&&isJoined){
//            isCameraOpen=false;
//            agoraEngine.disableVideo();
//            binding.openCloseCameraVideoLive.setImageResource(R.drawable.ic_videocam_off);
//
//        }else if (isJoined){
//            AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.AlertDialogStyle)
//                    .setTitle("ساله تاكيد")
//                    .setMessage("هل تريد فتح الكاميرا ؟")
//                    .setPositiveButton("نعم", (dialogInterface, i) -> {
//                        isCameraOpen=true;
//                        agoraEngine.enableVideo();
//                        binding.openCloseCameraVideoLive.setImageResource(R.drawable.ic_videocam);
//                    }).setNegativeButton("الغاء", (dialogInterface, i) -> {
//                    }).setIcon(R.drawable.ic_videocam);
//            builder.show();
//        }else {
//            showMessage("انت غير موجود في الايف");
//        }
//    }
//    private void closeOpenAudio(){
//        if (isAudioOpen&&isJoined){
//            isAudioOpen=false;
//            agoraEngine.disableAudio();
//            binding.openCloseSoundLive.setImageResource(R.drawable.ic_mic_off);
//        }else if (isJoined){
//            AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.AlertDialogStyle)
//                    .setTitle("ساله تاكيد")
//                    .setMessage("هل تشغيل الصوت ؟")
//                    .setPositiveButton("نعم", (dialogInterface, i) -> {
//                        isAudioOpen=true;
//                        agoraEngine.enableAudio();
//                        binding.openCloseSoundLive.setImageResource(R.drawable.ic_mic);
//                    }).setNegativeButton("الغاء", (dialogInterface, i) -> {
//                    }).setIcon(R.drawable.ic_mic);
//            builder.show();
//            isAudioOpen=true;
//            agoraEngine.enableAudio();
//
//        }else {
//            showMessage("انت غير موجود في الايف");
//        }
//    }
//    private void setupRemoteVideo(int uid) {
//        remoteSurfaceView=new SurfaceView(this);
//        remoteSurfaceView.setZOrderMediaOverlay(true);
//        binding.remoteVideoViewContainer.addView(remoteSurfaceView);
//        agoraEngine.setupRemoteVideo(new VideoCanvas(remoteSurfaceView,VideoCanvas.RENDER_MODE_FIT,uid));
//        remoteSurfaceView.setVisibility(View.VISIBLE);
//    }
//    private void setupLocalVideo() {
//        localSurfaceView=new SurfaceView(this);
//        users.add(new VideoUser(localSurfaceView));
//        adapter.notifyItemInserted(users.size());
//        agoraEngine.setupLocalVideo(new VideoCanvas(localSurfaceView,VideoCanvas.RENDER_MODE_HIDDEN,0));
//        agoraEngine.enableVideo();
//    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    users=new ArrayList<>();
//    setupVideoSDKEngine();
//    adapter=new VideoLiveUserAdapter(this, users, agoraEngine);
//        binding.rvAllUserVideo.setAdapter(adapter);
//    LinearLayoutManager manager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
//        binding.rvAllUserVideo.setLayoutManager(manager);
//        binding.btnCross.setOnClickListener(view -> {
//        if (isUsersHide){
//            binding.rvAllUserVideo.setVisibility(View.VISIBLE);
//            binding.btnCross.setImageResource(R.drawable.ic_close);
//            isUsersHide =false;
//        }else {
//            binding.rvAllUserVideo.setVisibility(View.GONE);
//            binding.btnCross.setImageResource(R.drawable.ic_settings_backup_restore);
//            isUsersHide =true;
//        }
//    });
//        if (!checkSelfPermission()){
//        ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
//    }
//        binding.joinButton.setOnClickListener(view -> {
//        binding.labelStudentCounter.setText(String.format(Locale.ENGLISH," %d طالب حاضر",users.size()+1));
//        if (isJoinChannel){
//            leaveChannel();
//            adapter.removeVideoUser(adapter.getItemCount()-1);
//            binding.joinButton.setText("دخول");
//            isJoinChannel=false;
//        }else {
//            joinChannel();
//            binding.joinButton.setText("خروج");
//            isJoinChannel=true;
//        }
//    });
//        binding.switchCamera.setOnClickListener(view -> {
//        if (isJoinChannel){
//            agoraEngine.switchCamera();
//        }
//    });
//        binding.openCloseSoundLive.setOnClickListener(view -> {
//        if(isJoinChannel){
//            if (isAudioMute){
//                agoraEngine.muteLocalAudioStream(true);
//                binding.openCloseSoundLive.setImageResource(R.drawable.ic_mic_off);
//                isAudioMute=false;
//            }else {
//                agoraEngine.muteLocalAudioStream(false);
//                binding.openCloseSoundLive.setImageResource(R.drawable.ic_mic);
//                isAudioMute=true;
//            }
//        }
//
//
//    });
//        binding.openCloseCameraVideoLive.setOnClickListener(view -> {
//        if (isJoinChannel){
//            if (isVideoMute){
//                agoraEngine.muteLocalVideoStream(true);
//                binding.openCloseCameraVideoLive.setImageResource(R.drawable.ic_videocam_off);
//                isVideoMute=false;
//            }else {
//                agoraEngine.muteLocalVideoStream(false);
//                binding.openCloseCameraVideoLive.setImageResource(R.drawable.ic_videocam);
//                isVideoMute=true;
//            }
//        }
//
//    });
//
//}
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        agoraEngine.stopPreview();
//        agoraEngine.leaveChannel();
//        new Thread(()->{
//            RtcEngine.destroy();
//            agoraEngine=null;
//        }).start();
//    }
//    void showMessage(String message){
//        runOnUiThread(() -> Toast.makeText(LiveStreamActivity.this, message, Toast.LENGTH_SHORT).show());
//    }
//    private final IRtcEngineEventHandler mEventHandler=new IRtcEngineEventHandler() {
//        @Override
//        public void onUserJoined(int uid, int elapsed) {
//            showMessage("Remote user joined " + uid);
//            runOnUiThread(() -> setupRemoteVideo(uid));
//        }
//        @Override
//        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
//            isJoinChannel = true;
//            showMessage("Joined Channel " + channel);
//        }
//        @Override
//        public void onUserOffline(int uid, int reason) {
//            showMessage("Remote user offline " + uid + " " + reason);
//            runOnUiThread(() -> remoteSurfaceView.setVisibility(View.GONE));
//        }
//    };
//    //done
//    private void setupVideoSDKEngine(){
//        try{
//            RtcEngineConfig config=new RtcEngineConfig();
//            config.mContext=this;
//            config.mAppId=appId;
//            config.mEventHandler=mEventHandler;
//            agoraEngine=RtcEngine.create(config);
//            agoraEngine.enableVideo();
//            agoraEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
//            agoraEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
//            agoraEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(VideoEncoderConfiguration.VD_640x360,VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,VideoEncoderConfiguration.STANDARD_BITRATE, VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));
//        }catch (Exception e){
//            showMessage(e.toString());
//        }
//    }
//    private void setupRemoteVideo(int uid) {
//        remoteSurfaceView=new SurfaceView(getBaseContext());
//        remoteSurfaceView.setZOrderMediaOverlay(true);
//        binding.remoteVideoViewContainer.addView(remoteSurfaceView);
//        agoraEngine.setupRemoteVideo(new VideoCanvas(remoteSurfaceView,VideoCanvas.RENDER_MODE_FIT,uid));
//        remoteSurfaceView.setVisibility(View.VISIBLE);
//    }
//    private void setupLocalVideo() {
//        localSurfaceView=new SurfaceView(this);
//        VideoUser user=new VideoUser(localSurfaceView);
//        agoraEngine.setupLocalVideo(new VideoCanvas(localSurfaceView,VideoCanvas.RENDER_MODE_HIDDEN,0));
//        agoraEngine.enableVideo();
//        if (!isJoinChannel){
//            adapter.addVideoUser(user);
//        }else {
//            adapter.removeVideoUser(adapter.getItemCount()-1);
//        }
//    }
//    public void joinChannel() {
//        if (checkSelfPermission()) {
//            ChannelMediaOptions options = new ChannelMediaOptions();
//            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION;
//            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
//            setupLocalVideo();
//            localSurfaceView.setVisibility(View.VISIBLE);
//            agoraEngine.startPreview();
//            agoraEngine.joinChannel(token, channelName, uid, options);
//            Toast.makeText(getApplicationContext(), "تم دخول القناة", Toast.LENGTH_SHORT).show();
//            binding.joinButton.setText("خروج");
//        } else {
//            Toast.makeText(getApplicationContext(), "لم يتم منح اذن الوصول", Toast.LENGTH_SHORT).show();
//        }
//    }
//    public void leaveChannel() {
//        if (!isJoinChannel) {
//            showMessage("انت غير موجود في القناة");
//        } else {
//            agoraEngine.leaveChannel();
//            showMessage("تم الخروج من القناة");
//            binding.joinButton.setText("دخول");
//            if (remoteSurfaceView != null) remoteSurfaceView.setVisibility(View.GONE);
//            if (localSurfaceView != null) localSurfaceView.setVisibility(View.GONE);
//            isJoinChannel = false;
//        }
    /*    void updateMediaPublishOptions(boolean publishScreen) {
        ChannelMediaOptions mediaOptions = new ChannelMediaOptions();
        mediaOptions.publishCameraTrack = !publishScreen;
        mediaOptions.publishMicrophoneTrack = !publishScreen;
        mediaOptions.publishScreenCaptureVideo = publishScreen;
        mediaOptions.publishScreenCaptureAudio = publishScreen;
        agoraEngine.updateChannelMediaOptions(mediaOptions);
    }
    private void fetchToken(int uid, String channelName, int tokenRole) {
        // Prepare the Url
        String URLString = com.example.projectcollage.utiltis.Constants.BASE_URL + "/rtc/" + channelName + "/" + tokenRole + "/"
                + "uid" + "/" + uid + "/?expiry=" + com.example.projectcollage.utiltis.Constants.TOKEN_EXPIRY;

        OkHttpClient client = new OkHttpClient();

        // Instantiate the RequestQueue.
        Request request = new Request.Builder()
                .url(URLString)
                .header("Content-Type", "application/json; charset=UTF-8")
                .get()
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {


            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i("Token Received", token);
                    Gson gson = new Gson();
                    String result = response.body().toString();
                    Map map = gson.fromJson(result, Map.class);
                    String _token = map.get("rtcToken").toString();
                    // Use the token to join a channel or to renew an expiring token
                    setToken(_token);
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("Token Fetching Failed", e.getMessage());
            }
        });
    }
    void setToken(String newValue) {
        token = newValue;
        if (!isJoined) { // Join a channel
            ChannelMediaOptions options = new ChannelMediaOptions();
            // For a Video call, set the channel profile as COMMUNICATION.
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION;
            // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
            // Start local preview.
            agoraEngine.startPreview();
            Toast.makeText(getApplicationContext(), "تم البدء في البث", Toast.LENGTH_SHORT).show();

            // Join the channel with a token.
            agoraEngine.joinChannel(token, channelName, uid, options);
        } else { // Already joined, renew the token by calling renewToken
            agoraEngine.renewToken(token);
            showMessage("Token renewed");
        }
    }
        public void shareScreen(View view) {
        Button sharingButton = (Button) view;

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
                sharingButton.setText("Stop Screen Sharing");
            }
        } else { // Stop sharing
            agoraEngine.stopScreenCapture();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (fgServiceIntent!=null) stopService(fgServiceIntent);
            }
            isSharingScreen = false;
            sharingButton.setText("Start Screen Sharing");

            // Restore camera and microphone publishing
            updateMediaPublishOptions(false);
            setupLocalVideo();
        }
    }
    private void startScreenSharePreview() {
        // Create render view by RtcEngine
        FrameLayout container = findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = new SurfaceView(getBaseContext());
        if (container.getChildCount() > 0) {
            container.removeAllViews();
        }
        // Add SurfaceView to the local FrameLayout
        container.addView(surfaceView,
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

        // Setup local video to render your local camera preview
        agoraEngine.setupLocalVideo(new VideoCanvas(surfaceView, Constants.RENDER_MODE_FIT, 0));

        agoraEngine.startPreview(Constants.VideoSourceType.VIDEO_SOURCE_SCREEN_PRIMARY);
    }

*/
}
