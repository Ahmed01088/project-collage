package com.example.projectcollage.fragments;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectcollage.activities.AddPostActivity;
import com.example.projectcollage.adapters.PostAdapter;
import com.example.projectcollage.database.Database;
import com.example.projectcollage.databinding.FragmentHomeBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Post;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    Database database;
    ArrayList<Post>posts;
    public HomeFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentHomeBinding.inflate(getLayoutInflater());
        binding.fabAddPost.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), AddPostActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        });
        getPosts();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
    private void getPosts(){
        Call<Data<List<Post>>>call= RetrofitClientLaravelData.getInstance().getApiInterface().getAllPosts();
        call.enqueue(new retrofit2.Callback<Data<List<Post>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Post>>> call, @NonNull Response<Data<List<Post>>> response) {
                if(response.isSuccessful()){
                    posts=new ArrayList<>(response.body().getData());
                    PostAdapter adapter=new PostAdapter(getContext(),posts);
                    binding.rvPosts.setAdapter(adapter);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                    binding.rvPosts.setLayoutManager(linearLayoutManager);
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    ObjectAnimator.ofFloat(binding.fabAddPost,"translationY",-1000f).setDuration(0).start();
                    ObjectAnimator.ofFloat(binding.fabAddPost,"translationY",0f).setDuration(1000).start();
                    ObjectAnimator.ofFloat(binding.fabAddPost,"alpha",0f).setDuration(0).start();
                    ObjectAnimator.ofFloat(binding.fabAddPost,"alpha",1f).setDuration(1000).start();


                }else {
                    Toast.makeText(getContext(), "Error: "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<List<Post>>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}