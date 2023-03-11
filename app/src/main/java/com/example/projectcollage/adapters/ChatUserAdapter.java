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
import com.example.projectcollage.activities.ShowImageActivity;
import com.example.projectcollage.database.Database;
import com.example.projectcollage.models.MessageUser;
import java.util.ArrayList;

import rm.com.audiowave.AudioWaveView;

public class ChatUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int MESSAGE_TYPE_SENDER=0;
    public static final int MESSAGE_TYPE_SENDER_VOICE=1;
    public static final int MESSAGE_TYPE_RECEIVER =2;
    public static final int MESSAGE_TYPE_RECEIVER_VOICE =3;
    Context context;
    ArrayList<MessageUser>messageUsers;
    Database database;
    private final String uid;
    public ChatUserAdapter(Context context, ArrayList<MessageUser> messageUsers,String uid) {
        this.context = context;
        this.messageUsers = messageUsers;
        this.uid=uid;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==MESSAGE_TYPE_SENDER){
            view= LayoutInflater.from(context).inflate(R.layout.sender_item_message_chat,parent,false);
            return new SenderHolder(view);
        }else if (viewType==MESSAGE_TYPE_RECEIVER){
            view= LayoutInflater.from(context).inflate(R.layout.reciver_item_message_chat,parent,false);
            return new ReceiverHolder(view);
        }else if (viewType==MESSAGE_TYPE_RECEIVER_VOICE){
            view= LayoutInflater.from(context).inflate(R.layout.item_voice_reciver,parent,false);
            return new VoiceReceiverHolder(view);
        }else {
            view= LayoutInflater.from(context).inflate(R.layout.item_voice_sender,parent,false);
            return new VoiceSenderHolder(view);

        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,int position) {
        database=new Database(context);
        if (holder.getClass()==SenderHolder.class){
            SenderHolder senderHolder= (SenderHolder) holder;
            senderHolder.message.setText(messageUsers.get(position).getMessage());
            senderHolder.time.setText(messageUsers.get(position).getTime());
            senderHolder.image.setOnClickListener(view -> {
                Intent intent=new Intent(context, ShowImageActivity.class);
                ShowImageActivity.DATA=messageUsers.get(position).getMessageImage();
                ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,
                        view.getWidth()/2,view.getHeight()/2,100,100);
                context.startActivity(intent,options.toBundle());

            });
            if (messageUsers.get(position).getMessage().isEmpty()){
                senderHolder.message.setVisibility(View.GONE);
            }else {
                senderHolder.message.setVisibility(View.VISIBLE);
            }
            if (!(messageUsers.get(position).getMessageImage()==null)) {
                senderHolder.image.setImageURI(messageUsers.get(position).getImageUri());
                senderHolder.image.setVisibility(View.VISIBLE);
                senderHolder.image.setImageBitmap(messageUsers.get(position).getMessageImage());

            }else {
                senderHolder.image.setVisibility(View.GONE);

            }
         }else if (holder.getClass()==VoiceReceiverHolder.class){

        }else if (holder.getClass()==VoiceSenderHolder.class){

        }
        else {
            ReceiverHolder receiverHolder= (ReceiverHolder) holder;
            receiverHolder.message.setText(messageUsers.get(position).getMessage());
            receiverHolder.time.setText(messageUsers.get(position).getTime());
            receiverHolder.image.setOnClickListener(view -> {
                Intent intent=new Intent(context, ShowImageActivity.class);
                ShowImageActivity.DATA=messageUsers.get(position).getMessageImage();
                ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,
                        view.getWidth()/2,view.getHeight()/2,100,100);
                context.startActivity(intent,options.toBundle());

            });
            if (!(messageUsers.get(position).getMessageImage()==null)) {
                receiverHolder.image.setImageURI(messageUsers.get(position).getImageUri());
                receiverHolder.image.setVisibility(View.VISIBLE);
                receiverHolder.image.setImageBitmap(messageUsers.get(position).getMessageImage());

            }else {
                receiverHolder.image.setVisibility(View.GONE);

            }
      }

        holder.itemView.setOnLongClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(context,R.style.AlertDialogStyle)
                    .setPositiveButton("مسح", (dialogInterface, i) -> {
                        boolean c=database.deleteMessageUser(messageUsers.get(position).getId(),uid);
                        if (c){
                            Toast.makeText(context, "تم مسح الرساله ...", Toast.LENGTH_SHORT).show();
                        }
                        removeItem(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();

                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {

                    })
                    .setMessage("هل تريد مسح الرساله ؟");
            builder.show();

            return false;
        });
    }

    @Override
    public int getItemCount() {
        return messageUsers.size();
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
    public static class VoiceSenderHolder extends RecyclerView.ViewHolder{
        AudioWaveView audioWaveView;
        ImageView play;

        public VoiceSenderHolder(@NonNull View itemView) {
            super(itemView);
//            audioWaveView=itemView.findViewById(R.id.seekBarSender);
//            play=itemView.findViewById(R.id.play_voice_sender);
        }
    }
    public static class VoiceReceiverHolder extends RecyclerView.ViewHolder{
        AudioWaveView audioWaveView;
        ImageView play;

        public VoiceReceiverHolder(@NonNull View itemView) {
            super(itemView);
//            audioWaveView=itemView.findViewById(R.id.seekBarRight);
//            play=itemView.findViewById(R.id.play_voice_right);

        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position%2==0){
            return MESSAGE_TYPE_SENDER;
        }else if (position%3==1){
            return MESSAGE_TYPE_RECEIVER_VOICE;
        }else if (position%4==3){
            return MESSAGE_TYPE_RECEIVER;
        }else {
            return MESSAGE_TYPE_SENDER_VOICE;
        }
    }
    public  void removeItem(int position){
        messageUsers.remove(position);
        notifyItemRemoved(position);


    }
}
