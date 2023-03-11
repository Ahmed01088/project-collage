package com.example.projectcollage.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.View;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.AddDataQuizAdapter;
import com.example.projectcollage.databinding.ActivityAddQuestionsQuizBinding;
import com.example.projectcollage.models.Question;

import java.util.ArrayList;

public class AddQuestionsQuizActivity extends AppCompatActivity {
    ActivityAddQuestionsQuizBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddQuestionsQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<Question>questions=new ArrayList<>();
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        AddDataQuizAdapter adapter=new AddDataQuizAdapter(this, questions);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.rvAddDataQuiz.setAdapter(adapter);
        binding.rvAddDataQuiz.setLayoutManager(manager);
        binding.btnBack.setOnClickListener(view -> finish());
        binding.button.setOnClickListener(view -> {
           String numberOfQuestion=binding.numberOfQuistion.getText().toString();
           int num=Integer.parseInt(numberOfQuestion);
            for (int i = 0; i < num; i++) {
                questions.add(new Question());

            }
            binding.numberOfQuistion.setVisibility(View.GONE);
            binding.button.setVisibility(View.GONE);
            binding.numberOfMinites.setVisibility(View.GONE);
            binding.btnSendQuiz.setVisibility(View.VISIBLE);

        });
        binding.btnSendQuiz.setOnClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(AddQuestionsQuizActivity.this, R.style.AlertDialogStyle)
                    .setPositiveButton("ارسال", (dialogInterface, i) -> finish())
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {

                    })
                    .setMessage("هل تريد ارسال الكويز ؟");
            builder.show();
        });
    }
}