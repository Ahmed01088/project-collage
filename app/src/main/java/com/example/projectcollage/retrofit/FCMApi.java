package com.example.projectcollage.retrofit;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMApi {
    @Headers({"Content-Type:application/json",
            "Authorization:key=your-key"})
    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body JsonObject body);

}
