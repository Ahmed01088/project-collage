package com.example.projectcollage.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityAboutDeveloperBinding;

public class AboutDeveloperActivity extends AppCompatActivity {
    ActivityAboutDeveloperBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutDeveloperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));


    }
}