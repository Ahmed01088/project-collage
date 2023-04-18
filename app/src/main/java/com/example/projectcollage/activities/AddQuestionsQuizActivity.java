package com.example.projectcollage.activities;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.AddDataQuizAdapter;
import com.example.projectcollage.databinding.ActivityAddQuestionsQuizBinding;
import com.example.projectcollage.model.Question;
import java.util.ArrayList;
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
                                dialogInterface.dismiss();
                                finish();
                            })
                            .setNegativeButton("لا", (dialogInterface, j) -> dialogInterface.dismiss())
                            .show();
                }
           }


        });
    }


}