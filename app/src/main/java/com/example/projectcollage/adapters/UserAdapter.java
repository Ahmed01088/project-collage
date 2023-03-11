package com.example.projectcollage.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.DragEvent;
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
import com.example.projectcollage.database.Database;
import com.example.projectcollage.models.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    ArrayList<User>users;
    Database database;

    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_user, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(users.get(position).getName());
        holder.profileImage.setImageBitmap(users.get(position).getImageBitmap());
        holder.itemView.setOnClickListener(view -> {
//            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,200,view.getHeight()/2,view.getWidth(),view.getHeight());
            ViewMessageUsersActivity.data=users.get(position).getImageBitmap();
            Intent intent=new Intent(context, ViewMessageUsersActivity.class);
            intent.putExtra("uid", users.get(position).getUid());
            intent.putExtra("name",users.get(position).getName());
            notifyDataSetChanged();
            notifyItemInserted(users.size());
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,view.getWidth()/2,view.getHeight()/2,300,300);
            context.startActivity(intent,options.toBundle());

        });
        holder.itemView.setOnLongClickListener(view -> {
            database=new Database(context);
            database.deleteUser(users.get(position).getUid());
            users.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
            return false;
        });
        holder.profileImage.setOnClickListener(view -> {
            CustomDialog dialog=new CustomDialog(context);
            dialog.imageData=users.get(position).getImageBitmap();
            dialog.uid=users.get(position).getUid();
            dialog.name=users.get(position).getName();
            dialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
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
