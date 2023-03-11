package com.example.projectcollage.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projectcollage.databinding.ActivityShowAnyDataStudentAffirsAddedBinding;

public class ShowAnyDataStudentAffairsAddedActivity extends AppCompatActivity {
    ActivityShowAnyDataStudentAffirsAddedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowAnyDataStudentAffirsAddedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}