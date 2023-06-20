package com.example.projectcollage.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.activities.LoginActivity;
import com.example.projectcollage.adapters.ClassroomAdapter;
import com.example.projectcollage.databinding.FragmentClassroomBinding;
import com.example.projectcollage.model.Classroom;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.models.Course;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassroomFragment extends Fragment {
    FragmentClassroomBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ClassroomFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentClassroomBinding.inflate(getLayoutInflater());
        sharedPreferences=getActivity().getSharedPreferences(Constants.DATA, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        int departmentId=sharedPreferences.getInt(Constants.DEPARTMENT_ID, 0);
        int uid=sharedPreferences.getInt(Constants.UID, 0);
        if (sharedPreferences.getString(Constants.USER_TYPE, "").equals(Constants.USER_TYPES[1])){
            getClassroomByLecturerId(uid);
        }
        else if (sharedPreferences.getString(Constants.USER_TYPE, "").equals(Constants.USER_TYPES[0])) {
            getClassroomsByDepartmentId(departmentId);
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
    private void getClassroomsByDepartmentId(int departmentId){
        Call<Data<List<Classroom>>>call= RetrofitClientLaravelData.getInstance().getApiInterface().getClassroomsByDepartmentId(departmentId);
        call.enqueue(new Callback<Data<List<Classroom>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Classroom>>> call, @NonNull Response<Data<List<Classroom>>> response) {
                if(response.isSuccessful()){
                        List<Classroom>classrooms=response.body().getData();
                        if(classrooms.size()>0){
                            binding.rvClassroom.setVisibility(View.VISIBLE);
                            LinearLayoutManager manager=new LinearLayoutManager(binding.getRoot().getContext());
                            ClassroomAdapter adapter=new ClassroomAdapter(binding.getRoot().getContext(), (ArrayList<Classroom>) classrooms);
                            binding.rvClassroom.setAdapter(adapter);
                            binding.rvClassroom.setLayoutManager(manager);
                            editor.apply();
                        }else{
                            binding.rvClassroom.setVisibility(View.GONE);
                        }
                    }else{
                        Toast.makeText(binding.getRoot().getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                  }
            @Override
            public void onFailure(@NonNull Call<Data<List<Classroom>>> call, @NonNull Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getClassroomByLecturerId(int lecturerId){
        Call<Data<List<Classroom>>>call= RetrofitClientLaravelData.getInstance().getApiInterface().getClassroomByLecturerId(lecturerId);
        call.enqueue(new Callback<Data<List<Classroom>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Classroom>>> call, @NonNull Response<Data<List<Classroom>>> response) {
                if(response.isSuccessful()){
                    List<Classroom>classrooms=response.body().getData();
                    if(classrooms.size()>0){
                        binding.rvClassroom.setVisibility(View.VISIBLE);
                        LinearLayoutManager manager=new LinearLayoutManager(binding.getRoot().getContext());
                        ClassroomAdapter adapter=new ClassroomAdapter(binding.getRoot().getContext(), (ArrayList<Classroom>) classrooms);
                        binding.rvClassroom.setAdapter(adapter);
                        binding.rvClassroom.setLayoutManager(manager);
                    }else{
                        binding.rvClassroom.setVisibility(View.GONE);
                    }
                }else{
                    Toast.makeText(binding.getRoot().getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<List<Classroom>>> call, @NonNull Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPreferences.getString(Constants.USER_TYPE, "").equals(Constants.USER_TYPES[1])){
            getClassroomByLecturerId(sharedPreferences.getInt(Constants.UID, 0));
        }
        else if (sharedPreferences.getString(Constants.USER_TYPE, "").equals(Constants.USER_TYPES[0])) {
            getClassroomsByDepartmentId(sharedPreferences.getInt(Constants.DEPARTMENT_ID, 0));
        }
    }
}