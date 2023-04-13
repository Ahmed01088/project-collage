package com.example.projectcollage.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDataCourseFragment extends Fragment {
    FragmentAddDataCourseBinding binding;
    String[] studentLevels={"الربعة","التالتة ","التانية","الاولي "};
    String []course_class={"التاني","الاول"};
    ArrayList<String> departmentArray=new ArrayList<>();
    ArrayAdapter<String> adapter;
    List<Department> departments;
    private Course courseRetrieved;
    private Lecturer lecturerRetrieved;
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
        ArrayAdapter<String> adapterLevels=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,studentLevels);
        ArrayAdapter<String>adapterClass=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,course_class);
        adapterLevels.setDropDownViewResource(R.layout.item_spinner);
        adapterClass.setDropDownViewResource(R.layout.item_spinner);
        binding.courseLevel.setAdapter(adapterLevels);
        binding.courseSemester.setAdapter(adapterClass);
        sharedPreferences=getActivity().getSharedPreferences("user",0);
        editor=sharedPreferences.edit();
        allDepartments();
        binding.send.setOnClickListener(v -> {
            addCourse();

        });
    }
    private void addCourse(){
        String courseName=binding.name.getText().toString();
        String courseCode=binding.code.getText().toString();
        String courseLevel=binding.courseLevel.getSelectedItem().toString();
        String courseSemester=binding.courseSemester.getSelectedItem().toString();
        int departmentId=departments.get(binding.courseDepartment.getSelectedItemPosition()).getDid();
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
                        adapter=new ArrayAdapter<>(getActivity(),R.layout.item_spinner,departmentArray);
                        adapter.setDropDownViewResource(R.layout.item_spinner);
                        binding.courseDepartment.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Department>>> call, @NonNull Throwable t) {

            }
        });
    }



}