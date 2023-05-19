package com.example.projectcollage.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.FragmentAddDataLecturerBinding;
import com.example.projectcollage.model.Chat;
import com.example.projectcollage.model.Classroom;
import com.example.projectcollage.model.Course;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Department;
import com.example.projectcollage.model.Lecturer;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDataLecturerFragment extends Fragment {
    FragmentAddDataLecturerBinding binding;
    List<Department> departments;
    List<Course> courses;
    ArrayList<String> departmentArray=new ArrayList<>();
    ArrayAdapter<String> adapterDepartments;

    ArrayList<String> courseArray=new ArrayList<>();
    ArrayAdapter<String> adapterCourses;
    int departmentId;

    public AddDataLecturerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preparationSpinner();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
    public void preparationSpinner(){
        binding=FragmentAddDataLecturerBinding.inflate(getLayoutInflater());
        getAllDepartments();
        binding.department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departmentId=departments.get(position).getDid();
                courseArray.clear();
                getCourseByDepartmentId(departmentId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        binding.send.setOnClickListener(v -> addLecturer());
    }
    private void addLecturer(){
        String firstName=binding.firstname.getText().toString();
        String lastName=binding.lastName.getText().toString();
        String email=binding.email.getText().toString();
        String password=binding.password.getText().toString();
        String phoneNumber=binding.phoneNumber.getText().toString();
        String nationalId="2060"+binding.nationalId.getText().toString();
        departmentId=departments.get(binding.department.getSelectedItemPosition()).getDid();
        int courseId=courses.get(binding.course.getSelectedItemPosition()).getCid();
        Lecturer lecturer=new Lecturer(firstName,lastName,email,password,phoneNumber,nationalId,departmentId,courseId);
        Call<Data<Lecturer>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addLecturer(lecturer);
        call.enqueue(new Callback<Data<Lecturer>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Lecturer>> call, @NonNull Response<Data<Lecturer>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "تم اضافة المدرس بنجاح", Toast.LENGTH_SHORT).show();
                    addClassroom(new Classroom(response.body().getData().getCourseId(),response.body().getData().getLid()));
                    Chat chat=new Chat();
                    chat.setLecturerSenderId(response.body().getData().getLid());
                    addChat(chat);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<Lecturer>> call, @NonNull Throwable t) {

            }
        });
        binding.firstname.setText("");
        binding.lastName.setText("");
        binding.email.setText("");
        binding.password.setText("");
        binding.phoneNumber.setText("");
        binding.nationalId.setText("");

    }
    private void getAllDepartments(){
        Call<Data<List<Department>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getAllDepartments();
        call.enqueue(new Callback<Data<List<Department>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Department>>> call, @NonNull Response<Data<List<Department>>> response) {
                if (response.isSuccessful()){
                    departments=response.body().getData();
                    for (Department department:departments){
                        departmentArray.add(department.getName());
                    }
                    adapterDepartments =new ArrayAdapter<>(getContext(),R.layout.item_spinner, departmentArray);
                    binding.department.setAdapter(adapterDepartments);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Department>>> call, @NonNull Throwable t) {
                  Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getCourseByDepartmentId(int departmentId){
        Call<Data<List<Course>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getCourseByDepartmentId(departmentId);
        call.enqueue(new Callback<Data<List<Course>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Course>>> call, @NonNull Response<Data<List<Course>>> response) {
                if (response.isSuccessful()){
                    courses=response.body().getData();
                    for (Course course:courses){
                        courseArray.add(course.getName());
                    }
                    adapterCourses =new ArrayAdapter<>(getContext(),R.layout.item_spinner, courseArray);
                    binding.course.setAdapter(adapterCourses);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<List<Course>>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addClassroom(Classroom classroom){
        Call<Data<Classroom>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addClassroom(classroom);
        call.enqueue(new Callback<Data<Classroom>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Classroom>> call, @NonNull Response<Data<Classroom>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Classroom>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "حدث خطأ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addChat(Chat chat){
        Call<Data<Chat>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addChat(chat);
        call.enqueue(new Callback<Data<Chat>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Chat>> call, @NonNull Response<Data<Chat>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Chat>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "حدث خطأ", Toast.LENGTH_SHORT).show();

            }
        });
    }

}