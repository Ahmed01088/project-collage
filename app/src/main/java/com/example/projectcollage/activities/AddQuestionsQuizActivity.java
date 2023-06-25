package com.example.projectcollage.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.AddDataQuizAdapter;
import com.example.projectcollage.databinding.ActivityAddQuestionsQuizBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Question;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

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
        int quizId=getIntent().getIntExtra(Constants.QUIZ_ID,0);
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
            ArrayList<Question>questions=adapter.getQuestions();
            for (int i = 0; i < questions.size(); i++) {
                if(!questions.get(i).isChecked()){
                    Toast.makeText(this, "من فضلك اكمل سؤال رقم "+(i+1), Toast.LENGTH_SHORT).show();
                    break;
                }
                if (i==questions.size()-1){
                    //send quiz
                    AlertDialog.Builder builder=new AlertDialog.Builder(this, R.style.AlertDialogStyle);
                    builder.setMessage("هل انت متأكد من انك تريد بدا الاختبار ؟")
                            .setPositiveButton("نعم", (dialogInterface, j) -> {
                                pushQuiz(quizId);
                                dialogInterface.dismiss();
                                finish();
                            })
                            .setNegativeButton("لا", (dialogInterface, j) -> dialogInterface.dismiss())
                            .show();
                }
           }

        });
    }
private void pushQuiz(int quiz_id){
    Call<Data<List<Question>>>call= RetrofitClientLaravelData.getInstance().getApiInterface().pushQuiz(quiz_id);
    call.enqueue(new retrofit2.Callback<Data<List<Question>>>() {
        @Override
        public void onResponse(@NonNull Call<Data<List<Question>>> call, retrofit2.Response<Data<List<Question>>> response) {
            if (response.isSuccessful()){
                    Toast.makeText(AddQuestionsQuizActivity.this, "تم ارسال الاختبار بنجاح", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailure(@NonNull Call<Data<List<Question>>> call, @NonNull Throwable t) {

        }
    });
}

}