package com.example.projectcollage.firebase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.projectcollage.R;
import com.example.projectcollage.activities.NotificationActivity;
import com.example.projectcollage.activities.QuizActivity;
import com.example.projectcollage.activities.SplashActivity;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private static int count = 0;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {//Here notification is recieved from server
        try {
            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);  //you can use your launcher Activity insted of SplashActivity, But if the Activity you used here is not launcher Activty than its not work when App is in background.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//Add Any key-value to pass extras to intent
        intent.putExtra("pushnotification", "yes");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//For Android Version Orio and greater than orio.        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel("Sesame", "Sesame", importance);
        mChannel.setDescription(messageBody);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mNotifyManager.createNotificationChannel(mChannel);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "Seasame");
        mBuilder.setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.ic_notifications)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setColor(Color.parseColor("#FFD600"))
                .setContentIntent(pendingIntent)
                .setChannelId("Sesame")
                .setPriority(NotificationCompat.PRIORITY_LOW);

        mNotifyManager.notify(count, mBuilder.build());
        count++;
    }
    @Override
    public void onMessageSent(@NonNull String msgId) {
        super.onMessageSent(msgId);
    }
    public void onNewToken(@NonNull String token) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.DATA, MODE_PRIVATE);
        int uid = sharedPreferences.getInt(Constants.UID, 0);
        Log.e(TAG, "onNewToken: " + token);
        storeToken(uid,token);
    }
    private void storeToken(int studentId, String token) {
        RequestBody tokenBody = RequestBody.create(token,MediaType.parse("text/plain"));
        Call<Data<Student>> call = RetrofitClientLaravelData.getInstance().getApiInterface().updateFcmToken(studentId,tokenBody);
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