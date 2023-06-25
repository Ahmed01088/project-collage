package com.example.projectcollage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.StudentAdapter;
import com.example.projectcollage.databinding.ActivityStudentOfCourseBinding;
import com.example.projectcollage.model.Chat;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentOfCourseActivity extends AppCompatActivity {
    ActivityStudentOfCourseBinding binding;
    SharedPreferences sharedPreferences;
    int lecturerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStudentOfCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences=getSharedPreferences(Constants.DATA, Context.MODE_PRIVATE);
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        Window window=this.getWindow();
        String lecturerName=getIntent().getStringExtra(Constants.LECTURER_NAME);
        Drawable drawable= AppCompatResources.getDrawable(this,R.drawable.background_gradient);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
        window.setBackgroundDrawable(drawable);
        binding.lecturerName.setText(String.format("الدكتور %s", lecturerName));
        lecturerId=getIntent().getIntExtra(Constants.LECTURER_ID,0);
        String nameOfCourse=getIntent().getStringExtra(Constants.COURSE_NAME);
        binding.courseName.setText(nameOfCourse);
        int departmentId=sharedPreferences.getInt(Constants.DEPARTMENT_ID, 0);
        getAllStudentInCourse(departmentId);
        binding.back.setOnClickListener(view -> finish());
        binding.startchatlecturer.setOnClickListener(view -> {
            Chat chat=new Chat();
            chat.setStudentSenderId(sharedPreferences.getInt(Constants.UID, 0));
            chat.setStudentReciverId(null);
            chat.setLecturerReciverId(lecturerId);
            chat.setLecturerSenderId(null);
            chat.setStudentAffairsReciverId(null);
            chat.setStudentAffairsSenderId(null);
            addChat(chat);
        });
    }
    private void getAllStudentInCourse(int departmentId){
        Call<Data<List<com.example.projectcollage.model.Student>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getAllStudentByDepartmentId(departmentId);
        call.enqueue(new Callback<Data<List<Student>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Student>>> call, @NonNull Response<Data<List<Student>>> response) {
                if(response.isSuccessful()){
                    List<Student>students=response.body().getData();
                    if(students.size()>0){
                        StudentAdapter adapter=new StudentAdapter(StudentOfCourseActivity.this, (ArrayList<Student>) students);
                        LinearLayoutManager manager=new LinearLayoutManager(StudentOfCourseActivity.this);
                        binding.rvOfAllStudentInClass.setLayoutManager(manager);
                        binding.rvOfAllStudentInClass.setAdapter(adapter);
                        binding.numberOfStudentInCoures.setText(String.format(Locale.ENGLISH,"عدد الطلاب %d", students.size()));

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Student>>> call, @NonNull Throwable t) {
                Toast.makeText(StudentOfCourseActivity.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void addChat(Chat chat){
        Call<Data<Chat>>call= RetrofitClientLaravelData.getInstance().getApiInterface().addChat(chat);
        call.enqueue(new retrofit2.Callback<Data<Chat>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Chat>> call, @NonNull retrofit2.Response<Data<Chat>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(StudentOfCourseActivity.this, "ابدا", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(StudentOfCourseActivity.this, ViewMessageUsersActivity.class);
                    intent.putExtra(Constants.CHAT_ID, response.body().getData().getId());
                    intent.putExtra(Constants.FIRSTNAME, sharedPreferences.getString(Constants.FIRSTNAME, ""));
                    intent.putExtra(Constants.UID, sharedPreferences.getInt(Constants.UID, 0));
                    intent.putExtra(Constants.SENDER_ID, sharedPreferences.getInt(Constants.UID, 0));
                    intent.putExtra(Constants.RECEIVER_ID, lecturerId);
                    intent.putExtra(Constants.FULL_NAME, String.format("%s %s", sharedPreferences.getString(Constants.FIRSTNAME, ""), sharedPreferences.getString(Constants.LASTNAME, "")));
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Chat>> call, @NonNull Throwable t) {
                Toast.makeText(StudentOfCourseActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}