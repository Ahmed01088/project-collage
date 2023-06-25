package com.example.projectcollage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.NotificationAdapter;
import com.example.projectcollage.databinding.ActivityNotificationBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Notification;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class NotificationActivity extends AppCompatActivity {
    ActivityNotificationBinding binding;
    SharedPreferences sharedPreferences;
    public static final String TOKEN="f1rzRAsrRkewnpxmSsMVfG:APA91bHE0u7pTqUW4ZLoldrno31zmt6H8NDB3wX_Jqm1_tvDxEMs-e-tC_omWVLFmTtozcHXaQwV3GUyvg7LSH2xzetmafEyJvTL0ZuXfSOengiI7p2MRakk51wipDm7G3rq6zX4CkJq";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        sharedPreferences=getSharedPreferences(Constants.DATA,MODE_PRIVATE);

        binding.btnBackFromNotify.setOnClickListener(view -> finish());
        firebase();
        if (sharedPreferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[0])){
            getNotificationByStudentId(sharedPreferences.getInt(Constants.UID,0));

        }else if (sharedPreferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[1])){
            getNotificationByLecturerId(sharedPreferences.getInt(Constants.UID,0));
    }
    }
    private void firebase(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()){
                        Toast.makeText(NotificationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String token=task.getResult();
                    Log.d("token","token : "+token);

                });
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }
    private void getNotificationByStudentId(int studentId){
        Call<Data<List<Notification>>>call= RetrofitClientLaravelData
                .getInstance()
                .getApiInterface()
                .getNotificationByStudentId(studentId);
        call.enqueue(new retrofit2.Callback<Data<List<Notification>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Notification>>> call, @NonNull retrofit2.Response<Data<List<Notification>>> response) {
                if (response.isSuccessful()) {
                    Data<List<Notification>> data = response.body();
                        if (data.getData() != null) {
                            ArrayList<Notification> notifications = (ArrayList<Notification>) data.getData();
                            NotificationAdapter adapter = new NotificationAdapter(NotificationActivity.this, notifications);
                            binding.rvNotification.setAdapter(adapter);
                            binding.rvNotification.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Notification>>> call, @NonNull Throwable t) {
                Toast.makeText(NotificationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        }

      private void getNotificationByLecturerId(int lecturerId){
        Call<Data<List<Notification>>>call= RetrofitClientLaravelData
                .getInstance()
                .getApiInterface()
                .getNotificationByLecturerId(lecturerId);
        call.enqueue(new retrofit2.Callback<Data<List<Notification>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Notification>>> call, @NonNull retrofit2.Response<Data<List<Notification>>> response) {
                if (response.isSuccessful()) {
                    Data<List<Notification>> data = response.body();
                        if (data.getData() != null) {
                            ArrayList<Notification> notifications = (ArrayList<Notification>) data.getData();
                            NotificationAdapter adapter = new NotificationAdapter(NotificationActivity.this, notifications);
                            binding.rvNotification.setAdapter(adapter);
                            binding.rvNotification.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Notification>>> call, @NonNull Throwable t) {
                Toast.makeText(NotificationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
      }
    }
