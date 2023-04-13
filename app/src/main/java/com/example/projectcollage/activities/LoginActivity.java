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
import com.example.projectcollage.model.Admin;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Lecturer;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.model.StudentAffairs;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static String[] UserType = {"Student Affairs", "Student", "Lecturer", "Admin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getColor(R.color.statesOnBoarding));
        getWindow().setNavigationBarColor(getColor(R.color.statesOnBoarding));
        preferences = getSharedPreferences("login", MODE_PRIVATE);
        editor=preferences.edit();
        if (preferences.getString("nationalId", null) != null) {
            String userType = preferences.getString("userType", null);
            if (userType.equals(UserType[0])) {
                if (preferences.getString("studentAffairs", null)!=null&&
                        preferences.getString("studentAffairs", null).equals("Admin")) {
                    startActivity(new Intent(this, ManagementDataOnAppActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            } else if (userType.equals(UserType[1])) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else if (userType.equals(UserType[2])) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else if (userType.equals(UserType[3])) {
                startActivity(new Intent(this, AddAdminStudentAffairsActivity.class));
                finish();
            }
        }
        binding.login.setOnClickListener(view -> {
            String nationalId = binding.nationalId.getText().toString();
            String password = binding.passwordLogin.getText().toString();
            if (nationalId.startsWith("2020")){
                loginAdmin(nationalId, password);
            }else if (nationalId.startsWith("2080")){
                loginStudentAffairs(nationalId, password);
            } else if (nationalId.startsWith("2060")){
                loginLecturer(nationalId, password);
            } else{
                loginStudent(nationalId, password);
            }
        });

    }

    private void loginStudentAffairs(final String nationalId, final String password) {
        binding.progress.setVisibility(View.VISIBLE);
        binding.login.setVisibility(View.INVISIBLE);
        Call<Data<StudentAffairs>> login = RetrofitClientLaravelData.getInstance().getApiInterface()
                .loginStudentAffairs(nationalId, password);
        login.enqueue(new Callback<Data<StudentAffairs>>() {
            @Override
            public void onResponse(@NonNull Call<Data<StudentAffairs>> call, @NonNull Response<Data<StudentAffairs>> response) {
                if (response.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogStyle)
                            .setPositiveButton("شئون طلاب ", (dialogInterface, i) -> {
                                Intent intent = new Intent(LoginActivity.this, ManagementDataOnAppActivity.class);
                                editor.putString("studentAffairs", "Admin");
                                editor.putString("userType", UserType[0]);
                                editor.putString("nationalId", nationalId);
                                editor.putString("firstName", response.body().getData().getFirstName());
                                editor.putString("lastName", response.body().getData().getLastName());
                                editor.putString("email", response.body().getData().getEmail());
                                editor.putInt("uid", response.body().getData().getSaid());
                                editor.apply();
                                ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.login, binding.login.getWidth() / 2,
                                        binding.login.getHeight() / 2, 100, 100);
                                startActivity(intent, options.toBundle());
                                binding.progress.setVisibility(View.GONE);
                                binding.login.setVisibility(View.VISIBLE);
                            }).setNegativeButton("دخول عادي", (dialogInterface, i) -> {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                editor.putString("nationalId", nationalId);
                                editor.putString("firstName", response.body().getData().getFirstName());
                                editor.putString("lastName", response.body().getData().getLastName());
                                editor.putString("email", response.body().getData().getEmail());
                                editor.putInt("uid", response.body().getData().getSaid());
                                editor.putString("studentAffairs", "User");
                                editor.putString("userType", UserType[0]);
                                editor.apply();
                                ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.nationalId,
                                        binding.nationalId.getWidth() / 2, binding.nationalId.getHeight() / 2,
                                        100, 100);
                                binding.progress.setVisibility(View.GONE);
                                binding.login.setVisibility(View.VISIBLE);
                                startActivity(intent, options.toBundle());
                                finish();
                            }).setMessage("اختار طريقة الدخول ");
                    builder.show();
                } else {
                    binding.progress.setVisibility(View.INVISIBLE);
                    binding.login.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "خطأ في البيانات", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<StudentAffairs>> call, @NonNull Throwable t) {
                binding.progress.setVisibility(View.INVISIBLE);
                binding.login.setVisibility(View.VISIBLE);
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(LoginActivity.this, "خطأ في الاتصال", Toast.LENGTH_SHORT).show();
                } else if (t instanceof ConnectException) {
                    Toast.makeText(LoginActivity.this, "خطأ في الاتصال", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
    private void loginAdmin(final String nationalId, final String password) {
        binding.progress.setVisibility(View.VISIBLE);
        binding.login.setVisibility(View.INVISIBLE);
        Call<Data<Admin>> login = RetrofitClientLaravelData.getInstance().getApiInterface()
                .loginAdmin(nationalId, password);
        login.enqueue(new Callback<Data<Admin>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Admin>> call, @NonNull Response<Data<Admin>> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, AddAdminStudentAffairsActivity.class);
                    editor.putString("nationalId", nationalId);
                    editor.putString("firstName", response.body().getData().getFirstName());
                    editor.putString("lastName", response.body().getData().getLastName());
                    editor.putString("email", response.body().getData().getEmail());
                    editor.putString("userType", UserType[3]);
                    editor.putInt("uid", response.body().getData().getAid());
                    editor.apply();
                    ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.login, binding.login.getWidth() / 2,
                            binding.login.getHeight() / 2, 100, 100);
                    startActivity(intent, options.toBundle());
                    Toast.makeText(LoginActivity.this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "المسئول غير موجود", Toast.LENGTH_SHORT).show();
                }
                binding.login.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<Data<Admin>> call, @NonNull Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof ConnectException) {
                    Toast.makeText(LoginActivity.this, "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
                }
                binding.login.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }
        });

    }
    private void loginStudent(final String nationalId, final String password) {
        binding.progress.setVisibility(View.VISIBLE);
        binding.login.setVisibility(View.INVISIBLE);
        Call<Data<Student>> login = RetrofitClientLaravelData.getInstance().getApiInterface()
                .loginStudent(nationalId, password);
        login.enqueue(new Callback<Data<Student>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Student>> call, @NonNull Response<Data<Student>> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);intent.putExtra("firstname", response.body().getData().getfName());intent.putExtra("lastname", response.body().getData().getlName());
                    editor.putString("nationalId", response.body().getData().getNationalId());
                    editor.putString("firstName", response.body().getData().getfName());
                    editor.putString("lastName", response.body().getData().getlName());
                    editor.putString("email", response.body().getData().getEmail());
                    editor.putInt("uid", response.body().getData().getUid());
                    editor.putString("userType", UserType[1]);
                    editor.putInt("departmentId", response.body().getData().getDepartmentId());
                    editor.apply();
                    ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.login, binding.login.getWidth() / 2,
                            binding.login.getHeight() / 2, 100, 100);
                    startActivity(intent, options.toBundle());
                    Toast.makeText(LoginActivity.this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "الطالب غير موجود", Toast.LENGTH_SHORT).show();
                }
                binding.login.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<Data<Student>> call, @NonNull Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof ConnectException) {
                    Toast.makeText(LoginActivity.this, "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
                }
                binding.login.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }
        });

    }
    private void loginLecturer(final String nationalId, final String password) {
        binding.progress.setVisibility(View.VISIBLE);
        binding.login.setVisibility(View.INVISIBLE);
        Call<Data<Lecturer>> login = RetrofitClientLaravelData.getInstance().getApiInterface()
                .loginLecturer(nationalId, password);
        login.enqueue(new Callback<Data<Lecturer>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Lecturer>> call, @NonNull Response<Data<Lecturer>> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    editor.putString("nationalId", response.body().getData().getNationalId());
                    editor.putString("firstName", response.body().getData().getfName());
                    editor.putString("lastName", response.body().getData().getlName());
                    editor.putString("email", response.body().getData().getEmail());
                    editor.putInt("uid", response.body().getData().getLid());
                    editor.putString("userType", UserType[2]);
                    editor.apply();
                    ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.login, binding.login.getWidth() / 2,
                            binding.login.getHeight() / 2, 100, 100);
                    startActivity(intent, options.toBundle());
                    Toast.makeText(LoginActivity.this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "المدرس غير موجود", Toast.LENGTH_SHORT).show();
                     editor.clear();
                }
                binding.login.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<Data<Lecturer>> call, @NonNull Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof ConnectException) {
                    Toast.makeText(LoginActivity.this, "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
                }
                binding.login.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
            }
        });

    }
}
