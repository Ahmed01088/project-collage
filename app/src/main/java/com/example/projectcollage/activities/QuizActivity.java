package com.example.projectcollage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.QuestionAdapter;
import com.example.projectcollage.databinding.ActivityQuizBinding;
import com.example.projectcollage.model.Question;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;
    int counterSeconds = 60;
    final int count=1000;
    CountDownTimer timer;
    QuestionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        getWindow().setStatusBarColor(getColor(R.color.statesBar));
        binding.submit.setOnClickListener(view -> {
            AppCompatEditText uid=new AppCompatEditText(this);
            uid.setHint("ادخل الرقم القومي للتاكيد");
            uid.setHintTextColor(Color.GRAY);
            uid.setTextSize(16);
            uid.setTextColor(Color.WHITE);
            uid.setBackground(AppCompatResources.getDrawable(this,R.drawable.background_raduis_16));
            uid.setSingleLine();
            uid.setPadding(10,10,10,10);
            uid.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            uid.setImeOptions(EditorInfo.IME_ACTION_DONE);
            uid.setFilters(new InputFilter[] { new InputFilter.LengthFilter(16) });
            AlertDialog.Builder builder=new AlertDialog.Builder(QuizActivity.this,R.style.AlertDialogStyle)
                    .setMessage("درجتك في هذا الكويز هي"+ QuestionAdapter.getScore())
                    .setPositiveButton("ارسال", (dialogInterface, i) -> {
                        finish();
                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {
                    });
        builder.setView(uid);
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
                                .setMessage("درجتك في هذا الكويز هي "+QuestionAdapter.getScore())
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    /*private void getQuestions(){
        Call<List<Question>>call= RetrofitClientLaravelData.getInstance().getApiInterfaceUser()
                .getQuestionsByQuizIdAndLecturerId(1,1);
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(@NonNull Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()){
                    List<Question>questions=response.body();
                    adapter = new QuestionAdapter(QuizActivity.this,(ArrayList<Question>)questions);
                    LinearLayoutManager manager=new LinearLayoutManager(QuizActivity.this);
                    binding.recVQuestions.setAdapter(adapter);
                    binding.recVQuestions.setLayoutManager(manager);
                    binding.recVQuestions.setHasFixedSize(true);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Question>> call, @NonNull Throwable t) {
                Toast.makeText(QuizActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}