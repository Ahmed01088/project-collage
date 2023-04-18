package com.example.projectcollage.retrofit;
import static com.example.projectcollage.utiltis.Constants.BASE_URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientLaravelData {
  private static RetrofitClientLaravelData instance=null;
  private final ApiInterfaceLaravelData apiInterfaceUser;
  private RetrofitClientLaravelData() {
    Gson gson=new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    apiInterfaceUser=retrofit.create(ApiInterfaceLaravelData.class);
  }
  public static synchronized RetrofitClientLaravelData getInstance(){
    if (instance==null){
      instance=new RetrofitClientLaravelData();
    }
    return instance;
  }
  public ApiInterfaceLaravelData getApiInterface(){
    return apiInterfaceUser;
  }
}
