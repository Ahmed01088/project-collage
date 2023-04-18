package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView .Adapter<CommentsAdapter.ViewHolder>{
    Context context;
    List<Comment> comments;
    public static int counterComments=0;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
        counterComments=comments.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(comments.get(position).getPersonName());
        holder.body.setText(comments.get(position).getCommentText());
        holder.time.setText(comments.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,body,time,replay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameComment);
            body=itemView.findViewById(R.id.body);
            time=itemView.findViewById(R.id.time);
            replay=itemView.findViewById(R.id.replay);
        }
    }
}
