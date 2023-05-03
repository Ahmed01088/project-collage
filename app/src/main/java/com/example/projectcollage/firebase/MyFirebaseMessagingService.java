package com.example.projectcollage.firebase;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.projectcollage.R;
import com.example.projectcollage.activities.NotificationActivity;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (message.getNotification() != null) {
            String title = message.getNotification().getTitle();
            String body = message.getNotification().getBody();
            sendNotification(title, body);
        }
    }

    @Override
    public void onMessageSent(@NonNull String msgId) {
        super.onMessageSent(msgId);
        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();

    }

    private void sendNotification(String title, String body) {
        RemoteViews notificationSmall = new RemoteViews(getPackageName(), R.layout.notification_small);
        notificationSmall.setTextViewText(R.id.title, title);
        notificationSmall.setTextViewText(R.id.body, body);
        RemoteViews notificationLarge = new RemoteViews(getPackageName(), R.layout.notification_large);
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        notificationBuilder.setSmallIcon(R.drawable.app_icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setTicker("Hearty365")
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setOngoing(false)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setCustomContentView(notificationSmall)
                .setCustomBigContentView(notificationLarge)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(0, notificationBuilder.build());
    }
    @Override
    public void onNewToken(@NonNull String token) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.DATA, MODE_PRIVATE);
        int uid = sharedPreferences.getInt(Constants.UID, 0);
        storeToken(uid,token);
    }
    private void storeToken(int studentId, String token) {
        Call<Data<Student>> call = RetrofitClientLaravelData.getInstance().getApiInterface().updateFcmToken(studentId,token);
        call.enqueue(new Callback<Data<Student>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Student>> call, @NonNull Response<Data<Student>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MyFirebaseMessagingService.this, "Token stored successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Student>> call, @NonNull Throwable t) {
                 Toast.makeText(MyFirebaseMessagingService.this, "Token stored failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}