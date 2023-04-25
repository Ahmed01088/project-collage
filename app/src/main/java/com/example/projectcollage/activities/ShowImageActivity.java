package com.example.projectcollage.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityShowImageBinding;
import com.example.projectcollage.utiltis.Constants;
import com.squareup.picasso.Picasso;

public class ShowImageActivity extends AppCompatActivity{
    public static String DATA;
    ActivityShowImageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        String path=getIntent().getStringExtra(Constants.PATH);
        Picasso.get().load(path).into(binding.showImageActivity);
        binding.btnBackShow.setOnClickListener(view -> {
            finish();
        });
    }
}