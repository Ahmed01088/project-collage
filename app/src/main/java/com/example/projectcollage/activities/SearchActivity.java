package com.example.projectcollage.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.TestApiAdapter;
import com.example.projectcollage.model.Post;
import com.example.projectcollage.retrofit.RetrofitClient;
import com.example.projectcollage.databinding.ActivitySearchBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        getTest();
    }
    private void getTest(){
        Call<List<Post>> posts= RetrofitClient.getInstance().getApiInterface().getPosts();
        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                TestApiAdapter adapter=new TestApiAdapter(SearchActivity.this, response.body());
                LinearLayoutManager manager=new LinearLayoutManager(SearchActivity.this);

                binding.rvtestRetrofit.setAdapter(adapter);
                binding.rvtestRetrofit.setLayoutManager(manager);
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {

            }
        });

    }

    private void searchData(String s){
        binding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}