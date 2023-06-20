package com.example.projectcollage.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityEditDataBinding;

public class EditDataActivity extends AppCompatActivity {
    ActivityEditDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}