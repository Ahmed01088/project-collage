package com.example.projectcollage.retrofit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FCMClient {
    public static final String BASE_URL_FCM="https://fcm.googleapis.com/";
    private static FCMClient instance=null;
    private final FCMApi apiInterface;
    private FCMClient(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL_FCM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface=retrofit.create(FCMApi.class);

    }
    public static synchronized FCMClient getInstance(){
        if (instance==null){
            instance=new FCMClient();
        }
        return instance;
    }
    public FCMApi getApiInterface(){
        return apiInterface;
    }
}
