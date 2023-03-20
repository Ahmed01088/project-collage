package com.example.projectcollage.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.VideoLiveUserAdapter;
import com.example.projectcollage.databinding.ActivityLiveStreemBinding;
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

public class LiveStreamActivity extends AppCompatActivity{
    ActivityLiveStreemBinding binding;
    public static final int PERMISSION_REQ_ID=22;
    public static final String[] REQUESTED_PERMISSIONS={
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };
    private boolean checkSelfPermission(){
        return ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED;
    }
    private final String appId="5cc56522815f4f5794df128f72d50cae";
    private final String channelName="Collage";
    private final String token="007eJxTYKh4ddJqrTjILrbHJ95XyTtTngcmit5RCYxL6crIerYxg4FBtPkZFMzUyMj";
    private final int uid=0;
    private RtcEngine agoraEngine;
    private SurfaceView localSurfaceView;
    private SurfaceView remoteSurfaceView;
    private boolean isUsersHide =false;
    private boolean isJoinChannel=false;
    private boolean isVideoMute=false;
    private boolean isAudioMute=false;
    ArrayList<VideoUser>users;
    VideoLiveUserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLiveStreemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        getWindow().setStatusBarColor(getColor(R.color.statesBar));
        users=new ArrayList<>();
        setupVideoSDKEngine();
        adapter=new VideoLiveUserAdapter(this, users, agoraEngine);
        binding.rvAllUserVideo.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        binding.rvAllUserVideo.setLayoutManager(manager);
        binding.btnCross.setOnClickListener(view -> {
            if (isUsersHide){
                binding.rvAllUserVideo.setVisibility(View.VISIBLE);
                binding.btnCross.setImageResource(R.drawable.ic_close);
                isUsersHide =false;
           }else {
                binding.rvAllUserVideo.setVisibility(View.GONE);
                binding.btnCross.setImageResource(R.drawable.ic_settings_backup_restore);
                isUsersHide =true;
            }
        });
        if (!checkSelfPermission()){
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        binding.joinButton.setOnClickListener(view -> {
        binding.labelStudentCounter.setText(String.format(Locale.ENGLISH," %d طالب حاضر",users.size()+1));
              if (isJoinChannel){
                  leaveChannel();
                  adapter.removeVideoUser(adapter.getItemCount()-1);
                  binding.joinButton.setText("دخول");
                  isJoinChannel=false;
              }else {
                    joinChannel();
                    binding.joinButton.setText("خروج");
                    isJoinChannel=true;
              }
            });
        binding.switchCamera.setOnClickListener(view -> {
            if (isJoinChannel){
                agoraEngine.switchCamera();
            }
        });
        binding.openCloseSoundLive.setOnClickListener(view -> {
            if(isJoinChannel){
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
        binding.openCloseCameraVideoLive.setOnClickListener(view -> {
            if (isJoinChannel){
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

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        agoraEngine.stopPreview();
        agoraEngine.leaveChannel();
        new Thread(()->{
            RtcEngine.destroy();
            agoraEngine=null;
        }).start();
    }
    void showMessage(String message){
        runOnUiThread(() -> Toast.makeText(LiveStreamActivity.this, message, Toast.LENGTH_SHORT).show());
    }
    private final IRtcEngineEventHandler mEventHandler=new IRtcEngineEventHandler() {
            @Override
            public void onUserJoined(int uid, int elapsed) {
                showMessage("Remote user joined " + uid);
                runOnUiThread(() -> setupRemoteVideo(uid));
            }
            @Override
            public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
                isJoinChannel = true;
                showMessage("Joined Channel " + channel);
            }
            @Override
            public void onUserOffline(int uid, int reason) {
                showMessage("Remote user offline " + uid + " " + reason);
                runOnUiThread(() -> remoteSurfaceView.setVisibility(View.GONE));
            }
    };
    //done
    private void setupVideoSDKEngine(){
        try{
            RtcEngineConfig config=new RtcEngineConfig();
            config.mContext=this;
            config.mAppId=appId;
            config.mEventHandler=mEventHandler;
            agoraEngine=RtcEngine.create(config);
            agoraEngine.enableVideo();
            agoraEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
            agoraEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
            agoraEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(VideoEncoderConfiguration.VD_640x360,VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,VideoEncoderConfiguration.STANDARD_BITRATE, VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));
        }catch (Exception e){
            showMessage(e.toString());
        }
    }
    private void setupRemoteVideo(int uid) {
        remoteSurfaceView=new SurfaceView(getBaseContext());
        remoteSurfaceView.setZOrderMediaOverlay(true);
        binding.remoteVideoViewContainer.addView(remoteSurfaceView);
        agoraEngine.setupRemoteVideo(new VideoCanvas(remoteSurfaceView,VideoCanvas.RENDER_MODE_FIT,uid));
        remoteSurfaceView.setVisibility(View.VISIBLE);
           }
    private void setupLocalVideo() {
        localSurfaceView=new SurfaceView(this);
        VideoUser user=new VideoUser(localSurfaceView);
        agoraEngine.setupLocalVideo(new VideoCanvas(localSurfaceView,VideoCanvas.RENDER_MODE_HIDDEN,0));
        agoraEngine.enableVideo();
        if (!isJoinChannel){
            adapter.addVideoUser(user);
        }else {
            adapter.removeVideoUser(adapter.getItemCount()-1);
        }
    }
    public void joinChannel() {
        if (checkSelfPermission()) {
            ChannelMediaOptions options = new ChannelMediaOptions();
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION;
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
            setupLocalVideo();
            localSurfaceView.setVisibility(View.VISIBLE);
            agoraEngine.startPreview();
            agoraEngine.joinChannel(token, channelName, uid, options);
            Toast.makeText(getApplicationContext(), "تم دخول القناة", Toast.LENGTH_SHORT).show();
            binding.joinButton.setText("خروج");
        } else {
            Toast.makeText(getApplicationContext(), "لم يتم منح اذن الوصول", Toast.LENGTH_SHORT).show();
        }
    }
    public void leaveChannel() {
        if (!isJoinChannel) {
            showMessage("انت غير موجود في القناة");
        } else {
            agoraEngine.leaveChannel();
            showMessage("تم الخروج من القناة");
            binding.joinButton.setText("دخول");
            if (remoteSurfaceView != null) remoteSurfaceView.setVisibility(View.GONE);
            if (localSurfaceView != null) localSurfaceView.setVisibility(View.GONE);
            isJoinChannel = false;
        }
    }

}

