package com.example.projectcollage.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.AddAdminAdapter;
import com.example.projectcollage.databinding.ActivityAddAdminStudentAffairsBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.StudentAffairs;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAdminStudentAffairsActivity extends AppCompatActivity {

    ActivityAddAdminStudentAffairsBinding binding;
    AddAdminAdapter adapter;
    String[] responsibleLevel ={"الربعة","التالتة ","التانية","الاولي "};
    List<StudentAffairs> studentAffairsList;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddAdminStudentAffairsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        preferences=getSharedPreferences("login",MODE_PRIVATE);
        binding.responsibleLevel.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner,responsibleLevel));
        binding.logout.setOnClickListener(view -> startActivity(new Intent(this,LoginActivity.class)));
        binding.addAdmin.setOnClickListener(v -> {
            organizeData();
            });
        allAdmins();
        binding.logout.setOnClickListener(v -> {
            SharedPreferences.Editor editor=preferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(this,LoginActivity.class));
        });
    }
    private void addStudentAffairsAdmin(StudentAffairs studentAffairs) {
        Call<Data<StudentAffairs>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addStudentAffairs(studentAffairs);
        call.enqueue(new Callback<Data<StudentAffairs>>() {
            @Override
            public void onResponse(@NonNull Call<Data<StudentAffairs>> call, @NonNull Response<Data<StudentAffairs>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AddAdminStudentAffairsActivity.this, "تم اضافة الادمن بنجاح", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<StudentAffairs>> call, @NonNull Throwable t) {
                Toast.makeText(AddAdminStudentAffairsActivity.this, "حدث خطأ", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void allAdmins() {
        Call<Data<List<StudentAffairs>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getAllStudentAffairs();
        call.enqueue(new Callback<Data<List<StudentAffairs>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<StudentAffairs>>> call, @NonNull Response<Data<List<StudentAffairs>>> response) {
                if (response.isSuccessful()){
                    studentAffairsList=response.body().getData();
                    binding.rvStudentAffairsAdmin.setLayoutManager(new LinearLayoutManager(AddAdminStudentAffairsActivity.this));
                    adapter=new AddAdminAdapter(AddAdminStudentAffairsActivity.this,studentAffairsList);
                    binding.rvStudentAffairsAdmin.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<List<StudentAffairs>>> call, @NonNull Throwable t) {
                Toast.makeText(AddAdminStudentAffairsActivity.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void organizeData(){
        String firstname=binding.firstname.getText().toString();
        String lastname=binding.lastName.getText().toString();
        String nationalId=binding.nationalIdA.getText().toString();
        String email=binding.email.getText().toString();
        String phoneNumber=binding.phoneNumber.getText().toString();
        String image="https://www.google.com";
        int adminId=preferences.getInt("uid",0);
        String password=binding.password.getText().toString();
        String level=binding.responsibleLevel.getSelectedItem().toString();
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String dateAdded = sdf.format(currentDate);
        boolean validateEmail = email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$\n");
        boolean validatePhone = phoneNumber.matches("^(?:\\+20|0)[17][0125][\\d]{7}$\n");
        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!validateEmail) {
                    binding.email.setError("البريد الالكتروني غير صحيح");
                }
            }
        });
        binding.phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!validatePhone) {
                    binding.phoneNumber.setError("رقم الهاتف غير صحيح");
                }
            }
        });
        if (firstname.isEmpty()
                ||lastname.isEmpty()
                ||nationalId.isEmpty()
                ||email.isEmpty()
                ||phoneNumber.isEmpty()||password.isEmpty()||level.isEmpty()||dateAdded.isEmpty()){
            Toast.makeText(this, "يجب ملئ جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        StudentAffairs studentAffairs=new StudentAffairs(firstname,lastname,nationalId,email,phoneNumber,image,adminId,password,level,dateAdded);
        addStudentAffairsAdmin(studentAffairs);
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
        binding.firstname.setText("");
        binding.lastName.setText("");
        binding.nationalIdA.setText("");
        binding.email.setText("");
        binding.phoneNumber.setText("");
        binding.password.setText("");

    }
}