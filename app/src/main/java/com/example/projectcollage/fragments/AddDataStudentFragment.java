package com.example.projectcollage.fragments;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.databinding.FragmentAddDataStudentBinding;
import com.example.projectcollage.model.Course;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Department;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Part;

public class AddDataStudentFragment extends Fragment {
    FragmentAddDataStudentBinding binding;
    ArrayList<String> departmentArray=new ArrayList<>();
    ArrayList<String> levelArray=new ArrayList<>();
    ArrayAdapter<String> adapterDepartments;
    ArrayAdapter<String> adapterLevels;
    List<Department> departments;

    List<Course> courses;

    private Uri uriImage;
    private Bitmap bitmap;
    private ProgressBar progressBar;
    private File file;

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
           ArrayAdapter<String>adapterState=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,Constants.STUDENT_STATUS);
        adapterState.setDropDownViewResource(R.layout.item_spinner);
        binding.studentState.setAdapter(adapterState);
        getAllDepartments();
        binding.selectImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);
        });
        binding.studentDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                levelArray.clear();
                getDepartmentById(departments.get(position).getDid());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            if (uriImage!=null){
                createStudent(student,file);
            }else {
                addStudent(student);
            }
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
                    binding.firstname.setText("");
                    binding.email.setText("");
                    binding.password.setText("");
                    binding.nationalIdA.setText("");
                    binding.lastName.setText("");
                    binding.phoneNumber.setText("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Student>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "حدث خطأ", Toast.LENGTH_SHORT).show();
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
                    adapterLevels=new ArrayAdapter<>(getActivity(), R.layout.item_spinner,levelArray);
                    adapterLevels.setDropDownViewResource(R.layout.item_spinner);
                    binding.level.setAdapter(adapterLevels);


                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Department>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "حدث خطأ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createStudent(Student student, File file){
            RequestBody firstname=RequestBody.create(student.getfName(), MediaType.parse("text/plain"));
            RequestBody lastname=RequestBody.create(student.getlName(), MediaType.parse("text/plain"));
            RequestBody nationalId=RequestBody.create(student.getNationalId(), MediaType.parse("text/plain"));
            RequestBody email=RequestBody.create(student.getEmail(), MediaType.parse("text/plain"));
            RequestBody phoneNumber=RequestBody.create(student.getPhoneNumber(), MediaType.parse("text/plain"));
            RequestBody password=RequestBody.create(student.getPassword(), MediaType.parse("text/plain"));
            RequestBody level=RequestBody.create(student.getLevel(), MediaType.parse("text/plain"));
            RequestBody state=RequestBody.create(student.getState(), MediaType.parse("text/plain"));
            RequestBody departmentCode=RequestBody.create(student.getDepartmentCode(), MediaType.parse("text/plain"));
            RequestBody imageBody = RequestBody.create(file,MediaType.parse("image/*"));
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), imageBody);
       Call<Data<Student>> call = RetrofitClientLaravelData.getInstance().getApiInterface().createStudent(
                firstname,
                lastname,
                nationalId,
                email,
                phoneNumber,
                password,
                level,
                state,
                departmentCode,
                student.getDepartmentId(),
                imagePart
         );
       call.enqueue(new Callback<Data<Student>>() {
           @Override
           public void onResponse(@NonNull Call<Data<Student>> call, @NonNull Response<Data<Student>> response) {
                if (response.isSuccessful()){
                     Toast.makeText(getActivity(), "تم اضافة الطالب بنجاح 1", Toast.LENGTH_SHORT).show();
                     Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        binding.firstname.setText("");
                        binding.email.setText("");
                        binding.password.setText("");
                        binding.nationalIdA.setText("");
                        binding.lastName.setText("");
                        binding.phoneNumber.setText("");

                }else {
                    binding.firstname.setText(response.errorBody().toString());
                    Toast.makeText(getActivity(), "تم اضافة الطالب بنجاح 2", Toast.LENGTH_SHORT).show();
                }
           }

           @Override
           public void onFailure(@NonNull Call<Data<Student>> call, @NonNull Throwable t) {
               binding.firstname.setText(t.getMessage());


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

    ActivityResultLauncher<Intent> someActivityResultLauncher
            = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (!(data == null)) {
                            uriImage = data.getData();
                            try {
                                if (uriImage != null) {
                                    binding.selectImage.setImageURI(uriImage);
                                    file = new File(getRealPathFromURI(uriImage));
                                }
                                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uriImage));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }

}