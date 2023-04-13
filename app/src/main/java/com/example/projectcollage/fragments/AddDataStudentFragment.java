package com.example.projectcollage.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.FragmentAddDataStudentBinding;
import com.example.projectcollage.model.Course;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Department;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDataStudentFragment extends Fragment {
    FragmentAddDataStudentBinding binding;
    String[] studentLevels={"الربعة","التالتة ","التانية","الاولي "};
    String[] studentState={"باقي","مستجد"};
    String[] studentDepartment={"Computer Science","Biology","Chemistry","Math","Physics","Math&Physics","Chemistry&Physics"};
    ArrayList<String> departmentArray=new ArrayList<>();
    ArrayAdapter<String> adapterDepartments;
    List<Department> departments;
    List<Course> courses;

    public AddDataStudentFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentAddDataStudentBinding.inflate(getLayoutInflater());
        preparationSpinner();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
    public void preparationSpinner(){
        ArrayAdapter<String>adapterLevels=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,studentLevels);
       ArrayAdapter<String>adapterState=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,studentState);
        adapterState.setDropDownViewResource(R.layout.item_spinner);
        adapterLevels.setDropDownViewResource(R.layout.item_spinner);
        binding.studentState.setAdapter(adapterState);
        binding.level.setAdapter(adapterLevels);
        getAllDepartments();
        binding.send.setOnClickListener(v -> {
            String firstname=binding.firstname.getText().toString();
            String lastname=binding.lastName.getText().toString();
            String email=binding.email.getText().toString();
            String password=binding.password.getText().toString();
            String nationalId=binding.nationalIdA.getText().toString();
            String level=binding.level.getSelectedItem().toString();
            String department=binding.studentDepartment.getSelectedItem().toString();
            String state=binding.studentState.getSelectedItem().toString();
            String phoneNumber=binding.phoneNumber.getText().toString();
            int departmentId=departments.get(binding.studentDepartment.getSelectedItemPosition()).getDid();
            Student student=new Student(firstname,lastname,email,password,nationalId,level,department,state,phoneNumber,departmentId);
            addStudent(student);
            binding.firstname.setText("");
            binding.email.setText("");
            binding.password.setText("");
            binding.nationalIdA.setText("");
            binding.lastName.setText("");
            binding.phoneNumber.setText("");

        });
    }

   private void addStudent(Student student){
        Call<Data<Student>>call= RetrofitClientLaravelData.getInstance().getApiInterface().addStudent(student);
        call.enqueue(new Callback<Data<Student>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Student>> call, @NonNull Response<Data<Student>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), "تم اضافة الطالب بنجاح", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Student>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "حدث خطأ", Toast.LENGTH_SHORT).show();
            }
        });
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
                    binding.studentDepartment.setAdapter(adapterDepartments);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Department>>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCoursesByDepartmentId(int id){
        Call<Data<List<Course>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getCourseByDepartmentId(id);
        call.enqueue(new Callback<Data<List<Course>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Course>>> call, @NonNull Response<Data<List<Course>>> response) {
               if (response.isSuccessful()){
                   courses=response.body().getData();
                   int []cIds=new int[courses.size()];
                     for (int i=0;i<courses.size();i++){
                          cIds[i]=courses.get(i).getCid();
                     }

               }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Course>>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}