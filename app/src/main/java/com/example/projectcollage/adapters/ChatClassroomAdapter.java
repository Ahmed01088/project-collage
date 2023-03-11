package com.example.projectcollage.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectcollage.R;
import com.example.projectcollage.activities.LiveStreamActivity;
import com.example.projectcollage.activities.ShowImageActivity;
import com.example.projectcollage.database.Database;
import com.example.projectcollage.models.Message;

import java.util.ArrayList;

import rm.com.audiowave.AudioWaveView;

public class ChatClassroomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int MESSAGE_TYPE_SENDER=0;
    public static final int MESSAGE_TYPE_SENDER_VOICE=1;
    public static final int MESSAGE_TYPE_RECEIVER =2;
    public static final int MESSAGE_TYPE_RECEIVER_VOICE =3;
    MediaPlayer player;

    private Context context;
    private ArrayList<Message>chatsMessage;
    private Database database;
    private final String tableName;

    public ChatClassroomAdapter(Context context, ArrayList<Message> chatsMessage, String tableName) {
        this.context = context;
        this.chatsMessage = chatsMessage;
        this.tableName=tableName;
        player=MediaPlayer.create(context,R.raw.audio);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==MESSAGE_TYPE_SENDER){
            view= LayoutInflater.from(context).inflate(R.layout.sender_item_message_classroom,parent,false);
            return new SenderHolder(view);
        }else if (viewType==MESSAGE_TYPE_RECEIVER){
            view= LayoutInflater.from(context).inflate(R.layout.reciver_item_message_classroom,parent,false);
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
            senderHolder.message.setText(chatsMessage.get(position).getMessage());
            senderHolder.date.setText(chatsMessage.get(position).getTime());
            senderHolder.image.setOnClickListener(view -> {
                    Intent intent=new Intent(context, ShowImageActivity.class);
                    ShowImageActivity.DATA=chatsMessage.get(position).getMessageImage();
                    ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,
                            view.getWidth()/2,view.getHeight()/2,100,100);
                    context.startActivity(intent,options.toBundle());
            });
            if (!(chatsMessage.get(position).getMessageImage()==null)) {
                senderHolder.image.setImageURI(chatsMessage.get(position).getImageUri());
                senderHolder.image.setVisibility(View.VISIBLE);
                senderHolder.image.setImageBitmap(chatsMessage.get(position).getMessageImage());

            }else {
                senderHolder.image.setVisibility(View.GONE);
            }
            senderHolder.itemView.setOnClickListener(view -> {
                int startId=senderHolder.message.getText().toString().indexOf("=");
                if (startId>8) {
                    String id = senderHolder.message.getText().toString().substring(startId + 1, startId + 8);
                    if (id.equals("2062001")) {
                        context.startActivity(new Intent(context, LiveStreamActivity.class));
                    }
                }
            });
        }else if (holder.getClass()== VoiceReceiverHolder.class){
            VoiceReceiverHolder voiceReceiverHolder=(VoiceReceiverHolder) holder;
            voiceReceiverHolder.position.setText(""+player.getCurrentPosition()/(1000*60));
            voiceReceiverHolder.duration.setText(""+player.getDuration()/(1000*60));
            voiceReceiverHolder.play.setOnClickListener(view -> {
            if (player.isPlaying()){
                    player.pause();
                    voiceReceiverHolder.play.setImageResource(R.drawable.ic_play);

                }else {
                    player.start();
                    voiceReceiverHolder.play.setImageResource(R.drawable.ic_pause);
                    updateProgress(voiceReceiverHolder.audioWaveView);
                }
            });
        }else if (holder.getClass()== VoiceSenderHolder.class){
            VoiceSenderHolder voiceSenderHolder=(VoiceSenderHolder) holder;
            voiceSenderHolder.play.setOnClickListener(view -> {
                voiceSenderHolder.duration.setText(""+player.getDuration()/(1000*60));
                voiceSenderHolder.position.setText(""+player.getCurrentPosition()/(1000*60));    if (player.isPlaying()){
                    voiceSenderHolder.play.setImageResource(R.drawable.ic_play);
                    player.pause();
                }else {
                    player.start();
                    voiceSenderHolder.play.setImageResource(R.drawable.ic_pause);
                    updateProgress(voiceSenderHolder.audioWaveView);
                }
            });
        }else {
            ReceiverHolder receiverHolder= (ReceiverHolder) holder;
            receiverHolder.message.setText(chatsMessage.get(position).getMessage());
            receiverHolder.date.setText(chatsMessage.get(position).getTime());
            receiverHolder.image.setOnClickListener(view -> {
                Intent intent=new Intent(context, ShowImageActivity.class);
                ShowImageActivity.DATA=chatsMessage.get(position).getMessageImage();
                ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,
                        view.getWidth()/2,view.getHeight()/2,100,100);
                context.startActivity(intent,options.toBundle());
            });
            if (!(chatsMessage.get(position).getMessageImage()==null)) {
                receiverHolder.image.setImageURI(chatsMessage.get(position).getImageUri());
                receiverHolder.image.setVisibility(View.VISIBLE);
                receiverHolder.image.setImageBitmap(chatsMessage.get(position).getMessageImage());
            }else {
                receiverHolder.image.setVisibility(View.GONE);
            }
            receiverHolder.itemView.setOnClickListener(view -> {
                int startId=receiverHolder.message.getText().toString().indexOf("=");
                if (startId>8) {
                    String id = receiverHolder.message.getText().toString().substring(startId + 1, startId + 8);
                    if (id.equals("2062001")) {
                        context.startActivity(new Intent(context, LiveStreamActivity.class));
                    }
                }
            });
        }
        holder.itemView.setOnLongClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(context,R.style.AlertDialogStyle)
                    .setPositiveButton("مسح", (dialogInterface, i) -> {
                            boolean c=database.deleteMessage(chatsMessage.get(position).getId(),tableName);
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

    private void updateProgress(AppCompatSeekBar audio) {
        new Handler().postDelayed(() -> {
            int progress=(int) ((float)player.getCurrentPosition()/player.getDuration()*100);
            audio.setProgress(progress);
            if (player.isPlaying()){
                updateProgress(audio);
            }
        }, 100);
    }

    @Override
    public int getItemCount() {
        return chatsMessage.size();
    }

    public static class SenderHolder extends RecyclerView.ViewHolder{
        TextView message,date;
        ImageView image;
        public SenderHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.senderItemMessage);
            date=itemView.findViewById(R.id.date_item_sender);
            image=itemView.findViewById(R.id.image_classroom_sender);


        }
    }
    public static class ReceiverHolder extends RecyclerView.ViewHolder{
        TextView message,date;
        ImageView image;
        public ReceiverHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.reciverItemMessage);
            date=itemView.findViewById(R.id.date_item_reciver);
            image=itemView.findViewById(R.id.image_classroom_reciver);

        }
    }
    public static class VoiceSenderHolder extends RecyclerView.ViewHolder{
        AppCompatSeekBar audioWaveView;
        ImageView play;
        TextView position,duration;
        public VoiceSenderHolder(@NonNull View itemView) {
            super(itemView);
            audioWaveView=itemView.findViewById(R.id.seekBarSender);
            play=itemView.findViewById(R.id.play_voice_sender);
            position=itemView.findViewById(R.id.current_position_sender);
            duration=itemView.findViewById(R.id.duration_sender);
        }
    }
    public static class VoiceReceiverHolder extends RecyclerView.ViewHolder{
        AppCompatSeekBar audioWaveView;
        ImageView play;
        TextView position,duration;

        public VoiceReceiverHolder(@NonNull View itemView) {
            super(itemView);
            audioWaveView=itemView.findViewById(R.id.seekBarReciver);
            play=itemView.findViewById(R.id.play_voice_reciver);
            position=itemView.findViewById(R.id.current_position_reciver);
            duration=itemView.findViewById(R.id.duration_reciver);

        }
    }    @Override
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
        chatsMessage.remove(position);
        notifyItemRemoved(position);


    }

}
