package com.example.projectcollage.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectcollage.R;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Message;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import java.util.ArrayList;

import retrofit2.Call;

public class ChatUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int MESSAGE_TYPE_SENDER=1;
    public static final int MESSAGE_TYPE_RECEIVER =2;
    SharedPreferences sharedPreferences;
    Context context;
    ArrayList<Message>messages;
    public ChatUserAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
        sharedPreferences=context.getSharedPreferences(Constants.DATA,Context.MODE_PRIVATE);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MESSAGE_TYPE_SENDER){
            View view= LayoutInflater.from(context).inflate(R.layout.sender_item_message_chat,parent, false);
            return new SenderHolder(view);
        }else {
            View view= LayoutInflater.from(context).inflate(R.layout.reciver_item_message_chat,parent, false);
            return new ReceiverHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,int position) {
        Message message=messages.get(position);
        if (holder.getClass()==SenderHolder.class){
            ((SenderHolder)holder).message.setText(message.getContent());
            ((SenderHolder)holder).time.setText(message.getSentAt());
            if (message.getImage()!=null){

                Glide.with(context).load(Constants.BASE_URL_PATH_MESSAGES+message.getImage()).into(((SenderHolder)holder).image);
            }else
                ((SenderHolder)holder).image.setVisibility(View.GONE);
        }else {
            ((ReceiverHolder)holder).message.setText(message.getContent());
            ((ReceiverHolder)holder).time.setText(message.getSentAt());
            if (message.getImage()!=null){
               Glide.with(context).load(Constants.BASE_URL_PATH_MESSAGES+message.getImage()).into(((ReceiverHolder)holder).image);
            }else
                ((ReceiverHolder)holder).image.setVisibility(View.GONE);

        }
       }
    @Override
    public int getItemCount() {
        return messages.size();
    }
    public static class SenderHolder extends RecyclerView.ViewHolder{
        TextView time,message;
        ImageView image;
           public SenderHolder(@NonNull View itemView) {
            super(itemView);
               time=itemView.findViewById(R.id.date_item_sender_user);
               message=itemView.findViewById(R.id.senderMessageUser);
               image=itemView.findViewById(R.id.image_user_sender);
           }
    }
    public static class ReceiverHolder extends RecyclerView.ViewHolder{
        TextView time,message;
        ImageView image;
        public ReceiverHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.date_item_reciver_user);
            message=itemView.findViewById(R.id.reciverMessageUser);
            image=itemView.findViewById(R.id.image_user_reciver);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (sharedPreferences.getInt(Constants.UID,0)==messages.get(position).getSender()){
            return MESSAGE_TYPE_SENDER;

      }else {
            return MESSAGE_TYPE_RECEIVER;
       }
    }
    public  void removeItem(int position){
        messages.remove(position);
        notifyItemRemoved(position);
    }
    public void deleteMessageById(int id,int position){
        Call<Data<Message>>call= RetrofitClientLaravelData.getInstance().getApiInterface().deleteMessageById(id);
        call.enqueue(new retrofit2.Callback<Data<Message>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Message>> call, @NonNull retrofit2.Response<Data<Message>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "تم ازالة الرسالة ...", Toast.LENGTH_SHORT).show();
                    removeItem(position);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<Message>> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
