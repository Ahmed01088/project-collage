package com.example.projectcollage.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.AddAdminAdapter;
import com.example.projectcollage.databinding.ActivityAddAdminStudentAffairsBinding;
import com.example.projectcollage.models.Admin;
import java.util.ArrayList;
import java.util.List;
public class AddAdminStudentAffairsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ActivityAddAdminStudentAffairsBinding binding;
    String[] responsibleLevel ={"الربعة","التالتة ","التانية","الاولي "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddAdminStudentAffairsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<Admin>admins=new ArrayList<>();
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        binding.logout.setOnClickListener(view -> startActivity(new Intent(this,LoginActivity.class)));
        AddAdminAdapter adapter=new AddAdminAdapter(this, admins);
        ArrayAdapter<String> adapterLevels=
                new ArrayAdapter<>(this, R.layout.item_spinner, responsibleLevel);
        adapterLevels.setDropDownViewResource(R.layout.item_spinner);
        binding.studentLevel.setAdapter(adapterLevels);

        binding.addAdmin.setOnClickListener(view -> {
            if (binding.emailAdminS.getText()!=null &&!(binding.emailAdminS.getText().toString().isEmpty())) {
                admins.add(new Admin(binding.emailAdminS.getText() + "",  true));
                adapter.notifyItemInserted(admins.size());
                preferences=getSharedPreferences("settings",MODE_PRIVATE);
                editor=preferences.edit();
                editor.putString("email", binding.emailAdminS.getText().toString());
                binding.emailAdminS.setText("");

            }
        });
        admins.add(new Admin("ahmedibrahim@gmail.com",  true));
        admins.add(new Admin("ahmedibreffeahimew@gmail.com", true));
        binding.rvStudentAffairsAdmin.setAdapter(adapter);
        binding.rvStudentAffairsAdmin.setLayoutManager(new LinearLayoutManager(this));
    }
}