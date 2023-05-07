package com.example.projectcollage.fragments;

import android.content.SharedPreferences;
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
import com.example.projectcollage.databinding.FragmentAddDataCourseBinding;
import com.example.projectcollage.model.Classroom;
import com.example.projectcollage.model.Course;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Department;
import com.example.projectcollage.model.Lecturer;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDataCourseFragment extends Fragment {
    FragmentAddDataCourseBinding binding;
    ArrayList<String> departmentArray=new ArrayList<>();
    ArrayList<String> semesterArray=new ArrayList<>();
    ArrayList<String> levelArray=new ArrayList<>();
    ArrayAdapter<String> departmentAdapter;
    ArrayAdapter<String> semesterAdapter;
    ArrayAdapter<String> levelAdapter;
    List<Department> departments;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public AddDataCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentAddDataCourseBinding.inflate(getLayoutInflater());
        preparationSpinner();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     return binding.getRoot();
    }
    public void preparationSpinner(){
        sharedPreferences=getActivity().getSharedPreferences(Constants.DATA,0);
        editor=sharedPreferences.edit();
        allDepartments();

        binding.courseDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                levelArray.clear();
                semesterArray.clear();
                getDepartmentById(departments.get(position).getDid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.send.setOnClickListener(v -> {
            addCourse();

        });
    }
    private void addCourse(){
        String courseName=binding.name.getText().toString();
        String courseCode=binding.code.getText().toString();
        int departmentId=departments.get(binding.courseDepartment.getSelectedItemPosition()).getDid();
        String courseLevel=departments.get(binding.courseDepartment.getSelectedItemPosition()).getLevel();
        String courseSemester=departments.get(binding.courseDepartment.getSelectedItemPosition()).getSemester();
        Course course=new Course(courseName,courseCode,courseLevel,courseSemester,departmentId);
        Call<Data<Course>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addCourse(course);
        call.enqueue(new Callback<Data<Course>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Course>> call, @NonNull Response<Data<Course>> response) {
                if(response.isSuccessful()){
                    binding.name.setText("");
                    binding.code.setText("");
                    editor.putInt("cid",response.body().getData().getCid());
                    Toast.makeText(getActivity(), "تم اضافة المادة بنجاح", Toast.LENGTH_SHORT).show();
                  }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Course>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "حدث خطأ", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void allDepartments(){
        Call<Data<List<Department>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getAllDepartments();
        call.enqueue(new Callback<Data<List<Department>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Department>>> call, @NonNull Response<Data<List<Department>>> response) {
                if(response.isSuccessful()){
                        departments = response.body().getData();
                        for (Department department:departments){
                            departmentArray.add(department.getName());
                        }
                        departmentAdapter=new ArrayAdapter<>(getActivity(),R.layout.item_spinner,departmentArray);
                        departmentAdapter.setDropDownViewResource(R.layout.item_spinner);
                        binding.courseDepartment.setAdapter(departmentAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Department>>> call, @NonNull Throwable t) {

            }
        });
    }
    private void getDepartmentById(int id){
        Call<Data<Department>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getDepartment(id);
        call.enqueue(new Callback<Data<Department>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Department>> call, @NonNull Response<Data<Department>> response) {
                if(response.isSuccessful()){
                    levelArray.add(response.body().getData().getLevel());
                    semesterArray.add(response.body().getData().getSemester());
                    levelAdapter=new ArrayAdapter<>(getActivity(),R.layout.item_spinner,levelArray);
                    levelAdapter.setDropDownViewResource(R.layout.item_spinner);
                    binding.courseLevel.setAdapter(levelAdapter);
                    semesterAdapter=new ArrayAdapter<>(getActivity(),R.layout.item_spinner,semesterArray);
                    semesterAdapter.setDropDownViewResource(R.layout.item_spinner);
                    binding.courseSemester.setAdapter(semesterAdapter);

                    binding.courseLevel.setSelection(levelAdapter.getPosition(response.body().getData().getLevel()));
                    binding.courseSemester.setSelection(semesterAdapter.getPosition(response.body().getData().getSemester()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Department>> call, @NonNull Throwable t) {
                               Toast.makeText(getActivity(), "حدث خطأ", Toast.LENGTH_SHORT).show();
            }
        });
    }



}