package com.example.projectcollage.adapters;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.projectcollage.R;
import com.example.projectcollage.activities.DetailsActivity;
import com.example.projectcollage.model.Message;
import com.example.projectcollage.utiltis.Constants;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatClassroomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int MESSAGE_TYPE_SENDER=1;
    public static final int MESSAGE_TYPE_RECEIVER =2;

    private final Context context;
    private final ArrayList<Message> messages;
    SharedPreferences sharedPreferences;

    public ChatClassroomAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
        sharedPreferences=context.getSharedPreferences(Constants.DATA,Context.MODE_PRIVATE);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==MESSAGE_TYPE_SENDER){
            view= LayoutInflater.from(context).inflate(R.layout.sender_item_message_classroom,parent,false);
            return new SenderHolder(view);
        }else {
            view= LayoutInflater.from(context).inflate(R.layout.reciver_item_message_classroom,parent,false);
            return new ReceiverHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,int position) {
        Message message=messages.get(position);
        if (holder.getClass()== SenderHolder.class){
            ((SenderHolder)holder).message.setText(message.getContent());
            ((SenderHolder)holder).date.setText(message.getSentAt());
            if (message.getImageBitmap()!=null){
                ((SenderHolder)holder).image.setImageBitmap(message.getImageBitmap());
            }
            if (sharedPreferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[1])&&message.getSender()==sharedPreferences.getInt(Constants.UID,0)){
                ((SenderHolder)holder).name.setText(String.format("الدكتور %s", messages.get(position).getSenderName()));
                ((SenderHolder)holder).ic_verified.setVisibility(View.VISIBLE);
            }else{
                ((SenderHolder)holder).name.setText(messages.get(position).getSenderName());
                ((SenderHolder)holder).ic_verified.setVisibility(View.GONE);
            }
            if (message.getImage()!=null){
                Glide.with(context).load(Constants.BASE_URL_PATH_MESSAGES+message.getImage())
                        .into(((SenderHolder)holder).image);
            }else
                ((SenderHolder)holder).image.setVisibility(View.GONE);
        }else {
            ((ReceiverHolder)holder).message.setText(message.getContent());
            ((ReceiverHolder)holder).date.setText(message.getSentAt());
            if (message.getImageBitmap()!=null){
                ((ReceiverHolder)holder).image.setImageBitmap(message.getImageBitmap());
            }
            ((ReceiverHolder)holder).studentClassroomPic.setOnClickListener(v -> {
                Intent intent=new Intent(context, DetailsActivity.class);
                ActivityOptions options= ActivityOptions.makeClipRevealAnimation(holder.itemView,holder.itemView.getWidth()/2,holder.itemView.getHeight()/2,300,300);
                intent.putExtra(Constants.IMAGE,Constants.BASE_URL_PATH_USERS+messages.get(position).getSenderImage());
                context.startActivity(intent,options.toBundle());
            });
            if (sharedPreferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[1])&&message.getSender()==sharedPreferences.getInt(Constants.UID,0)){
                ((ReceiverHolder)holder).name.setText(String.format("الدكتور %s", messages.get(position).getSenderName()));
                ((ReceiverHolder)holder).ic_verified.setVisibility(View.VISIBLE);
            }else{
                ((ReceiverHolder)holder).name.setText(messages.get(position).getSenderName());
                ((ReceiverHolder)holder).ic_verified.setVisibility(View.GONE);
            }
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

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    public static class SenderHolder extends RecyclerView.ViewHolder{
        TextView message,date,name;
        ImageView image,ic_verified;
        public SenderHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.senderItemMessage);
            date=itemView.findViewById(R.id.date_item_sender);
            image=itemView.findViewById(R.id.image_classroom_sender);
            name=itemView.findViewById(R.id.person_send_message_s);
            ic_verified=itemView.findViewById(R.id.ic_verified_account_classroom_s);


        }
    }
    public static class ReceiverHolder extends RecyclerView.ViewHolder{
        TextView message,date,name;
        ImageView image,ic_verified,studentClassroomPic;
        public ReceiverHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.reciverItemMessage);
            date=itemView.findViewById(R.id.date_item_reciver);
            image=itemView.findViewById(R.id.image_classroom_reciver);
            name=itemView.findViewById(R.id.person_send_message_r);
            ic_verified=itemView.findViewById(R.id.ic_verified_account_classroom);
            studentClassroomPic=itemView.findViewById(R.id.studentClassroomPic);

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

}
