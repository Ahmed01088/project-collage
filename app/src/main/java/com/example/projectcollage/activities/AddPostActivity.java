package com.example.projectcollage.activities;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.database.Database;
import com.example.projectcollage.databinding.ActivityAddPostBinding;
import com.example.projectcollage.models.Post;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPostActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 300;
    ActivityAddPostBinding binding;
    Database database;
    private Uri uriImage;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        database=new Database(this);
        binding.post.setOnClickListener(view -> {
            String name="احمد ابراهيم";
            String  question=binding.contentQuestionPost.getText().toString();
            Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm aa", Locale.ENGLISH);
                String time = format.format(date);
                binding.progressBar.setVisibility(View.VISIBLE);
                database.insertPost(new Post(name, question, time, bitmap));
                new Handler().postDelayed(() -> {
                    Toast.makeText(AddPostActivity.this, "تم النشر ...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }, SPLASH_DISPLAY_LENGTH);
         });
        binding.btnPostBack.setOnClickListener(view -> {
            if (!(binding.contentQuestionPost.toString().isEmpty())){
                AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                        .setPositiveButton("نعم", (dialogInterface, i) -> {
                            Intent intent=new Intent(AddPostActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .setNegativeButton("الغاء", (dialogInterface, i) -> {

                        })
                        .setMessage("هل تريد تجاهل المنشور ؟");
                builder.show();

            }

        });
        binding.addImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,MainActivity.class);
        ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.showImage,
                binding.showImage.getWidth()/2,binding.showImage.getHeight()/2,300,300);
        startActivity(intent,options.toBundle());

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher
            =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data=result.getData();
                        if (!(data == null)) {
                            uriImage = data.getData();
                            try {
                                if (uriImage!=null){
                                    binding.card.setVisibility(View.VISIBLE);
                                    binding.showImage.setImageURI(uriImage);
                                }else {
                                    binding.card.setVisibility(View.GONE);

                                }
                                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriImage));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

}