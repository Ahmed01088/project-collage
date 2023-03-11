package com.example.projectcollage.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityShowImageBinding;
public class ShowImageActivity extends AppCompatActivity{
    public static Bitmap DATA;
    ActivityShowImageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        binding.showImageActivity.setImageBitmap(DATA);
        binding.btnBackShow.setOnClickListener(view -> {
            finish();
        });
    }
}