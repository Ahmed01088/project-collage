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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.activities.DetailsActivity;
import com.example.projectcollage.activities.ViewMessageUsersActivity;
import com.example.projectcollage.model.Chat;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    Context context;
    ArrayList<Student> students;
    SharedPreferences sharedPreferences;
    Intent intent;
    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
        sharedPreferences=context.getSharedPreferences(Constants.DATA, Context.MODE_PRIVATE);
        intent=new Intent(context, ViewMessageUsersActivity.class);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_student,parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (sharedPreferences.getInt(Constants.UID, 0)==students.get(position).getUid()&&sharedPreferences.getString(Constants.USER_TYPE, "").equals(Constants.USER_TYPES[0])){
            holder.nameOfStudent.setText(String.format("%s %s ( انت )", students.get(position).getfName(), students.get(position).getlName()));
            holder.icStartChat.setVisibility(View.GONE);
        }else {
            holder.nameOfStudent.setText(String.format("%s %s", students.get(position).getfName(), students.get(position).getlName()));

        }
            if (students.get(position).getImage()!=null){
                Picasso.get().load(Constants.BASE_URL_PATH_USERS+students.get(position).getImage()).into(holder.imageOfStudent);
            }else {
               holder.imageOfStudent.setImageResource(R.drawable.avatar);
            }
            holder.icStartChat.setOnClickListener(view -> {
            Chat chat=new Chat();
            if (sharedPreferences.getString(Constants.USER_TYPE, "").equals(Constants.USER_TYPES[0])){
                chat.setStudentSenderId(sharedPreferences.getInt(Constants.UID, 0));
                chat.setStudentReciverId(students.get(position).getUid());
                chat.setLecturerReciverId(null);
                chat.setLecturerSenderId(null);
                chat.setStudentAffairsReciverId(null);
                chat.setStudentAffairsSenderId(null);
            }else if (sharedPreferences.getString(Constants.USER_TYPE, "").equals(Constants.USER_TYPES[1])){
                chat.setStudentSenderId(null);
                chat.setStudentReciverId(students.get(position).getUid());
                chat.setLecturerReciverId(null);
                chat.setLecturerSenderId(sharedPreferences.getInt(Constants.UID, 0));
                chat.setStudentAffairsReciverId(null);
                chat.setStudentAffairsSenderId(null);
            }else if (sharedPreferences.getString(Constants.USER_TYPE, "").equals(Constants.USER_TYPES[2])){
                chat.setStudentSenderId(null);
                chat.setStudentReciverId(students.get(position).getUid());
                chat.setLecturerReciverId(null);
                chat.setLecturerSenderId(null);
                chat.setStudentAffairsReciverId(sharedPreferences.getInt(Constants.UID, 0));
                chat.setStudentAffairsSenderId(null);
            }
            addChat(chat, position);
        });
            holder.itemView.setOnClickListener(v -> {
                Intent intent=new Intent(context, DetailsActivity.class);
                ActivityOptions options= ActivityOptions.makeClipRevealAnimation(holder.itemView,holder.itemView.getWidth()/2,holder.itemView.getHeight()/2,300,300);
                intent.putExtra(Constants.IMAGE,Constants.BASE_URL_PATH_USERS+students.get(position).getImage());
                context.startActivity(intent,options.toBundle());
            });

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfStudent;
        ImageView icStartChat, imageOfStudent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfStudent=itemView.findViewById(R.id.firstname);
            icStartChat=itemView.findViewById(R.id.ic_startChat);
            imageOfStudent=itemView.findViewById(R.id.studentPicProfile);
        }
    }
    private void addChat(Chat chat, int position){
        Call<Data<Chat>>call= RetrofitClientLaravelData.getInstance().getApiInterface().addChat(chat);
        call.enqueue(new retrofit2.Callback<Data<Chat>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Chat>> call, @NonNull retrofit2.Response<Data<Chat>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "ابدا", Toast.LENGTH_SHORT).show();
                    intent.putExtra(Constants.CHAT_ID, response.body().getData().getId());
                    intent.putExtra(Constants.FIRSTNAME, students.get(position).getfName());
                    intent.putExtra(Constants.UID, students.get(position).getUid());
                    intent.putExtra(Constants.SENDER_ID, sharedPreferences.getInt(Constants.UID, 0));
                    intent.putExtra(Constants.RECEIVER_ID, chat.getStudentReciverId());
                    intent.putExtra(Constants.FULL_NAME, String.format("%s %s", students.get(position).getfName(), students.get(position).getlName()));
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Chat>> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
