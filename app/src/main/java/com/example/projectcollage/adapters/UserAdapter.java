package com.example.projectcollage.adapters;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectcollage.R;
import com.example.projectcollage.activities.ViewMessageUsersActivity;
import com.example.projectcollage.customView.CustomDialog;
import com.example.projectcollage.model.Chat;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;

public class UserAdapter<T> extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    ArrayList<Chat> chats;
    T T;
    public UserAdapter(Context context, ArrayList<Chat> chats) {
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
        holder.itemView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(context,R.style.AlertDialogStyle);
            builder.setTitle("هل تريد حذف المحادثة ؟");
            builder.setPositiveButton("نعم", (dialog, which) -> {
                deleteChatById(chat.getId());
                chats.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,chats.size());
            });
            builder.setNegativeButton("لا", (dialog, which) -> dialog.dismiss());
            builder.show();

            return true;
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
    private void deleteChatById(int id){
        Call<Data<Chat>> call= RetrofitClientLaravelData.getInstance().getApiInterface().deleteChatById(id);
        call.enqueue(new retrofit2.Callback<Data<Chat>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Chat>> call, retrofit2.Response<Data<Chat>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "تم ازاله المحادثة ", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<Chat>> call, @NonNull Throwable t) {
            }
        });
    }
}
