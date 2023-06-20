package com.example.projectcollage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.projectcollage.adapters.DataAdapter;
import com.example.projectcollage.databinding.ActivityShowAnyDataStudentAffirsAddedBinding;
import com.example.projectcollage.model.Course;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Department;
import com.example.projectcollage.model.Lecturer;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ShowAnyDataStudentAffairsAddedActivity extends AppCompatActivity {
    ActivityShowAnyDataStudentAffirsAddedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowAnyDataStudentAffirsAddedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getAllStudent();
        binding.btnstudent.setOnClickListener(v -> {
            getAllStudent();
        });
        binding.btnlecturer.setOnClickListener(v -> {
            getAllLecturer();
        });
        binding.btncourse.setOnClickListener(v -> {
            getAllCourses();
        });
        binding.btndepartment.setOnClickListener(v -> {
            getAllDepartments();
        });


    }
    private void getAllStudent(){
        Call<Data<List<Student>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getAllStudent();
        call.enqueue(new retrofit2.Callback<Data<List<Student>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Student>>> call, @NonNull retrofit2.Response<Data<List<Student>>> response) {
                if (response.isSuccessful()){
                    Data<List<Student>> data=response.body();
                        List<Student> studentList=data.getData();
                        binding.rvShowAnyData.setAdapter(new DataAdapter<>(ShowAnyDataStudentAffairsAddedActivity.this, (ArrayList<Student>) studentList,new Student()));
                        binding.rvShowAnyData.setLayoutManager(new LinearLayoutManager(ShowAnyDataStudentAffairsAddedActivity.this));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Student>>> call, @NonNull Throwable t) {

            }
        });

    }
    private void getAllLecturer(){
        Call<Data<List<Lecturer>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getAllLecturer();
        call.enqueue(new retrofit2.Callback<Data<List<Lecturer>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Lecturer>>> call, @NonNull retrofit2.Response<Data<List<Lecturer>>> response) {
                if (response.isSuccessful()){
                    Data<List<Lecturer>> data=response.body();
                        List<Lecturer> lecturerList=data.getData();
                        binding.rvShowAnyData.setAdapter(new DataAdapter<>(ShowAnyDataStudentAffairsAddedActivity.this, (ArrayList<Lecturer>) lecturerList,new Lecturer()));
                        binding.rvShowAnyData.setLayoutManager(new LinearLayoutManager(ShowAnyDataStudentAffairsAddedActivity.this));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Lecturer>>> call, @NonNull Throwable t) {

            }
        });
    }
    private void getAllCourses(){
        Call<Data<List<Course>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getAllCourses();
        call.enqueue(new retrofit2.Callback<Data<List<Course>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Course>>> call, @NonNull retrofit2.Response<Data<List<Course>>> response) {
                if (response.isSuccessful()){
                    Data<List<Course>> data=response.body();
                        List<Course> courseList=data.getData();
                        binding.rvShowAnyData.setAdapter(new DataAdapter<>(ShowAnyDataStudentAffairsAddedActivity.this, (ArrayList<Course>) courseList,new Course()) );
                        binding.rvShowAnyData.setLayoutManager(new LinearLayoutManager(ShowAnyDataStudentAffairsAddedActivity.this));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Course>>> call, @NonNull Throwable t) {

            }
        });

    }
    private void getAllDepartments() {
        Call<Data<List<Department>>> call = RetrofitClientLaravelData.getInstance().getApiInterface().getAllDepartments();
        call.enqueue(new retrofit2.Callback<Data<List<Department>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Department>>> call, @NonNull retrofit2.Response<Data<List<Department>>> response) {
                if (response.isSuccessful()) {
                    Data<List<Department>> data = response.body();
                    List<Department> departmentList = data.getData();
                    binding.rvShowAnyData.setAdapter(new DataAdapter<>(ShowAnyDataStudentAffairsAddedActivity.this, (ArrayList<Department>) departmentList,new Department()));
                    binding.rvShowAnyData.setLayoutManager(new LinearLayoutManager(ShowAnyDataStudentAffairsAddedActivity.this));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Department>>> call, @NonNull Throwable t) {

            }
        });
    }


}