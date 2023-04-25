package com.example.projectcollage.adapters;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectcollage.R;
import com.example.projectcollage.activities.ViewMessageUsersActivity;
import com.example.projectcollage.customView.CustomDialog;
import com.example.projectcollage.model.Chat;
import com.example.projectcollage.utiltis.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class UserAdapter<T> extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    ArrayList<Chat> chats;
    T T;
    public UserAdapter(Context context, ArrayList chats) {
        this.context = context;
        this.chats = chats;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_user, parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat=chats.get(position);
        holder.name.setText(chat.getReciverName());
        if (chat.getReciverImage()!=null){
            Picasso.get().load(Constants.BASE_URL_PATH_USERS+chat.getReciverImage()).into(holder.profileImage);
        }else {
            holder.profileImage.setImageResource(R.drawable.avatar);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context, ViewMessageUsersActivity.class);
            intent.putExtra(Constants.FULL_NAME,chat.getReciverName());
            intent.putExtra(Constants.IMAGE,chat.getReciverImage());
            intent.putExtra(Constants.CHAT_ID,chat.getId());
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(v,v.getWidth()/2,v.getHeight()/2,300,300);
            context.startActivity(intent,options.toBundle());
         });
        holder.profileImage.setOnClickListener(v -> {
            CustomDialog customDialog=new CustomDialog(context);
            customDialog.imageData=chat.getReciverImage();
            customDialog.name=chat.getReciverName();
            customDialog.chatId=chat.getId();
            customDialog.show();

        });

    }
    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView profileImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            profileImage=itemView.findViewById(R.id.profile_image_user_home);
        }
    }
}
