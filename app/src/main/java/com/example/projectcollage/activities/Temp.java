package com.example.projectcollage.activities;

import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.projectcollage.R;
import com.example.projectcollage.models.VideoUser;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.video.VideoCanvas;

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


}
