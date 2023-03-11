package com.example.projectcollage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.NotificationAdapter;
import com.example.projectcollage.databinding.ActivityNotificationBinding;
import com.example.projectcollage.models.Notification;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    ActivityNotificationBinding binding;
    public static final String TOKEN="f1rzRAsrRkewnpxmSsMVfG:APA91bHE0u7pTqUW4ZLoldrno31zmt6H8NDB3wX_Jqm1_tvDxEMs-e-tC_omWVLFmTtozcHXaQwV3GUyvg7LSH2xzetmafEyJvTL0ZuXfSOengiI7p2MRakk51wipDm7G3rq6zX4CkJq";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        ArrayList<Notification> notifications=new ArrayList<>();
        notifications.add(new Notification());
        notifications.add(new Notification());
        notifications.add(new Notification());
        notifications.add(new Notification());
        notifications.add(new Notification());
        LinearLayoutManager manager=new LinearLayoutManager(this);
        NotificationAdapter adapter=new NotificationAdapter(this,notifications);
        binding.rvNotification.setAdapter(adapter);
        binding.rvNotification.setLayoutManager(manager);
        binding.btnBackFromNotify.setOnClickListener(view -> finish());
        firebase();
        binding.token.setText(TOKEN);
    }
    private void firebase(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()){
                        Toast.makeText(NotificationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String token=task.getResult();
                    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
                    Log.d("token","token : "+token);

                });
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }
}
