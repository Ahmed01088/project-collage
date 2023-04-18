package com.example.projectcollage.activities;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityAddPostBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Post;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.squareup.picasso.Picasso;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    ActivityAddPostBinding binding;
    SharedPreferences preferences;
    private Uri uriImage;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        preferences=getSharedPreferences(Constants.DATA,MODE_PRIVATE);
        String userType=preferences.getString(Constants.USER_TYPE,"");
        binding.post.setOnClickListener(view -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime targetDateTime = currentDateTime.minusHours(2).minusMinutes(30);
            Duration duration = Duration.between(targetDateTime, currentDateTime);
            String durationString = formatDurationAsAgo(duration);
            Post post=new Post("M12121",binding.contentQuestionPost.getText().toString(),durationString);
            if (userType.equals(Constants.USER_TYPES[2])){
                post.setStudentAffairsId(preferences.getInt(Constants.UID,0));
                post.setLecturerId(null);
                post.setStudentId(null);
            } else if (userType.equals(Constants.USER_TYPES[0])){
                post.setStudentId(preferences.getInt(Constants.UID,0));
                post.setLecturerId(null);
                post.setStudentAffairsId(null);
            } else if (userType.equals(Constants.USER_TYPES[1])){
                post.setLecturerId(preferences.getInt(Constants.UID,0));
                post.setStudentId(null);
                post.setStudentAffairsId(null);
            }
            post.setLikes(100);
            post.setNumberOfComments(100);
            if (binding.contentQuestionPost.getText().toString().isEmpty()){
                binding.contentQuestionPost.setError("الرجاء كتابة المنشور");
                return;
            }
            addPost(post);
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
                                    Picasso.get().load(uriImage).into(binding.showImage);
                                    Glide.with(AddPostActivity.this).load(uriImage).into(binding.showImage);
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
    private void addPost(Post post){
        Call<Data<Post>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addPost(post);
        call.enqueue(new Callback<Data<Post>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Post>> call, @NonNull Response<Data<Post>> response) {
                if (response.isSuccessful()){

                        Toast.makeText(AddPostActivity.this, "تم النشر", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AddPostActivity.this,MainActivity.class);
                        ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.showImage,
                                binding.showImage.getWidth()/2,binding.showImage.getHeight()/2,300,300);
                        startActivity(intent,options.toBundle());
                        finish();
                    }
                else {
                    binding.contentQuestionPost.setText(response.errorBody().toString());
                    Toast.makeText(AddPostActivity.this, "Field", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<Data<Post>> call, @NonNull Throwable t) {
                Toast.makeText(AddPostActivity.this, "حدث خطأ"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String formatDurationAsAgo(Duration duration) {
        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return seconds + " seconds";
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            return minutes + " minutes";
        } else if (seconds < 86400) {
            long hours = seconds / 3600;
            return hours + " hours";
        } else {
            long days = seconds / 86400;
            return days + " days";
        }
    }

}