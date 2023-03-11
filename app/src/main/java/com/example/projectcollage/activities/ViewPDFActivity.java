package com.example.projectcollage.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityViewPdfactivityBinding;

public class ViewPDFActivity extends AppCompatActivity {
    ActivityViewPdfactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewPdfactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher
            =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()==RESULT_OK){
                        Intent data=result.getData();
                        if (data!=null){
                            Uri uri=data.getData();
                            binding.pdfview.fromUri(uri).spacing(10).load();
                        }
                    }
            }
     });
}