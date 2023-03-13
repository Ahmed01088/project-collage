package com.example.projectcollage.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.QuestionAdapter;
import com.example.projectcollage.databinding.ActivityQuizBinding;
import com.example.projectcollage.model.Question;

import java.util.ArrayList;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;
    int counterSeconds = 60;
    final int count=1000;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<Question>questions=new ArrayList<>();
        questions.add(new Question());
        questions.add(new Question());
        questions.add(new Question());
        questions.add(new Question());
        questions.add(new Question());
        questions.add(new Question());
        timer();
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        getWindow().setStatusBarColor(getColor(R.color.statesBar));
        QuestionAdapter adapter = new QuestionAdapter(this,questions);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.recVQuestions.setAdapter(adapter);
        binding.recVQuestions.setLayoutManager(manager);
        binding.submit.setOnClickListener(view -> {
        AlertDialog.Builder builder=new AlertDialog.Builder(QuizActivity.this,R.style.AlertDialogStyle)
                    .setMessage("درجتك في هذا الكويز هي 15")
                    .setPositiveButton("ارسال", (dialogInterface, i) -> {
                        timer.cancel();
                        finish();
                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {
                    });
            builder.show();
        });
    }
    private void timer(){
       timer=new CountDownTimer(61000, count) {
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
                        Toast.makeText(QuizActivity.this, "تم انتهاء الوقت ", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder=new AlertDialog.Builder(QuizActivity.this,R.style.AlertDialogStyle)
                                .setMessage("تم انتهاء الوقت\nدرجتك في هذا الكويز هي 15")
                                .setPositiveButton("ارسال", (dialogInterface, i) -> timer.cancel())
                                .setNegativeButton("الغاء", (dialogInterface, i) -> {
                                });
                        builder.show();
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        };
       timer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
        finish();
    }
}