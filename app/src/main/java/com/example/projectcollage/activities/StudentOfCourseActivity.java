package com.example.projectcollage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.StudentAdapter;
import com.example.projectcollage.databinding.ActivityStudentOfCourseBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentOfCourseActivity extends AppCompatActivity {
    ActivityStudentOfCourseBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStudentOfCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        Window window=this.getWindow();
        Drawable drawable= AppCompatResources.getDrawable(this,R.drawable.background_gradient);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
        window.setBackgroundDrawable(drawable);
        String nameOfCourse=getIntent().getStringExtra(ViewMessageClassroomActivity.NAME_OF_COURSE);
        binding.courseName.setText(nameOfCourse);
        int departmentId=sharedPreferences.getInt("departmentId", 0);
        getAllStudentInCourse(departmentId);
        binding.back.setOnClickListener(view -> finish());
    }
    private void getAllStudentInCourse(int departmentId){
        Call<Data<List<com.example.projectcollage.model.Student>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getAllStudentByDepartmentId(departmentId);
        call.enqueue(new Callback<Data<List<Student>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Student>>> call, @NonNull Response<Data<List<Student>>> response) {
                if(response.isSuccessful()){
                    List<Student>students=response.body().getData();
                    if(students.size()>0){
                        StudentAdapter adapter=new StudentAdapter((Context) StudentOfCourseActivity.this, (ArrayList<Student>) students);
                        LinearLayoutManager manager=new LinearLayoutManager(StudentOfCourseActivity.this);
                        binding.rvOfAllStudentInClass.setLayoutManager(manager);
                        binding.rvOfAllStudentInClass.setAdapter(adapter);
                        binding.numberOfStudentInCoures.setText(String.format(Locale.getDefault(), "%dعدد الطلاب ", students.size()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Student>>> call, @NonNull Throwable t) {
                Toast.makeText(StudentOfCourseActivity.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();

            }
        });

    }
}