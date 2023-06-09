package com.example.projectcollage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.QuestionAdapter;
import com.example.projectcollage.databinding.ActivityQuizBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Question;
import com.example.projectcollage.model.Realtime;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
    private static final long TIMER_DURATION_MILLISECONDS = 60000;
    ActivityQuizBinding binding;
    int counterSeconds = 60;
    final int count=1000;
    QuestionAdapter adapter;
    int id;
    int quizId;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        getWindow().setStatusBarColor(getColor(R.color.statesBar));
        SharedPreferences settings = getSharedPreferences(Constants.DATA, MODE_PRIVATE);
        id = settings.getInt(Constants.UID, 0);
        quizId=getIntent().getIntExtra(Constants.QUIZ_ID,0);
        int lecturerId=getIntent().getIntExtra(Constants.LECTURER_ID,0);
        int time=getIntent().getIntExtra(Constants.QUIZ_TIME,0);
        binding.submit.setOnClickListener(view -> {
//            AppCompatEditText uid=new AppCompatEditText(this);
//            uid.setHint("ادخل الرقم القومي للتاكيد");
//            uid.setHintTextColor(Color.GRAY);
//            uid.setTextSize(16);
//            uid.setTextColor(Color.WHITE);
//            uid.setBackground(AppCompatResources.getDrawable(this,R.drawable.background_raduis_16));
//            uid.setSingleLine();
//            uid.setPadding(10,10,10,10);
//            uid.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
//            uid.setImeOptions(EditorInfo.IME_ACTION_DONE);
//            uid.setFilters(new InputFilter[] { new InputFilter.LengthFilter(16) });
       AlertDialog.Builder builder=new AlertDialog.Builder(QuizActivity.this,R.style.AlertDialogStyle)
                    .setMessage("درجتك في هذا الكويز هي"+ QuestionAdapter.getScore())
                    .setPositiveButton("ارسال", (dialogInterface, i) -> {
                        endQuizForStudent(id);
                        finish();
                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {
                    });
        builder.show();
        });
          timer(time);
        getQuestions(quizId,lecturerId);
    }
    private void timer(int timeByMints){
        long timeByMilliSeconds= (long) timeByMints *60*1000;
       countDownTimer=new CountDownTimer(timeByMilliSeconds, count) {
            @Override
            public void onTick(long l) {
                if (counterSeconds >30){
                    binding.timer.setText(String.format(Locale.ENGLISH,"الوقت المتبقي حتي انتهاء الوقت %d ث", counterSeconds));
                    counterSeconds--;
                }else {
                    binding.timer.setText(String.format(Locale.ENGLISH,"الوقت المتبقي حتي انتهاء الوقت %d ث", counterSeconds));
                    binding.timer.setTextColor(Color.RED);
                    counterSeconds--;
                    if (counterSeconds ==0){
                        endQuizForStudent(id);
                        Toast.makeText(QuizActivity.this, "تم انتهاء الوقت ", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder=new AlertDialog.Builder(QuizActivity.this,R.style.AlertDialogStyle)
                                .setMessage("درجتك في هذا الكويز هي "+QuestionAdapter.getScore())
                                .setPositiveButton("ارسال", (dialogInterface, i) -> countDownTimer.cancel())
                                .setNegativeButton("الغاء", (dialogInterface, i) -> {
                                });
                        builder.show();
                        countDownTimer.cancel();
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        };
       countDownTimer.start();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void getQuestions(int quizId,int lecturerId){
        Call<Data<List<Question>>>call= RetrofitClientLaravelData.getInstance().getApiInterface()
                .getQuestionsByQuizIdAndLecturerId(quizId,lecturerId);
        call.enqueue(new Callback<Data<List<Question>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Question>>> call, @NonNull Response<Data<List<Question>>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                            List<Question>questions=response.body().getData();
                            adapter=new QuestionAdapter(QuizActivity.this, (ArrayList<Question>) questions);
                            binding.recVQuestions.setLayoutManager(new LinearLayoutManager(QuizActivity.this));
                            binding.recVQuestions.setAdapter(adapter);


                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Question>>> call, @NonNull Throwable t) {
                                Toast.makeText(QuizActivity.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();
            }
        });
        }
        private void endQuizForStudent(int studentId) {
            Call<Data<Realtime>> call = RetrofitClientLaravelData.getInstance().getApiInterface()
                    .endQuizForStudent(studentId);
            call.enqueue(new Callback<Data<Realtime>>() {
                @Override
                public void onResponse(@NonNull Call<Data<Realtime>> call, @NonNull Response<Data<Realtime>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(QuizActivity.this, "تم انتهاء الكويز", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Data<Realtime>> call, @NonNull Throwable t) {
                    Toast.makeText(QuizActivity.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();
                }
            });
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        endQuizForStudent(id);

    }

}