package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.activities.AddQuestionsQuizActivity;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Question;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDataQuizAdapter extends RecyclerView.Adapter<AddDataQuizAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Question>questions;
    private final int quizId;

    public AddDataQuizAdapter(Context context, ArrayList<Question> questions, int quizId) {
        this.context = context;
        this.questions = questions;
        this.quizId = quizId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_question_editable, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.question_num.setText(String.format(Locale.ENGLISH," ( %d ) ", position+1));
        holder.done.setOnClickListener(view -> {
            Question question=questions.get(position);
            question.setQuestion(holder.questionText.getText().toString());
            question.setAnswerA(holder.answerA.getText().toString());
            question.setAnswerB(holder.answerB.getText().toString());
            question.setAnswerC(holder.answerC.getText().toString());
            question.setAnswerD(holder.answerD.getText().toString());
            question.setCorrectAnswer(holder.correctAnswerA.isChecked()?1:holder.correctAnswerB.isChecked()?2:holder.correctAnswerC.isChecked()?3:4);
            question.setQuid(quizId);
            if (!question.isChecked()){
                if (question.getQuestion().isEmpty() ||
                        question.getAnswerA().isEmpty() ||
                        question.getAnswerB().isEmpty() ||
                        question.getAnswerC().isEmpty() ||
                        question.getAnswerD().isEmpty()||
                        holder.radioGroup.getCheckedRadioButtonId()==-1){
                    Toast.makeText(context, "من فضلك املاء كل الحقول", Toast.LENGTH_SHORT).show();
                }else {
                    addQuestion(question);
                    holder.done.setImageResource(R.drawable.ic_check_circle);
                    question.setChecked(true);
                }
            }else {
                Toast.makeText(context, "تم اضافة السؤال من قبل", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return questions.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView question_num;
        EditText questionText,answerA,answerB,answerC,answerD;
        RadioButton correctAnswerA,correctAnswerB,correctAnswerC,correctAnswerD;
        RadioGroup radioGroup;
        ImageView done;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question_num=itemView.findViewById(R.id.question_number);
            questionText=itemView.findViewById(R.id.question_text);
            answerA=itemView.findViewById(R.id.answer_a);
            answerB=itemView.findViewById(R.id.answer_b);
            answerC=itemView.findViewById(R.id.answer_c);
            answerD=itemView.findViewById(R.id.answer_d);
            correctAnswerA=itemView.findViewById(R.id.correct_answer_a);
            correctAnswerB=itemView.findViewById(R.id.correct_answer_b);
            correctAnswerC=itemView.findViewById(R.id.correct_answer_c);
            correctAnswerD=itemView.findViewById(R.id.correct_answer_d);
            radioGroup=itemView.findViewById(R.id.radioChosices);
            done=itemView.findViewById(R.id.question_done);

        }
    }
    private void addQuestion(Question question){
        Call<Data<Question>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addQuestion(question);
        call.enqueue(new Callback<Data<Question>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Question>> call, @NonNull Response<Data<Question>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "تم اضافة السؤال", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<Question>> call, @NonNull Throwable t) {
                Toast.makeText(context, "حدث خطأ" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
