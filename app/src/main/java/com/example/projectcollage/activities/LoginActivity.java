package com.example.projectcollage.activities;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.agora.common.annotation.NonNull;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private final String ADMIN="admin";
    static final String STUDENT_AFFAIRS="studentf";
    public static boolean drCode=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getColor(R.color.dark_blue));
        getWindow().setNavigationBarColor(getColor(R.color.statesOnBoarding));
        preferences=getSharedPreferences("login",MODE_PRIVATE);
        editor=preferences.edit();
        if(!(preferences.getString("name",null)==null&&preferences.getString("userId",null)==null&&preferences.getString("admin",null)==null)) {
            if (preferences.getString("admin", "null").equals(STUDENT_AFFAIRS)){
                Intent intent=new Intent(LoginActivity.this,ManagementDataOnAppActivity.class);
                editor.putString("admin",ADMIN);
                ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.main,
                        binding.main.getWidth()/2,binding.main.getHeight()/2,300,300);
                startActivity(intent,options.toBundle());
                finish();
            }else {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("name", Objects.requireNonNull(binding.userIdLogin.getText()).toString());
                intent.putExtra("userId", Objects.requireNonNull(binding.passwordLogin.getText()).toString());
                ActivityOptions options= ActivityOptions.
                makeClipRevealAnimation(binding.userIdLogin,binding.userIdLogin.getWidth()/2,binding.userIdLogin.getHeight()/2,300,300);
                startActivity(intent,options.toBundle());
                finish();
            }
        }
        binding.login.setOnClickListener(view -> {

            if (Objects.requireNonNull(binding.userIdLogin.getText()).toString().isEmpty()){
                binding.textInputLayout.setError("ادخل الاسم");
            }else if (Objects.requireNonNull(binding.userIdLogin.getText()).toString().equals(STUDENT_AFFAIRS)){
                AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this,R.style.AlertDialogStyle)
                        .setPositiveButton("شئون طلاب ", (dialogInterface, i) -> {
                            Intent intent=new Intent(LoginActivity.this,ManagementDataOnAppActivity.class);
                            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.login,binding.login.getWidth()/2,
                                    binding.login.getHeight()/2,100,100);
                            startActivity(intent,options.toBundle());
                        }).setNegativeButton("دخول عادي", (dialogInterface, i) -> {

                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra(STUDENT_AFFAIRS, STUDENT_AFFAIRS);
                            finish();
                            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.userIdLogin,
                                    binding.userIdLogin.getWidth()/2,binding.userIdLogin.getHeight()/2,
                                    100,100);
                            startActivity(intent,options.toBundle());
                        }).setMessage("اختار طريقة الدخول ");
                builder.show();
            }else if (binding.userIdLogin.getText().toString().equals(ADMIN)){
                Intent intent=new Intent(LoginActivity.this,AddAdminStudentAffairsActivity.class);
                ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.login,binding.login.getWidth()/2,binding.login.getHeight()/2,300,300);
                startActivity(intent,options.toBundle());
                finish();
            }else {
                drCode= binding.userIdLogin.getText().toString().startsWith("dr");
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("name",binding.userIdLogin.getText().toString());
                intent.putExtra("drCodeCheck",drCode);
                intent.putExtra("userId", Objects.requireNonNull(binding.passwordLogin.getText()).toString());
                editor.putString("name",binding.userIdLogin.getText().toString());
                editor.putBoolean("drCodeCheck",drCode);
                editor.putString("userId",binding.passwordLogin.getText().toString());
                editor.apply();
                finish();
                ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.userIdLogin,binding.userIdLogin.getWidth()/2,binding.userIdLogin.getHeight()/2,300,300);
                startActivity(intent,options.toBundle());
            }
        });
    }
    private void addDataToFireBase(){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("userId", binding.userIdLogin.getText().toString());
        user.put("password", binding.passwordLogin.getText().toString());
        db.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference -> Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(LoginActivity.this, "Failed"+e, Toast.LENGTH_SHORT).show();
                    Log.d("TAG", e.toString());
                });
        }
}