package com.example.projectcollage.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.AddDataQuizAdapter;
import com.example.projectcollage.databinding.ActivityAddQuestionsQuizBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Question;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddQuestionsQuizActivity extends AppCompatActivity {
    ActivityAddQuestionsQuizBinding binding;
    ArrayList<Question>questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddQuestionsQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        questions=new ArrayList<>();
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        int numberOfQuestion=getIntent().getIntExtra("numberQuestion",0);
        int timeLimit=getIntent().getIntExtra("timeLimit",0);
        int quizId=getIntent().getIntExtra("quizId",0);
        int courseId=getIntent().getIntExtra("courseId",0);
        String courseName=getIntent().getStringExtra("courseName");
        for (int i = 0; i < numberOfQuestion; i++) {
            questions.add(new Question());
        }
        AddDataQuizAdapter adapter=new AddDataQuizAdapter(this, questions, quizId);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.rvAddDataQuiz.setAdapter(adapter);
        binding.rvAddDataQuiz.setLayoutManager(manager);
        binding.btnBack.setOnClickListener(
                v -> finish()
        );
        binding.btnSendQuiz.setOnClickListener(view -> {
            manager.setStackFromEnd(true);
            AlertDialog.Builder builder=new AlertDialog.Builder(AddQuestionsQuizActivity.this, R.style.AlertDialogStyle)
                    .setPositiveButton("ارسال", (dialogInterface, i) -> {
                        adapter.notifyDataSetChanged();
                        ArrayList<Question> updated= adapter.getDataUpdated();
                        for (Question question : updated) {
                            addQuestion(question);
                        }
                        Toast.makeText(AddQuestionsQuizActivity.this, "تم ارسال الاختبار", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        finish();
                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {
                        Toast.makeText(AddQuestionsQuizActivity.this, "تم الغاء الارسال", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                     })
                    .setTitle("هل انت متاكد من ارسال الاختبار؟");
            builder.setCancelable(false);
            builder.show();
        });
    }
    private void addQuestion(Question question){
        Call<Data<Question>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addQuestion(question);
        call.enqueue(new Callback<Data<Question>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Question>> call, @NonNull Response<Data<Question>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AddQuestionsQuizActivity.this, "تم اضافة السؤال", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<Question>> call, @NonNull Throwable t) {
                Toast.makeText(AddQuestionsQuizActivity.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
            }
        });
    }


}