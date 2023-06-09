package com.example.projectcollage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.CommentsAdapter;
import com.example.projectcollage.databinding.ActivityPostCommentsBinding;
import com.example.projectcollage.model.Comment;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostCommentsActivity extends AppCompatActivity {
    ActivityPostCommentsBinding binding;
    SharedPreferences preferences;
    CommentsAdapter adapter;
    List<Comment> comments;
    int postId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPostCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        preferences=getSharedPreferences(Constants.DATA,MODE_PRIVATE);
        postId=getIntent().getIntExtra(Constants.POST_ID,0);
        getCommentsByPostId(postId);
        comments=new ArrayList<>();
        setupPusher();
        binding.iconSend.setOnClickListener(view -> {
            Comment comment=new Comment();
            comment.setPostId(postId);
             comment.setCommentText(binding.senderMessage.getText().toString());
             String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
             String fullName=preferences.getString(Constants.FIRSTNAME,"") + " " + preferences.getString(Constants.LASTNAME,"");
             comment.setTimestamp(currentDateTimeString);
             comment.setPersonName(fullName);
             if (preferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[0])){
                 comment.setStudentId(preferences.getInt(Constants.UID,0));
                 comment.setLecturerId(null);
                 comment.setStudentAffairsId(null);
             }else if (preferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[1])){
                 comment.setLecturerId(preferences.getInt(Constants.UID,0));
                 comment.setStudentId(null);
                 comment.setStudentAffairsId(null);
             }else if (preferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[2])){
                 comment.setStudentAffairsId(preferences.getInt(Constants.UID,0));
                 comment.setStudentId(null);
                 comment.setLecturerId(null);
             }
                addComment(comment);
                comments.add(comment);
                adapter=new CommentsAdapter(PostCommentsActivity.this,comments);
                binding.rvComments.setAdapter(adapter);
                LinearLayoutManager manager=new LinearLayoutManager(PostCommentsActivity.this);
                binding.rvComments.setLayoutManager(manager);
                adapter.notifyItemInserted(comments.size()-1);
                manager.scrollToPosition(comments.size()-1);
                manager.setStackFromEnd(true);
             binding.senderMessage.setText("");
         });
        binding.btnBackFromComment.setOnClickListener(view -> finish());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    private void addComment(Comment comment){
      Call<Data<Comment>>call=  RetrofitClientLaravelData.getInstance().getApiInterface().addComment(comment);
      call.enqueue(new Callback<Data<Comment>>() {
          @Override
          public void onResponse(@NonNull Call<Data<Comment>> call, @NonNull Response<Data<Comment>> response) {
              if(response.isSuccessful()){
               Toast.makeText(PostCommentsActivity.this, "تم اضافة التعليق", Toast.LENGTH_SHORT).show();
                         }
          }

          @Override
          public void onFailure(@NonNull Call<Data<Comment>> call, @NonNull Throwable t) {
                Toast.makeText(PostCommentsActivity.this, "حدث خطأ", Toast.LENGTH_SHORT).show();

          }
      });
    }
    private void getCommentsByPostId(int postId){
        Call<Data<List<Comment>>>call= RetrofitClientLaravelData.getInstance().getApiInterface().getCommentsByPostId(postId);
        call.enqueue(new Callback<Data<List<Comment>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Comment>>> call, @NonNull Response<Data<List<Comment>>> response) {
                if (response.isSuccessful()) {
                    comments = response.body().getData();
                    adapter = new CommentsAdapter(PostCommentsActivity.this, comments);
                    binding.rvComments.setAdapter(adapter);
                    LinearLayoutManager manager = new LinearLayoutManager(PostCommentsActivity.this);
                    binding.rvComments.setLayoutManager(manager);
                    adapter.notifyItemInserted(comments.size() - 1);
                    manager.scrollToPosition(comments.size() - 1);
                    manager.setStackFromEnd(true);


                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Comment>>> call, @NonNull Throwable t) {
                Toast.makeText(PostCommentsActivity.this, "حدث خطأ", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void setupPusher(){
        PusherOptions options = new PusherOptions();
        options.setCluster(Constants.PUSHER_APP_CLUSTER);
        Pusher pusher = new Pusher(Constants.PUSHER_APP_KEY, options);
        Channel channel = pusher.subscribe("comment-posted");
        channel.bind("comment-posted", event -> {
            runOnUiThread(() -> {
                getCommentsByPostId(postId);
            });
        }
        );
        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {


            }

            @Override
            public void onError(String message, String code, Exception e) {

            }
        }, ConnectionState.ALL);
    }
}