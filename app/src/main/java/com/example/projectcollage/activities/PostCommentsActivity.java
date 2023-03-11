package com.example.projectcollage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.CommentsAdapter;
import com.example.projectcollage.databinding.ActivityPostCommentsBinding;
import com.example.projectcollage.models.Comment;
import com.example.projectcollage.retrofit.RetrofitClient;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostCommentsActivity extends AppCompatActivity {
    ActivityPostCommentsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPostCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        Call<List<Comment>>comments= RetrofitClient.getInstance().getApiInterface().getComments();
        comments.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                CommentsAdapter adapter=new CommentsAdapter(PostCommentsActivity.this, response.body());
                LinearLayoutManager manager=new LinearLayoutManager(PostCommentsActivity.this);
                binding.rvComments.setLayoutManager(manager);
                binding.rvComments.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
        binding.btnBackFromComment.setOnClickListener(view -> finish());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}