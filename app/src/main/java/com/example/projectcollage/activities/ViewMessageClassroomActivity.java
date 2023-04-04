package com.example.projectcollage.activities;
import static java.lang.Thread.sleep;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.ChatClassroomAdapter;
import com.example.projectcollage.database.Database;
import com.example.projectcollage.databinding.ActivityViewMessageClassroomBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Quiz;
import com.example.projectcollage.models.Message;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMessageClassroomActivity extends AppCompatActivity {
    ActivityViewMessageClassroomBinding binding;
    Database database;
    ArrayList<Message>chats;
    public static final String NAME_OF_COURSE="nameOfCourse";
    Bitmap bitmap;
    Uri uriImage;
    View dialog;
    Integer uid;
    int quizId;
    private Call<Data<Quiz>> call;
    SharedPreferences preferences;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewMessageClassroomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String nameOfCourse=getIntent().getStringExtra(NAME_OF_COURSE);
        database=new Database(this);
        preferences = getSharedPreferences("login", MODE_PRIVATE);
        SQLiteDatabase db=database.getWritableDatabase();
        database.createDB(db,"chat_classroom_"+nameOfCourse);
        binding.nameOfCourse.setText(nameOfCourse);
        uid=preferences.getInt("uid",0);
        if (preferences.getString("email","").startsWith("dr")){
            binding.addQuiz.setVisibility(View.VISIBLE);
            binding.startVideo.setVisibility(View.VISIBLE);
        }else {
            binding.addQuiz.setVisibility(View.GONE);
            binding.startVideo.setVisibility(View.GONE);
        }
        if (database.getAllMessage()==null){
            chats=new ArrayList<>();
        }else {
            chats=database.getAllMessage();
        }
        binding.nameOfCourse.setOnClickListener(view -> {
          ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,view.getWidth(),view.getHeight(),50,50);
          Intent intent=new Intent(this,StudentOfCourseActivity.class);
          intent.putExtra(NAME_OF_COURSE,nameOfCourse);
          startActivity(intent,options.toBundle());
        });
        binding.addQuiz.setOnClickListener(view -> {
            dialog=getLayoutInflater().inflate(R.layout.add_quiz_dialog,binding.rvMessageClassroom,false);
            AlertDialog.Builder builder=new AlertDialog.Builder(ViewMessageClassroomActivity.this,R.style.AlertDialogStyle)
                    .setPositiveButton("نعم", (dialogInterface, i) -> {
                        binding.progressBar3.setVisibility(View.VISIBLE);
                        intent=new Intent(ViewMessageClassroomActivity.this,AddQuestionsQuizActivity.class);
                        initDialog();
                        ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.addQuiz,binding.addQuiz.getWidth(),binding.addQuiz.getHeight(),300,300);
                        startActivity(intent,options.toBundle());
                        binding.progressBar3.setVisibility(View.GONE);
                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {
                        Toast.makeText(this, "تم الالغاء", Toast.LENGTH_SHORT).show();
                    });
            builder.setView(dialog);
            builder.show();

        });
        binding.startVideo.setOnClickListener(view -> {
            AppCompatEditText videoId=new AppCompatEditText(this);
            videoId.setHint("ادخل رابط الفيديو");
            videoId.setHintTextColor(Color.GRAY);
            videoId.setTextSize(16);
            videoId.setTextColor(Color.WHITE);
            videoId.setBackground(AppCompatResources.getDrawable(this,R.drawable.background_raduis_16));
            videoId.setSingleLine();
            videoId.setPadding(10,10,10,10);
            videoId.setImeOptions(EditorInfo.IME_ACTION_DONE);
            AlertDialog.Builder builder=new AlertDialog.Builder(ViewMessageClassroomActivity.this,R.style.AlertDialogStyle)
                    .setPositiveButton("نعم", (dialogInterface, i) -> {
                        Intent intent=new Intent(ViewMessageClassroomActivity.this,LiveStreamActivity.class);
                        ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.addQuiz,binding.addQuiz.getWidth(),binding.addQuiz.getHeight(),300,300);
                        startActivity(intent,options.toBundle());
                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {

                    })
                    .setMessage("هل تريد بداء فيديو لايف ؟");
            builder.setView(videoId);
            builder.show();
          });
        ChatClassroomAdapter adapter=new ChatClassroomAdapter(this,chats,nameOfCourse);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.rvMessageClassroom.setAdapter(adapter);
        binding.senderMessage.setImeOptions(EditorInfo.IME_ACTION_SEND);
        manager.setStackFromEnd(true);
        getWindow().setNavigationBarColor(this.getColor(R.color.main_bar));
        binding.senderMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Objects.requireNonNull(binding.senderMessage.getText()).toString().isEmpty()){
                    binding.iconSend.setImageResource(R.drawable.ic_mic);
                }else if (bitmap!=null||!(binding.senderMessage.getText().toString().isEmpty())){
                        binding.senderMessage.setTextColor(Color.BLACK);
                        binding.iconSend.setImageResource(R.drawable.ic_send);
                     }
                manager.setStackFromEnd(true);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                binding.rvMessageClassroom.smoothScrollToPosition(chats.size());
            }
        });
        binding.iconSelectImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);
        });
        binding.iconSend.setOnClickListener(view -> {
            Animation animationMic= AnimationUtils.loadAnimation(this, R.anim.mic_anim);
                        view.startAnimation(animationMic);
            String message= Objects.requireNonNull(binding.senderMessage.getText()).toString();
            if (!message.isEmpty()){
                Date date=new Date();
                SimpleDateFormat format=new SimpleDateFormat("HH:mm aa",Locale.ENGLISH);
                String time=format.format(date);
                database.insertMessage(new Message(message, time, bitmap));
                chats.add(new Message(message,time,uriImage));
                bitmap=null;
                adapter.notifyItemInserted(chats.size());
                binding.senderMessage.setText("");
            }

        });
        binding.rvMessageClassroom.setLayoutManager(manager);
        binding.btnBack.setOnClickListener(view -> finish());
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher
            =registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()==RESULT_OK){
                        Intent data=result.getData();
                        if (data!=null){
                            uriImage=data.getData();
                            binding.iconSend.setImageResource(R.drawable.ic_send);
                            try {
                                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriImage));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
   /*private void addQuiz(String title,String description,int numberOfQuestion,int timeLimit,int score,int lecturerId){
       Quiz quiz=new Quiz(title,description,numberOfQuestion,timeLimit,score,lecturerId);
       call= RetrofitClientLaravelData.getInstance().getApiInterfaceUser().addQuiz(quiz);
       call.enqueue(new Callback<Data<Quiz>>() {
           @Override
           public void onResponse(@NonNull Call<Data<Quiz>> call, @NonNull Response<Data<Quiz>> response) {
               if (response.isSuccessful()){
                   quizId=response.body().getData().getQuizId();
               }
           }

           @Override
           public void onFailure(@NonNull Call<Data<Quiz>> call, @NonNull Throwable t) {
               Toast.makeText(ViewMessageClassroomActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
   }*/
   private void initDialog(){
       EditText title=dialog.findViewById(R.id.dialog_title);
       EditText description=dialog.findViewById(R.id.dialog_description);
       EditText numberOfQuestion=dialog.findViewById(R.id.dialog_questions);
       EditText timeLimit=dialog.findViewById(R.id.dialog_minutes);

       Toast.makeText(this, ""+quizId, Toast.LENGTH_SHORT).show();
       intent.putExtra("numberOfQuestion",Integer.parseInt(numberOfQuestion.getText().toString()));
       intent.putExtra("timeLimit",Integer.parseInt(timeLimit.getText().toString()));
       intent.putExtra("quizId",quizId);

   }
  }

