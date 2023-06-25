package com.example.projectcollage.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.model.Question;

import java.util.ArrayList;
import java.util.Locale;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    Context context;
    ArrayList<Question>questions;
    static int score = 0;
    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_question,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question=questions.get(position);
        holder.questionStudentNumber.setText(String.format(Locale.ENGLISH," ( %d ) ",(position+1)));
        holder.questionText.setText(question.getQuestion());
        holder.radioButtonA.setText(question.getAnswerA());
        holder.radioButtonB.setText(question.getAnswerB());
        holder.radioButtonC.setText(question.getAnswerC());
        holder.radioButtonD.setText(question.getAnswerD());
        int correctAnswer=question.getCorrectAnswer();
        final int[] studentAnswer = {0};
        final boolean[] isChecked= {false};
        holder.done.setOnClickListener(v -> {
            if (holder.group.getCheckedRadioButtonId()==-1){
                Toast.makeText(context, "من فضلك حدد اجابة اولا", Toast.LENGTH_SHORT).show();
            }else {
                holder.done.setImageResource(R.drawable.ic_check_circle);
                if(!isChecked[0]){
                    isChecked[0]=true;
                    holder.done.setImageResource(R.drawable.ic_empty_circle);
                    if (holder.radioButtonA.isChecked())
                        studentAnswer[0] =1;
                    else if (holder.radioButtonB.isChecked())
                        studentAnswer[0] =2;
                    else if (holder.radioButtonC.isChecked())
                        studentAnswer[0] =3;
                    else if (holder.radioButtonD.isChecked())
                        studentAnswer[0] =4;
                    else
                        studentAnswer[0] =0;
                    if (studentAnswer[0] == correctAnswer){
                        score++;
                    }
                }

            }

        });

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView questionStudentNumber,questionText;
        RadioButton radioButtonA,radioButtonB,radioButtonC,radioButtonD;
        RadioGroup group;
        ImageView done;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionStudentNumber=itemView.findViewById(R.id.question_student_number);
            radioButtonA=itemView.findViewById(R.id.radioButtonA);
            radioButtonB=itemView.findViewById(R.id.radioButtonB);
            radioButtonC=itemView.findViewById(R.id.radioButtonC);
            radioButtonD=itemView.findViewById(R.id.radioButtonD);
            questionText=itemView.findViewById(R.id.question_text);

            group=itemView.findViewById(R.id.group);
            done=itemView.findViewById(R.id.ic_done);

        }
    }
    public static int getScore(){
        return score;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }



}
