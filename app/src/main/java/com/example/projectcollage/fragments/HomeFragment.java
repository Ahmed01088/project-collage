package com.example.projectcollage.fragments;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.projectcollage.activities.AddPostActivity;
import com.example.projectcollage.adapters.PostAdapter;
import com.example.projectcollage.database.Database;
import com.example.projectcollage.databinding.FragmentHomeBinding;
import com.example.projectcollage.models.Post;
import java.util.ArrayList;

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
        database=new Database(getContext());
        SQLiteDatabase sqLiteDatabase=database.getWritableDatabase();
        database.createTablePosts(sqLiteDatabase,"posts");
        if (!(database.getAllPosts()==null)){
            posts=database.getAllPosts();

        }else {
               posts=new ArrayList<>();
        }
        PostAdapter adapter=new PostAdapter(binding.getRoot().getContext(),posts);
        LinearLayoutManager manager=new LinearLayoutManager(binding.getRoot().getContext());
        binding.rvPosts.setAdapter(adapter);
        binding.rvPosts.setLayoutManager(manager);
        binding.fabAddPost.setOnClickListener(view -> {
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,view.getWidth(),view.getHeight(),50,50);
            ObjectAnimator animator=ObjectAnimator.ofFloat(binding.fabAddPost, "rotation", 0f,45f);
            animator.setDuration(300);
            animator.start();
            Intent intent=new Intent(getContext(), AddPostActivity.class);
            startActivity(intent,options.toBundle());
            getActivity().finish();

        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
}