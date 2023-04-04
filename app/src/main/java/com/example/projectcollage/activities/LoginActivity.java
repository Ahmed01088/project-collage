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
    public String[] UserType = {"Student Affairs", "Student", "Lecturer", "Admin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getColor(R.color.statesOnBoarding));
        getWindow().setNavigationBarColor(getColor(R.color.statesOnBoarding));
        preferences = getSharedPreferences("login", MODE_PRIVATE);
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
        Call<Data<StudentAffairs>> login = RetrofitClientLaravelData.getInstance().getApiInterfaceUser()
                .loginStudentAffairs(nationalId, password);
        login.enqueue(new Callback<Data<StudentAffairs>>() {
            @Override
            public void onResponse(@NonNull Call<Data<StudentAffairs>> call, @NonNull Response<Data<StudentAffairs>> response) {
                if (response.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogStyle)
                            .setPositiveButton("شئون طلاب ", (dialogInterface, i) -> {
                                Intent intent = new Intent(LoginActivity.this, ManagementDataOnAppActivity.class);
                                intent.putExtra("firstname", response.body().getData().getFirstName());
                                intent.putExtra("lastname", response.body().getData().getLastName());
                                intent.putExtra("nationalId", response.body().getData().getNationalId());
                                intent.putExtra("uid", response.body().getData().getSaid());
                                intent.putExtra("email", response.body().getData().getEmail());
                                intent.putExtra("userType", UserType[0]);
                                ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.login, binding.login.getWidth() / 2,
                                        binding.login.getHeight() / 2, 100, 100);
                                startActivity(intent, options.toBundle());
                            }).setNegativeButton("دخول عادي", (dialogInterface, i) -> {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("firstname", response.body().getData().getFirstName());
                                intent.putExtra("uid", response.body().getData().getSaid());
                                intent.putExtra("email", response.body().getData().getEmail());
                                intent.putExtra("nationalId", response.body().getData().getNationalId());
                                intent.putExtra("userType", UserType[0]);
                                ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.nationalId,
                                        binding.nationalId.getWidth() / 2, binding.nationalId.getHeight() / 2,
                                        100, 100);
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
        Call<Data<Admin>> login = RetrofitClientLaravelData.getInstance().getApiInterfaceUser()
                .loginAdmin(nationalId, password);
        login.enqueue(new Callback<Data<Admin>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Admin>> call, @NonNull Response<Data<Admin>> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, AddAdminStudentAffairsActivity.class);
                    intent.putExtra("firstname", response.body().getData().getFirstName());
                    intent.putExtra("lastname", response.body().getData().getLastName());
                    intent.putExtra("nationalId", response.body().getData().getNationalId());
                    intent.putExtra("email", response.body().getData().getEmail());
                    intent.putExtra("uid", response.body().getData().getAid());
                    intent.putExtra("userType", UserType[3]);
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
        Call<Data<Student>> login = RetrofitClientLaravelData.getInstance().getApiInterfaceUser()
                .loginStudent(nationalId, password);
        login.enqueue(new Callback<Data<Student>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Student>> call, @NonNull Response<Data<Student>> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("firstname", response.body().getData().getfName());
                    intent.putExtra("lastname", response.body().getData().getlName());
                    intent.putExtra("nationalId", response.body().getData().getNationalId());
                    intent.putExtra("email", response.body().getData().getEmail());
                    intent.putExtra("uid", response.body().getData().getUid());
                    intent.putExtra("userType", UserType[1]);
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
        Call<Data<Lecturer>> login = RetrofitClientLaravelData.getInstance().getApiInterfaceUser()
                .loginLecturer(nationalId, password);
        login.enqueue(new Callback<Data<Lecturer>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Lecturer>> call, @NonNull Response<Data<Lecturer>> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("firstname", response.body().getData().getfName());
                    intent.putExtra("lastname", response.body().getData().getlName());
                    intent.putExtra("nationalId", response.body().getData().getNationalId());
                    intent.putExtra("email", response.body().getData().getEmail());
                    intent.putExtra("uid", response.body().getData().getLid());
                    intent.putExtra("userType", UserType[2]);
                    ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.login, binding.login.getWidth() / 2,
                            binding.login.getHeight() / 2, 100, 100);
                    startActivity(intent, options.toBundle());
                    Toast.makeText(LoginActivity.this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "المدرس غير موجود", Toast.LENGTH_SHORT).show();
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
