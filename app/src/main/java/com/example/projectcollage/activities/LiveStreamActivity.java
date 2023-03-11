package com.example.projectcollage.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
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
    private boolean isUserHide=false;
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
            if (isUserHide){
                binding.rvAllUserVideo.setVisibility(View.VISIBLE);
                binding.btnCross.setImageResource(R.drawable.ic_close);
                isUserHide=false;
           }else {
                binding.rvAllUserVideo.setVisibility(View.GONE);
                binding.btnCross.setImageResource(R.drawable.ic_settings_backup_restore);
                isUserHide=true;
            }
        });
        if (!checkSelfPermission()){
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        binding.joinButton.setOnClickListener(view -> {
        binding.labelStudentCounter.setText(String.format(Locale.ENGLISH," %d طالب حاضر",adapter.getItemCount()+1));
            joinChannel();
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
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            showMessage("joined channel "+channel);

        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
            showMessage("Remote user joined "+uid);
            runOnUiThread(() -> setupRemoteVideo(uid));
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            showMessage("Remote user offline " +uid + " "+ reason);
            runOnUiThread(() -> remoteSurfaceView.setVisibility(View.GONE));

        }

    };
    private void setupVideoSDKEngine(){
        try{
            RtcEngineConfig config=new RtcEngineConfig();
            config.mContext=this;
            config.mAppId=appId;
            config.mEventHandler=mEventHandler;
            agoraEngine=RtcEngine.create(config);
            agoraEngine.enableVideo();
        }catch (Exception e){
            showMessage(e.toString());
        }
    }

    private void setupRemoteVideo(int uid) {
        remoteSurfaceView=new SurfaceView(this);
        remoteSurfaceView.setZOrderMediaOverlay(true);
        binding.remoteVideoViewContainer.addView(remoteSurfaceView);
        agoraEngine.setupRemoteVideo(new VideoCanvas(remoteSurfaceView,VideoCanvas.RENDER_MODE_FIT,uid));
        remoteSurfaceView.setVisibility(View.VISIBLE);
    }
    private void setupLocalVideo() {
        localSurfaceView=new SurfaceView(this);
        users.add(new VideoUser(localSurfaceView));
        adapter.notifyItemInserted(users.size());
        agoraEngine.setupLocalVideo(new VideoCanvas(localSurfaceView,VideoCanvas.RENDER_MODE_HIDDEN,0));
        agoraEngine.enableVideo();
    }
    public void joinChannel(){
        if (checkSelfPermission()){
            ChannelMediaOptions options=new ChannelMediaOptions();
            options.channelProfile= Constants.CHANNEL_PROFILE_COMMUNICATION;
            options.clientRoleType=Constants.CLIENT_ROLE_BROADCASTER;
            setupLocalVideo();
            showMessage("تم الدخول ");
            localSurfaceView.setVisibility(View.VISIBLE);
            agoraEngine.startPreview();
            agoraEngine.joinChannel(token, channelName,uid, options);

        }else {
            Toast.makeText(this, "الوصول ممنوع", Toast.LENGTH_SHORT).show();
        }
    }

}

