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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityAddPostBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Notification;
import com.example.projectcollage.model.Post;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    ActivityAddPostBinding binding;
    SharedPreferences preferences;
    private Uri uriImage;
    private Bitmap bitmap;
    private File file;
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
            post.setLikes(0);
            post.setNumberOfComments(3);
            if (binding.contentQuestionPost.getText().toString().isEmpty()){
                binding.contentQuestionPost.setError("الرجاء كتابة المنشور");
                return;
            }
            if (file!=null){
                createPost(post,file);
            }else {
                addPost(post);
            }
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
                                    file=new File(getRealPathFromURI(uriImage));
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

    private void createPost(Post post, File file) {
        ProgressBar progressBar=new ProgressBar(this);
        progressBar.setVisibility(View.VISIBLE);
        RequestBody imageBody = RequestBody.create( file,MediaType.parse("image/*"));
        RequestBody titleBody = RequestBody.create(post.getTitle(),MediaType.parse("text/plain"));
        RequestBody contentBody = RequestBody.create(post.getContent(),MediaType.parse("text/plain"));
        RequestBody postedAtBody = RequestBody.create(post.getPosted_at(),MediaType.parse("text/plain"));
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), imageBody);
        Call<Data<Post>> call = RetrofitClientLaravelData.getInstance().getApiInterface().createPost(titleBody, contentBody, imagePart, postedAtBody, post.getStudentId(), post.getStudentAffairsId(), post.getLecturerId(), post.getLikes(), post.getNumberOfComments());
        call.enqueue(new Callback<Data<Post>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Post>> call, @NonNull Response<Data<Post>> response) {
                if (response.isSuccessful()) {
                    Data<Post> data = response.body();
                    if (data != null) {
                            Toast.makeText(AddPostActivity.this, "تم اضافة المنشور ", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AddPostActivity.this,MainActivity.class);
                            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.showImage,
                                    binding.showImage.getWidth()/2,binding.showImage.getHeight()/2,300,300);
                        sendNotificationsForAllStudents(new Notification(post.getPersonName() + "تم اضافة منشور جديد بواسطة",post.getContent()));
                        startActivity(intent,options.toBundle());
                            finish();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(AddPostActivity.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddPostActivity.this, response.errorBody()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Post>> call, @NonNull Throwable t) {
                Toast.makeText(AddPostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }
    private void addPost(Post post) {
        Call<Data<Post>> call = RetrofitClientLaravelData.getInstance().getApiInterface().addPost(post);
        call.enqueue(new Callback<Data<Post>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Post>> call, @NonNull Response<Data<Post>> response) {
                if (response.isSuccessful()) {
                    Data<Post> data = response.body();
                    if (data != null) {
                            Toast.makeText(AddPostActivity.this, "تم اضافة المنشور ", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AddPostActivity.this,MainActivity.class);
                            sendNotificationsForAllStudents(new Notification(post.getPersonName() + "تم اضافة منشور جديد بواسطة",post.getContent()));
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddPostActivity.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();
                        }

                } else {
                    Toast.makeText(AddPostActivity.this, response.errorBody()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Post>> call, @NonNull Throwable t) {
                // handle network failure
                // ...
                Toast.makeText(AddPostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNotificationsForAllStudents(Notification notification) {
        Call<Void> call = RetrofitClientLaravelData.getInstance().getApiInterface().sendNotificationsForAllStudents(notification);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddPostActivity.this, "حدث خطأ ما " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(AddPostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}