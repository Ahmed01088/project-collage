package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.models.VideoUser;

import java.util.ArrayList;

import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.video.VideoCanvas;

public class VideoLiveUserAdapter extends RecyclerView.Adapter<VideoLiveUserAdapter.ViewHolder> {
    private Context context;
    private ArrayList<VideoUser>users;
    RtcEngine agoraEngine;

    public VideoLiveUserAdapter(Context context, ArrayList<VideoUser> users,RtcEngine agoraEngine)
    {
        this.context = context;
        this.users = users;
        this.agoraEngine=agoraEngine;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_remote_video_users,viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
//   holder.local_video_view_container.addView(users.get(i).getSurfaceView());
        holder.localVideo.setZOrderMediaOverlay(true);
        holder.localVideo.setVisibility(View.VISIBLE);
        agoraEngine.setupLocalVideo(new VideoCanvas(holder.localVideo,VideoCanvas.RENDER_MODE_HIDDEN,0));
        agoraEngine.enableVideo();
        agoraEngine.enableAudio();

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        FrameLayout local_video_view_container;
        SurfaceView localVideo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            local_video_view_container =itemView.findViewById(R.id.local_video_view_container);
            localVideo =itemView.findViewById(R.id.local_video);
        }
    }
    public void addVideoUser(VideoUser user){
        users.add(user);
        notifyDataSetChanged();
        agoraEngine.joinChannel(user.getToken(), "channelName", "", user.getUid());
    }
    public void removeVideoUser(int uid){
        users.remove(uid);
        notifyDataSetChanged();
        agoraEngine.leaveChannel();
    }
}
