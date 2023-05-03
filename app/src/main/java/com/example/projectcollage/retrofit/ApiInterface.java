package com.example.projectcollage.retrofit;

import com.example.projectcollage.model.Post;
import com.example.projectcollage.models.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("photos/")
    Call<List<Post>> getPosts();
    @GET("comments/")
    Call<List<Comment>> getComments();

}
