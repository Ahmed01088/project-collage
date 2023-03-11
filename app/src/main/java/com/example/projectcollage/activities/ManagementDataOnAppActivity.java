package com.example.projectcollage.activities;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityMamagementDataOnAppBinding;
import com.example.projectcollage.fragments.AddDataCourseFragment;
import com.example.projectcollage.fragments.AddDataDepartmentFragment;
import com.example.projectcollage.fragments.AddDataLecturerFragment;
import com.example.projectcollage.fragments.AddDataStudentFragment;

public class ManagementDataOnAppActivity extends AppCompatActivity {
    ActivityMamagementDataOnAppBinding binding;
    String[] spinnerElements={"بيانات دكتور" ,"بيانات طالب","بيانات قسم","بيانات كورس"};
    SharedPreferences login;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMamagementDataOnAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, R.layout.item_spinner_main,spinnerElements);
        adapter.setDropDownViewResource(R.layout.item_spinner_main);
        binding.spinnerData.setAdapter(adapter);
        binding.logout.setOnClickListener(view -> {
            finish();
            login=getSharedPreferences("login", MODE_PRIVATE);
            editor = login.edit();
            editor.clear();
            editor.commit();
        });
        binding.spinnerData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                if (binding.spinnerData.getSelectedItem().toString().equals("بيانات طالب")){
                    transaction.replace(R.id.container, new AddDataStudentFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else if (binding.spinnerData.getSelectedItem().toString().equals("بيانات دكتور")){
                    transaction.replace(R.id.container, new AddDataLecturerFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else if (binding.spinnerData.getSelectedItem().toString().equals("بيانات كورس")){
                    transaction.replace(R.id.container, new AddDataCourseFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else {
                    transaction.replace(R.id.container, new AddDataDepartmentFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnBack.setOnClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(ManagementDataOnAppActivity.this, R.style.AlertDialogStyle)
                    .setTitle("تاكيد ")
                    .setIcon(R.drawable.ic_data_object)
                    .setPositiveButton("حفظ ", (dialogInterface, i) -> {
                        Toast.makeText(ManagementDataOnAppActivity.this, "تم حفظ البيانات", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> finish())
                    .setMessage("هل تريد حفظ البيانات ؟");
            builder.show();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}