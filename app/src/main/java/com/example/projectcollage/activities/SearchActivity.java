package com.example.projectcollage.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.PostAdapter;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Post;
import com.example.projectcollage.databinding.ActivitySearchBinding;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    private ArrayList<Post> posts;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        binding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 value= binding.searchBox.getText().toString();
                getSearchPosts(value);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.searchIcon.setOnClickListener(view -> {
            getSearchPosts(value);
        });

    }

    private void getSearchPosts(String value){
        Call<Data<List<Post>>>call= RetrofitClientLaravelData.getInstance().getApiInterface().searchInPosts(value);
        call.enqueue(new retrofit2.Callback<Data<List<Post>>>() {

            @Override
            public void onResponse(@NonNull Call<Data<List<Post>>> call, @NonNull Response<Data<List<Post>>> response) {
                if(response.isSuccessful()) {
                    posts = new ArrayList<>(response.body().getData());
                    PostAdapter adapter = new PostAdapter(SearchActivity.this, posts);
                    binding.rvsearch.setAdapter(adapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                    binding.rvsearch.setLayoutManager(linearLayoutManager);
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);

                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<List<Post>>> call, @NonNull Throwable t) {
                Toast.makeText(SearchActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}