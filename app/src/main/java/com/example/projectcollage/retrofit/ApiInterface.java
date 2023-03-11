package com.example.projectcollage.retrofit;

import com.example.projectcollage.model.Post;
import com.example.projectcollage.models.Comment;
import com.example.projectcollage.models.Notification;
import com.example.projectcollage.models.NotificationRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("photos/")
    Call<List<Post>> getPosts();
    @GET("comments/")
    Call<List<Comment>> getComments();

}
