package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.model.Question;

import java.util.ArrayList;
import java.util.Locale;

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
        Question question=questions.get(position);
        question.setQuestion(holder.questionText.getText().toString());
        question.setAnswerA(holder.answerA.getText().toString());
        question.setAnswerB(holder.answerB.getText().toString());
        question.setAnswerC(holder.answerC.getText().toString());
        question.setAnswerD(holder.answerD.getText().toString());
        question.setCorrectAnswer(holder.correctAnswerA.isChecked()?1:holder.correctAnswerB.isChecked()?2:holder.correctAnswerC.isChecked()?3:4);
        question.setQuid(quizId);
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

        }
    }
    public ArrayList<Question> getDataUpdated () {
        return questions;
    }


}
