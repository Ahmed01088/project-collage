package com.example.projectcollage.retrofit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FCMClient {
    public static final String BASE_URL_FCM="https://fcm.googleapis.com/";
    private static Retrofit retrofit=null;
    public static  Retrofit getInstance(){
        if (retrofit==null){
            OkHttpClient okHttpClient =new OkHttpClient.Builder().addInterceptor(
                    chain -> {
                        Request original=chain.request();
                        Request.Builder builder=original.newBuilder()
                                .addHeader("Content-Type:application/json",
                                        "Authorization:key=your-key")
                                .method(original.method(), original.body());
                        Request request=builder.build();
                        return chain.proceed(request);
                    }
            ).build();
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL_FCM)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
