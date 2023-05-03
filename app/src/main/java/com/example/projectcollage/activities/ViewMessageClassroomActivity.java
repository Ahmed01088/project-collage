package com.example.projectcollage.activities;
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

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.ChatClassroomAdapter;
import com.example.projectcollage.databinding.ActivityViewMessageClassroomBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Quiz;
import com.example.projectcollage.model.Message;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMessageClassroomActivity extends AppCompatActivity {
    ActivityViewMessageClassroomBinding binding;
    ArrayList<Message>messages;
    public static final String NAME_OF_COURSE="courseName";
    Bitmap bitmap;
    Uri uriImage;
    LinearLayoutManager manager;
    ChatClassroomAdapter adapter;
    File file;
    View dialog;
    int uid;
    int quizId;
    private int departmentId;
    private Call<Data<Quiz>> call;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Intent intent;
    private  EditText title,description,numberQuestion,quizTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewMessageClassroomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String nameOfCourse=getIntent().getStringExtra("courseName");
        preferences = getSharedPreferences(Constants.DATA, MODE_PRIVATE);
        binding.nameOfCourse.setText(nameOfCourse);
        uid=preferences.getInt(Constants.UID,0);
        String lecturerName=getIntent().getStringExtra("lecturerName");
        int classroomId=getIntent().getIntExtra(Constants.CLASSROOM_ID,0);
        getMessagesByClassroomId(classroomId);
        departmentId=preferences.getInt(Constants.DEPARTMENT_ID,0);
        manager=new LinearLayoutManager(this);
        initDialog();
        if (preferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[1])){
            binding.addQuiz.setVisibility(View.VISIBLE);
            binding.startVideo.setVisibility(View.VISIBLE);
        }else {
            binding.addQuiz.setVisibility(View.GONE);
            binding.startVideo.setVisibility(View.GONE);
        }

        binding.nameOfCourse.setOnClickListener(view -> {
          ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,view.getWidth(),view.getHeight(),50,50);
          Intent intent=new Intent(this,StudentOfCourseActivity.class);
          intent.putExtra("lecturerName",lecturerName);
          intent.putExtra(NAME_OF_COURSE,nameOfCourse);
          startActivity(intent,options.toBundle());
        });
        binding.addQuiz.setOnClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(ViewMessageClassroomActivity.this,R.style.AlertDialogStyle)
                    .setPositiveButton("ارسال", (dialogInterface, i) -> {
                        String titleQuiz=title.getText().toString();
                        String descriptionQuiz=description.getText().toString();
                        int numberQuestionQuiz= Integer.parseInt(numberQuestion.getText().toString());
                        int quizTimeQuiz= Integer.parseInt(quizTime.getText().toString());
                        int courseId=getIntent().getIntExtra("courseId",0);
                        Quiz quiz=new Quiz(
                                titleQuiz,
                                descriptionQuiz,
                                numberQuestionQuiz,
                                quizTimeQuiz,
                                courseId,
                                classroomId,
                                uid);
                        addQuiz(quiz);
                        dialogInterface.dismiss();
                                                })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {
                        Toast.makeText(this, "تم الالغاء", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    });
            if (dialog.getParent()!=null){
                ((ViewGroup)dialog.getParent()).removeView(dialog);
            }
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
        binding.senderMessage.setImeOptions(EditorInfo.IME_ACTION_SEND);
        getWindow().setNavigationBarColor(this.getColor(R.color.main_bar));
        binding.iconSelectImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);
        });
        binding.iconSend.setOnClickListener(v -> {
            String content=binding.senderMessage.getText().toString();
            if (!content.isEmpty()){
                SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss aa", Locale.getDefault());
                String date=dateFormat.format(new Date());
                Message message=new Message();
                message.setContent(content);
                message.setSentAt(date);
                message.setSender(uid);
                message.setClassroomId(classroomId);
                message.setReceiver(classroomId);
                message.setSenderName(preferences.getString(Constants.FIRSTNAME,"")+" "+preferences.getString(Constants.LASTNAME,""));
                if (uriImage!=null){
                    createMessage(message,file);
                }else {
                    sendMessage(message);
                }
                adapter=new ChatClassroomAdapter(ViewMessageClassroomActivity.this,messages);
                messages.add(message);
                binding.rvMessageClassroom.setLayoutManager(manager);
                binding.rvMessageClassroom.setAdapter(adapter);
                binding.rvMessageClassroom.scrollToPosition(messages.size()-1);
                adapter.notifyItemInserted(messages.size()-1);
                binding.rvMessageClassroom.setHasFixedSize(true);
                binding.senderMessage.setText("");

            }
        });
        binding.btnBack.setOnClickListener(view -> finish());
        binding.senderMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!binding.senderMessage.getText().toString().isEmpty()) {
                    binding.iconSend.setImageResource(R.drawable.ic_send);
                } else {
                    binding.iconSend.setImageResource(R.drawable.ic_mic);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher
            = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (!(data == null)) {
                            uriImage = data.getData();
                            try {
                                if (uriImage != null) {
                                    file = new File(getRealPathFromURI(uriImage));
                                }
                                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriImage));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }
    private void addQuiz(Quiz quiz){
       call= RetrofitClientLaravelData.getInstance().getApiInterface().addQuiz(quiz);
       call.enqueue(new Callback<Data<Quiz>>() {
           @Override
           public void onResponse(@NonNull Call<Data<Quiz>> call, @NonNull Response<Data<Quiz>> response) {
               if (response.isSuccessful()){
                   quizId=response.body().getData().getId();
                   editor.putInt("quizId",quizId);
                   editor.apply();
                   binding.progressBar3.setVisibility(View.VISIBLE);
                   intent=new Intent(ViewMessageClassroomActivity.this,AddQuestionsQuizActivity.class);
                   ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.addQuiz,binding.addQuiz.getWidth(),binding.addQuiz.getHeight(),300,300);
                   startActivity(intent,options.toBundle());
                   intent.putExtra("classroomId",quiz.getClassroomId());
                   intent.putExtra("quizId",quizId);
                   intent.putExtra("courseId",quiz.getCourseId());
                   intent.putExtra("courseName",quiz.getTitle());
                   intent.putExtra("departmentId",departmentId);
                   intent.putExtra("numberQuestion",quiz.getNumberQuestions());
                   intent.putExtra("timeLimit",quiz.getLimitTime());
                   intent.putExtra("uid",uid);
                   startActivity(intent,options.toBundle());
                   binding.progressBar3.setVisibility(View.GONE);

               }
           }

           @Override
           public void onFailure(@NonNull Call<Data<Quiz>> call, @NonNull Throwable t) {
               Toast.makeText(ViewMessageClassroomActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
   }
   private void initDialog(){
       dialog=getLayoutInflater().inflate(R.layout.add_quiz_dialog,binding.getRoot(),false);
       title=dialog.findViewById(R.id.dialog_title);
       description=dialog.findViewById(R.id.dialog_description);
       numberQuestion=dialog.findViewById(R.id.dialog_questions);
       quizTime=dialog.findViewById(R.id.dialog_minutes);
   }
    private void sendMessage(Message message){
        Call<Data<Message>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addMessage(message);
        call.enqueue(new Callback<Data<Message>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Message>> call, @NonNull retrofit2.Response<Data<Message>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ViewMessageClassroomActivity.this, "تم ارسال رساله", Toast.LENGTH_SHORT).show();
                    pusher();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Message>> call, @NonNull Throwable t) {

            }
        });
    }
    private void createMessage(Message message, File file){
        RequestBody content=RequestBody.create(message.getContent(), MediaType.parse("text/plain"));
        RequestBody sentAt=RequestBody.create(message.getSentAt(),MediaType.parse("text/plain"));
        RequestBody imageBody = RequestBody.create(file,MediaType.parse("image/*"));
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), imageBody);
        Call<Data<Message>> call= RetrofitClientLaravelData.getInstance().getApiInterface().createMessage(
                content,
                sentAt,
                message.getClassroomId(),
                message.getChatId(),
                message.getSender(),
                message.getReceiver(),
                imagePart
        );
        call.enqueue(new Callback<Data<Message>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Message>> call, @NonNull retrofit2.Response<Data<Message>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ViewMessageClassroomActivity.this, "تم ارسال رساله", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Message>> call, @NonNull Throwable t) {
                 Toast.makeText(ViewMessageClassroomActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
   private void getMessagesByClassroomId(int classroomId){
       Call<Data<List<Message>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getMessagesByClassroomId(classroomId);
       call.enqueue(new Callback<Data<List<Message>>>() {
           @Override
           public void onResponse(@NonNull Call<Data<List<Message>>> call, @NonNull Response<Data<List<Message>>> response) {
               if (response.isSuccessful()){
                   messages= (ArrayList<Message>) response.body().getData();
                   adapter=new ChatClassroomAdapter(ViewMessageClassroomActivity.this,messages);
                   binding.rvMessageClassroom.setAdapter(adapter);
                   binding.rvMessageClassroom.scrollToPosition(messages.size()-1);
                   binding.rvMessageClassroom.setLayoutManager(manager);
                   manager.setStackFromEnd(true);

               }
           }

           @Override
           public void onFailure(@NonNull Call<Data<List<Message>>> call, @NonNull Throwable t) {
               Toast.makeText(ViewMessageClassroomActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
   }
   private  void pusher(){
       runOnUiThread(() -> {
           PusherOptions options = new PusherOptions();
           options.setCluster("eu");
           Pusher pusher = new Pusher("5cb5996a19c6ae482eaa", options);
           pusher.connect(new ConnectionEventListener() {
               @Override
               public void onConnectionStateChange(ConnectionStateChange change) {
                   Log.d("pusher", "State changed from " + change.getPreviousState() +
                           " to " + change.getCurrentState());

               }
               @Override
               public void onError(String message, String code, Exception e) {
                  Log.d("pusher", "There was a problem connecting! " +
                           "\ncode: " + code +
                           "\nmessage: " + message +
                           "\nException: " + e);
               }
           }, ConnectionState.ALL);

           Channel channel = pusher.subscribe("chat");

           channel.bind("my-event", event -> Log.d("pusher", "Received event with data: " + event.toString()));
           pusher.user();
       });

   }
}

