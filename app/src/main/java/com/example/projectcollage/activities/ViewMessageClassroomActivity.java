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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.ChatClassroomAdapter;
import com.example.projectcollage.databinding.ActivityViewMessageClassroomBinding;
import com.example.projectcollage.model.Classroom;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Quiz;
import com.example.projectcollage.model.Message;
import com.example.projectcollage.model.Realtime;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import org.json.JSONException;
import org.json.JSONObject;
import com.pusher.client.channel.Channel;
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
    ArrayList<Message> messages;
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
    private Intent intent;
    int classroomId;
    private EditText title, description, numberQuestion, quizTime;
    public static final int PERMISSION_REQ_ID = 22;
    public static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private int lecturerId;
    private int quitTime;

    private boolean checkSelfPermission() {
        return ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewMessageClassroomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String nameOfCourse = getIntent().getStringExtra(Constants.COURSE_NAME);
        preferences = getSharedPreferences(Constants.DATA, MODE_PRIVATE);
        binding.nameOfCourse.setText(nameOfCourse);
        uid = preferences.getInt(Constants.UID, 0);
        String lecturerName = getIntent().getStringExtra(Constants.LECTURER_NAME);
        int lecturerId = getIntent().getIntExtra(Constants.LECTURER_ID, 0);
        classroomId = getIntent().getIntExtra(Constants.CLASSROOM_ID, 0);
        getMessagesByClassroomId(classroomId);
        departmentId = preferences.getInt(Constants.DEPARTMENT_ID, 0);
        manager = new LinearLayoutManager(this);
        initDialog();
        setupPusher();
        int userId = preferences.getInt(Constants.UID, 0);
        if (preferences.getString(Constants.USER_TYPE, "").equals(Constants.USER_TYPES[0]))
        {
            startQuiz(userId);
            isLive(userId);

        }



        if (preferences.getString(Constants.USER_TYPE, "").equals(Constants.USER_TYPES[1])) {
            binding.addQuiz.setVisibility(View.VISIBLE);
        }
        else {
            binding.addQuiz.setVisibility(View.GONE);
            binding.startVideo.setVisibility(View.GONE);
        }

        binding.nameOfCourse.setOnClickListener(view -> {
            ActivityOptions options = ActivityOptions.makeClipRevealAnimation(view, view.getWidth(), view.getHeight(), 50, 50);
            Intent intent = new Intent(this, StudentOfCourseActivity.class);
            intent.putExtra(Constants.LECTURER_NAME, lecturerName);
            intent.putExtra(Constants.COURSE_NAME, nameOfCourse);
            intent.putExtra(Constants.LECTURER_ID, lecturerId);
            startActivity(intent, options.toBundle());
        });
        binding.addQuiz.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewMessageClassroomActivity.this, R.style.AlertDialogStyle)
                    .setPositiveButton("ارسال", (dialogInterface, i) -> {
                        binding.loadmessage.setVisibility(View.VISIBLE);
                        String titleQuiz = title.getText().toString();
                        String descriptionQuiz = description.getText().toString();
                        int numberQuestionQuiz = Integer.parseInt(numberQuestion.getText().toString());
                        int quizTimeQuiz = Integer.parseInt(quizTime.getText().toString());
                        int courseId = getIntent().getIntExtra(Constants.QUIZ_ID, 0);
                        Quiz quiz = new Quiz(
                                titleQuiz,
                                descriptionQuiz,
                                numberQuestionQuiz,
                                quizTimeQuiz,
                                courseId,
                                classroomId,
                                uid);
                        addQuiz(quiz);
                        title.setText("");
                        description.setText("");
                        numberQuestion.setText("");
                        quizTime.setText("");
                        dialogInterface.dismiss();
                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {
                        Toast.makeText(this, "تم الالغاء", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    });
            if (dialog.getParent() != null) {
                ((ViewGroup) dialog.getParent()).removeView(dialog);
            }
            builder.setView(dialog);
            builder.show();

        });
        binding.startVideo.setOnClickListener(view -> {
//            AppCompatEditText videoId = new AppCompatEditText(this);
//            videoId.setHint("ادخل رابط الفيديو");
//            videoId.setHintTextColor(Color.GRAY);
//            videoId.setTextSize(16);
//            videoId.setTextColor(Color.WHITE);
//            videoId.setBackground(AppCompatResources.getDrawable(this, R.drawable.background_raduis_16));
//            videoId.setSingleLine();
//            videoId.setPadding(10, 10, 10, 10);
//            videoId.setImeOptions(EditorInfo.IME_ACTION_DONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewMessageClassroomActivity.this, R.style.AlertDialogStyle)
                    .setTitle(" هل تريد بداء فيديو لايف ؟")
                    .setPositiveButton("نعم", (dialogInterface, i) -> {
                        Intent intent = new Intent(ViewMessageClassroomActivity.this, LiveStreamActivity.class);
                        ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.addQuiz, binding.addQuiz.getWidth(), binding.addQuiz.getHeight(), 300, 300);
                        intent.putExtra(Constants.CLASSROOM_ID, classroomId);
                        startActivity(intent, options.toBundle());
                        startLive(classroomId);

                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {

                    });
            builder.show();
        });
        binding.senderMessage.setImeOptions(EditorInfo.IME_ACTION_SEND);
        getWindow().setNavigationBarColor(this.getColor(R.color.main_bar));
        binding.iconSelectImage.setOnClickListener(view -> {
            if (!checkSelfPermission()) {
                ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
            }
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);
        });

        binding.iconSend.setOnClickListener(v -> {
            String content = binding.senderMessage.getText().toString();
            if (!content.isEmpty()) {
                SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss aa", Locale.getDefault());
                String date=dateFormat.format(new Date());
                Message message = new Message();
                message.setContent(content);
                message.setSentAt(date);
                message.setSender(uid);
                message.setClassroomId(classroomId);
                message.setReceiver(classroomId);
                if (uriImage != null) {
                    adapter.notifyDataSetChanged();
                    createMessage(message, file);
                } else {
                    sendMessage(message);
                }
                adapter = new ChatClassroomAdapter(ViewMessageClassroomActivity.this, messages);
                binding.rvMessageClassroom.setLayoutManager(manager);
                binding.rvMessageClassroom.setAdapter(adapter);
                binding.rvMessageClassroom.scrollToPosition(messages.size() - 1);
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
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }

    private void addQuiz(Quiz quiz) {
        call = RetrofitClientLaravelData.getInstance().getApiInterface().addQuiz(quiz);
        call.enqueue(new Callback<Data<Quiz>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Quiz>> call, @NonNull Response<Data<Quiz>> response) {
                if (response.isSuccessful()) {
                    Quiz quizResponse = response.body().getData();
                    intent = new Intent(ViewMessageClassroomActivity.this, AddQuestionsQuizActivity.class);
                    ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.addQuiz, binding.addQuiz.getWidth(), binding.addQuiz.getHeight(), 300, 300);
                    intent.putExtra("classroomId", quizResponse.getClassroomId());
                    intent.putExtra(Constants.QUIZ_ID, quizResponse.getId());
                    intent.putExtra("courseId", quizResponse.getCourseId());
                    intent.putExtra("courseName", quizResponse.getTitle());
                    intent.putExtra("departmentId", departmentId);
                    intent.putExtra("numberQuestion", quizResponse.getNumberQuestions());
                    intent.putExtra("timeLimit", quizResponse.getLimitTime());
                    intent.putExtra("uid", uid);
                    startActivity(intent, options.toBundle());
                    binding.loadmessage.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Quiz>> call, @NonNull Throwable t) {
                Toast.makeText(ViewMessageClassroomActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDialog() {
        dialog = getLayoutInflater().inflate(R.layout.add_quiz_dialog, binding.getRoot(), false);
        title = dialog.findViewById(R.id.dialog_title);
        description = dialog.findViewById(R.id.dialog_description);
        numberQuestion = dialog.findViewById(R.id.dialog_questions);
        quizTime = dialog.findViewById(R.id.dialog_minutes);
    }

    private void sendMessage(Message message) {

        Call<Data<Message>> call = RetrofitClientLaravelData.getInstance().getApiInterface().addMessage(message);
        call.enqueue(new Callback<Data<Message>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Message>> call, @NonNull retrofit2.Response<Data<Message>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ViewMessageClassroomActivity.this, "لم يتم ارسال الرسالة ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Message>> call, @NonNull Throwable t) {
                Toast.makeText(ViewMessageClassroomActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void createMessage(Message message, File file) {
        RequestBody content = RequestBody.create(message.getContent(), MediaType.parse("text/plain"));
        RequestBody sentAt = RequestBody.create(message.getSentAt(), MediaType.parse("text/plain"));
        RequestBody imageBody = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), imageBody);
        Call<Data<Message>> call = RetrofitClientLaravelData.getInstance().getApiInterface().createMessage(
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
                if (!response.isSuccessful()) {
                    Toast.makeText(ViewMessageClassroomActivity.this, "لم يتم ارسال الرسالة ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Message>> call, @NonNull Throwable t) {
                Toast.makeText(ViewMessageClassroomActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMessagesByClassroomId(int classroomId) {
        Call<Data<List<Message>>> call = RetrofitClientLaravelData.getInstance().getApiInterface().getMessagesByClassroomId(classroomId);
        call.enqueue(new Callback<Data<List<Message>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Message>>> call, @NonNull Response<Data<List<Message>>> response) {
                if (response.isSuccessful()) {
                    messages = (ArrayList<Message>) response.body().getData();
                    adapter = new ChatClassroomAdapter(ViewMessageClassroomActivity.this, messages);
                    binding.rvMessageClassroom.setAdapter(adapter);
                    binding.rvMessageClassroom.scrollToPosition(messages.size() - 1);
                    binding.rvMessageClassroom.setLayoutManager(manager);
                    manager.setStackFromEnd(true);
                    binding.loadmessage.setVisibility(View.GONE);
                    if (messages.size() == 0) {
                        binding.noMessage.setVisibility(View.VISIBLE);
                    } else {
                        binding.noMessage.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Message>>> call, @NonNull Throwable t) {
                Toast.makeText(ViewMessageClassroomActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "تم منح الوصول", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "تم رفض الوصول", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupPusher() {
        PusherOptions options = new PusherOptions();
        options.setCluster(Constants.PUSHER_APP_CLUSTER);
        Pusher pusher = new Pusher(Constants.PUSHER_APP_KEY, options);
        Channel channel = pusher.subscribe("chat");
        Channel liveChannel = pusher.subscribe("live-added");
        Channel quizChannel = pusher.subscribe("quiz-added");
        quizChannel.bind("quiz-added", event -> {
            try {
            JSONObject object = new JSONObject(event.getData());
            JSONObject quizObject = object.getJSONObject("quiz");
             quizId = quizObject.getInt("id");
             lecturerId = quizObject.getInt("lecturer_id");
             quitTime = quizObject.getInt("limit_time");
            runOnUiThread(() -> {
            if(preferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[0])){
                binding.addQuiz.setVisibility(View.GONE);
                binding.answorQuiz.setVisibility(View.VISIBLE);
                Intent intent = new Intent(ViewMessageClassroomActivity.this, QuizActivity.class);
                intent.putExtra(Constants.QUIZ_ID, quizId);
                intent.putExtra(Constants.LECTURER_ID, lecturerId);
                intent.putExtra(Constants.QUIZ_TIME, quitTime);

                binding.answorQuiz.setOnClickListener(
                    v -> {
                        startActivity(intent);
                        binding.answorQuiz.setVisibility(View.GONE);

                    }
                );

                   }
            });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
        );


        channel.bind("message", event -> {
            try {
                    runOnUiThread(
                        () -> {
                            getMessagesByClassroomId(classroomId);
                            binding.rvMessageClassroom.scrollToPosition(messages.size() - 1);

                        }
                );
} catch (Exception e) {
                e.printStackTrace();
            }
        });
        liveChannel.bind("live-added", event -> {
            int classroomLive;
            try {
                JSONObject object = new JSONObject(event.getData());
                JSONObject classroom = object.getJSONObject("classroom");
               classroomLive = classroom.getInt("id");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            runOnUiThread(
                            () ->{
                                if(preferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[0])&&classroomId==classroomLive){
                                    binding.enter.playAnimation();
                                    binding.enter.setVisibility(View.VISIBLE);
                                    //set image padding
                                    binding.enter.setOnClickListener(
                                            v -> {
                                                Intent intent = new Intent(ViewMessageClassroomActivity.this, LiveStreamActivity.class);
                                                intent.putExtra(Constants.CLASSROOM_ID, classroomId);
                                                ActivityOptions activityOptions = ActivityOptions.makeClipRevealAnimation(v, v.getWidth(), v.getHeight(), 50, 50);
                                                startActivity(intent, activityOptions.toBundle());
                                            }
                                    );
                                }
                            }
                    );

            }
        );
        pusher.connect(
                new ConnectionEventListener() {
                    @Override
                    public void onConnectionStateChange(ConnectionStateChange change) {
                        Log.i("Pusher", "State changed from " + change.getPreviousState() +
                                " to " + change.getCurrentState());
                    }

                    @Override
                    public void onError(String message, String code, Exception e) {
                        Log.i("Pusher", "There was a problem connecting! " +
                                "\ncode: " + code +
                                "\nmessage: " + message +
                                "\nException: " + e
                        );
                    }
                },
                ConnectionState.ALL
        );
        runOnUiThread(() -> Toast.makeText(ViewMessageClassroomActivity.this, "تم الاتصال بالسيرفر", Toast.LENGTH_SHORT).show());

    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }
    private void startLive(int classroomId){
        Call<Data<Classroom>> call = RetrofitClientLaravelData.getInstance().getApiInterface().getLivestratedClassroom(classroomId);
        call.enqueue(
                new Callback<Data<Classroom>>() {
                    @Override
                    public void onResponse(@NonNull Call<Data<Classroom>> call, @NonNull Response<Data<Classroom>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(ViewMessageClassroomActivity.this, "تم بدء البث", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Data<Classroom>> call, @NonNull Throwable t) {
                        Toast.makeText(ViewMessageClassroomActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    private void isLive(int id){
        Call<Data<Realtime>> call = RetrofitClientLaravelData.getInstance().getApiInterface().getIsLive(id);
           call.enqueue(
                    new Callback<Data<Realtime>>() {
                        @Override
                        public void onResponse(@NonNull Call<Data<Realtime>> call, @NonNull Response<Data<Realtime>> response) {
                            if(response.isSuccessful()&&response.body().getData()!=null){
                                if(response.body().getData().getIsLive()&&response.body().getData().getClassroomId()==classroomId){
                                    binding.enter.setVisibility(View.VISIBLE);
                                    binding.enter.playAnimation();
                                    binding.enter.setOnClickListener(
                                            v -> {
                                                Intent intent = new Intent(ViewMessageClassroomActivity.this, LiveStreamActivity.class);
                                                intent.putExtra(Constants.CLASSROOM_ID, classroomId);
                                                startActivity(intent);
                                                finish();
                                            }
                                    );
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Data<Realtime>> call, @NonNull Throwable t) {
                            Toast.makeText(ViewMessageClassroomActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
    }
    private void startQuiz(int studentId){
        Call<Data<Realtime>> call = RetrofitClientLaravelData.getInstance().getApiInterface().getIsQuizStarted(studentId);
        call.enqueue(
                new Callback<Data<Realtime>>() {
                    @Override
                    public void onResponse(@NonNull Call<Data<Realtime>> call, @NonNull Response<Data<Realtime>> response) {
                        if(response.isSuccessful()&&response.body().getData().getIsQuizStarted()){
                            if(response.body().getData().getIsQuizStarted()&&response.body().getData().getClassroomId()==classroomId){
                                binding.answorQuiz.setVisibility(View.VISIBLE);
                                binding.answorQuiz.setOnClickListener(
                                        v -> {
                                            Intent intent = new Intent(ViewMessageClassroomActivity.this, QuizActivity.class);
                                            intent.putExtra(Constants.LECTURER_ID, response.body().getData().getLecturerId());
                                            intent.putExtra(Constants.QUIZ_ID, response.body().getData().getQuizId());
                                            intent.putExtra(Constants.QUIZ_TIME, response.body().getData().getQuiz().getLimitTime());
                                            Toast.makeText(ViewMessageClassroomActivity.this, ""+response.body().getData().getQuiz().getLimitTime(), Toast.LENGTH_SHORT).show();
                                            ActivityOptions options = ActivityOptions.makeClipRevealAnimation(v, v.getWidth(), v.getHeight(), 50, 50);
                                            startActivity(intent, options.toBundle());
                                            binding.answorQuiz.setVisibility(View.GONE);

                                            Toast.makeText(ViewMessageClassroomActivity.this, "تم بدء الاختبار", Toast.LENGTH_SHORT).show();

                                        }
                                );
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Data<Realtime>> call, @NonNull Throwable t) {
                        Toast.makeText(ViewMessageClassroomActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }


}