package com.example.projectcollage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.StudentAdapter;
import com.example.projectcollage.databinding.ActivityStudentOfCourseBinding;
import com.example.projectcollage.models.Student;

import java.util.ArrayList;
import java.util.Locale;

public class StudentOfCourseActivity extends AppCompatActivity {
    ActivityStudentOfCourseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStudentOfCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getResources().getColor(R.color.main_bar));
        Window window=this.getWindow();
        Drawable drawable=getDrawable(R.drawable.background_gradient);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
        window.setBackgroundDrawable(drawable);
        String nameOfCourse=getIntent().getStringExtra(ViewMessageClassroomActivity.NAME_OF_COURSE);
        ArrayList<Student>students=new ArrayList<>();
        students.add(new Student("user1","احمد ابراهيم"));
        students.add(new Student("user2","محمد علي "));
        students.add(new Student("user3","محمد سعيد"));
        students.add(new Student("user4","عمرو دياب"));
        students.add(new Student("user5","محمد حماقي "));
        students.add(new Student("user6","احمد الشيخ"));
        students.add(new Student("user7","احمد حلمي"));
        students.add(new Student("user8","محمد ابو تريكا"));
        students.add(new Student("user9","احمد كامل "));
        students.add(new Student("user10","جوكر"));
        binding.courseName.setText(nameOfCourse);
        StudentAdapter adapter=new StudentAdapter(this, students);
        binding.numberOfStudentInCoures.setText(String.format(Locale.ENGLISH,"عدد الطلاب : %d",adapter.getItemCount()));
        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.rvOfAllStudentInClass.setAdapter(adapter);
        binding.rvOfAllStudentInClass.setLayoutManager(manager);
        binding.back.setOnClickListener(view -> finish());
    }
}