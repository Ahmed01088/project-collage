package com.example.projectcollage.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));

        binding.rateing.setOnClickListener(view -> {
            binding.layoutRateing.setVisibility(View.VISIBLE);
        });
        binding.sendRate.setOnClickListener(view -> {
            binding.layoutRateing.setVisibility(View.GONE);
            Toast.makeText(DetailsActivity.this, "تم ارسال تقييمك شكرا لمشاركة رايك ...", Toast.LENGTH_SHORT).show();
        });
    }
}