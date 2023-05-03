package com.example.projectcollage.retrofit;

import com.example.projectcollage.model.PushNotification;
import com.example.projectcollage.utiltis.Constants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMApi {
    @Headers({"Content-Type:"+ Constants.CONTENT_TYPE,
            "Authorization:"+ Constants.SERVER_KEY})
    @POST("fcm/send")
    Call<PushNotification> sendNotification(@Body PushNotification pushNotification);



}
