package com.example.projectcollage.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class  RetrofitClient {
  private static RetrofitClient instance=null;
  private final ApiInterface apiInterface;
  private final String BASE_URL="https://jsonplaceholder.typicode.com/";
  private RetrofitClient(){
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    apiInterface=retrofit.create(ApiInterface.class);
  }
  public static synchronized RetrofitClient getInstance(){
    if (instance==null){
      instance=new RetrofitClient();
    }
    return instance;
  }
  public ApiInterface getApiInterface(){
    return apiInterface;
  }
}
