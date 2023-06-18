package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;

import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.video.VideoCanvas;

class RemoteVideoAdapter extends RecyclerView.Adapter<RemoteVideoAdapter.RemoteVideoViewHolder> {
    private int[] mUserIds;
    Context context;
    static RtcEngine mRtcEngine;

    public RemoteVideoAdapter(int[] mUserIds, Context context, RtcEngine mRtcEngine) {
        this.mUserIds = mUserIds;
        this.context = context;
        this.mRtcEngine = mRtcEngine;
    }

    public RemoteVideoAdapter() {
        mUserIds = new int[5];
    }

    public void addUser(int userId) {
        for (int i = 0; i < mUserIds.length; i++) {
            if (mUserIds[i] == 0) {
                mUserIds[i] = userId;
                notifyItemInserted(i);
                break;
            }
        }
    }

    public void removeUser(int userId) {
        for (int i = 0; i < mUserIds.length; i++) {
            if (mUserIds[i] == userId) {
                mUserIds[i] = 0;
                notifyItemRemoved(i);
                break;
            }
        }
    }

    @NonNull
    @Override
    public RemoteVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_remote_video, parent, false);
        return new RemoteVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemoteVideoViewHolder holder, int position) {
        int userId = mUserIds[position];
        holder.bindUser(userId);
    }

    @Override
    public int getItemCount() {
        return mUserIds.length;
    }

    public static class RemoteVideoViewHolder extends RecyclerView.ViewHolder {
        private SurfaceView mRemoteView;

        public RemoteVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            mRemoteView = itemView.findViewById(R.id.remote_view);
        }

        public void bindUser(int userId) {
            if (userId != 0) {
                mRemoteView.setZOrderMediaOverlay(true);
                mRemoteView.setZOrderOnTop(true);
                mRtcEngine.setupRemoteVideo(new VideoCanvas(mRemoteView, VideoCanvas.RENDER_MODE_FIT, userId));
            } else {
                mRemoteView.setZOrderMediaOverlay(false);
                mRemoteView.setZOrderOnTop(false);
                mRemoteView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));
            }
        }
    }
}