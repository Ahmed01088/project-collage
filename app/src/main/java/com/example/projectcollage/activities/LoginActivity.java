package com.example.projectcollage.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityLoginBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.User;
import com.example.projectcollage.retrofit.RetrofitClientUser;

import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
//            boolean checkPass=binding.passwordLogin.getText().toString().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$\n");
//            if (checkPass){
                login(binding.userIdLogin.getText().toString(),binding.passwordLogin.getText().toString());
//            }else {
//                binding.passwordLogin.setError("حرف واحد على الأقل\n" +
//                        "رقم واحد على الأقل\n" +
//                        "رمز خاص واحد على الأقل\n" +
//                        "طول الكلمة يجب أن يكون على الأقل 8 أحرف");
//            }
        });
    }
    private void login(final String nationalId,final String password){
        binding.progress.setVisibility(View.VISIBLE);
        binding.login.setVisibility(View.INVISIBLE);
        Call<Data> login=RetrofitClientUser.getInstance().getApiInterfaceUser()
                .login(new User(nationalId,password));
        login.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                if (response.isSuccessful()){
                    if (nationalId.startsWith("2060")){
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
                        binding.login.setVisibility(View.VISIBLE);
                        binding.progress.setVisibility(View.GONE);

                    }else {
                        Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra(STUDENT_AFFAIRS, STUDENT_AFFAIRS);
                        ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.userIdLogin,
                                binding.userIdLogin.getWidth()/2,binding.userIdLogin.getHeight()/2,
                                100,100);
                        startActivity(intent,options.toBundle());
                        finish();
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this, "الطالب غير موجود", Toast.LENGTH_SHORT).show();
                    binding.login.setVisibility(View.VISIBLE);
                    binding.progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                binding.passwordLogin.setText(t.toString());
            }
        });

    }
}