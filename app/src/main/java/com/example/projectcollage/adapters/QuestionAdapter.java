package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private final boolean []isCorrect={false,false,false,false};
    private final boolean []isChecked={false,false,false,false};
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
        holder.itemView.setTag(this);
        int correctAnswer=question.getCorrectAnswer();
        if (correctAnswer==1){
            isCorrect[0]=true;
        }else if (correctAnswer==2){
            isCorrect[1]=true;
        }else if (correctAnswer==3){
            isCorrect[2]=true;
        }else if (correctAnswer==4){
            isCorrect[3]=true;
        }
        holder.group.clearCheck();
        holder.group.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonA
                    &&holder.radioButtonA.isChecked()==isCorrect[0]) {
                if (isCorrect[0]&&!isChecked[0]){
                    score++;
                }else {
                    score--;
                    isChecked[0]=false;
                }
                } else if (checkedId == R.id.radioButtonB
                    &&holder.radioButtonB.isChecked()==isCorrect[1]) {
                if (isCorrect[1]&&!isChecked[1]){
                    score++;
                }else {
                    score--;
                    isChecked[1]=true;

                }
            } else if (checkedId == R.id.radioButtonC&&holder.radioButtonC.isChecked()==isCorrect[2]) {
                if (isCorrect[2]&&!isChecked[2]){
                    score++;
                }else {
                    score--;
                    isChecked[2]=true;

                }
            } else if (checkedId == R.id.radioButtonD&&holder.radioButtonD.isChecked()==isCorrect[3]) {
                if (isCorrect[3]&&!isChecked[3]){
                    score++;
                }else {
                    score--;
                    isChecked[3]=true;

                }
            }
        });
        /*holder.radioButtonA.setOnClickListener(view -> {
            holder.radioButtonB.setChecked(false);
            holder.radioButtonC.setChecked(false);
            holder.radioButtonD.setChecked(false);
            //answer =1;
            if (correctAnswer == 1) {
                if (holder.radioButtonA.isChecked()) {
                    if (isAChecked) {
                        score = score - 1;
                        isAChecked = false;
                        holder.radioButtonA.setChecked(false);
                    } else {
                        score = score + 1;
                        isAChecked = true;
                    }
                }
                isCorrect[0] = true;
            } else if (correctAnswer == 2) {
                if (holder.radioButtonB.isChecked()) {
                    if (isBChecked) {
                        score = score - 1;
                        isBChecked = false;
                        holder.radioButtonB.setChecked(false);

                    } else {
                        score = score + 1;
                        isBChecked = true;


                    }
                }
                isCorrect[1] = true;
            } else if (correctAnswer == 3) {
                if (holder.radioButtonC.isChecked()) {
                    if (isCChecked) {
                        score = score - 1;
                        isCChecked = false;
                        holder.radioButtonC.setChecked(false);
                    } else {
                        score = score + 1;
                        isCChecked = true;

                    }
                }
                isCorrect[2] = true;
            } else if (correctAnswer == 4) {
                if (holder.radioButtonD.isChecked()) {
                    if (isDChecked) {
                        score = score - 1;
                        isDChecked = false;
                        holder.radioButtonD.setChecked(false);

                    } else {
                        score = score + 1;
                        isDChecked = true;

                    }
                }
                isCorrect[3] = true;
            }
            Toast.makeText(context, ""+score, Toast.LENGTH_SHORT).show();

        });
        holder.radioButtonB.setOnClickListener(view -> {
            holder.radioButtonA.setChecked(false);
            holder.radioButtonC.setChecked(false);
            holder.radioButtonD.setChecked(false);
            switch (correctAnswer){
                case 1:
                    if (holder.radioButtonA.isChecked()){
                        if (isAChecked){
                            score=score-1;
                            isAChecked=false;
                            holder.radioButtonA.setChecked(false);
                        }else {
                            score=score+1;
                            isAChecked=true;
                        }
                    }
                    isCorrectAnswer=true;
                    break;
                case 2:
                    if (holder.radioButtonB.isChecked()){
                        if (isBChecked){
                            score=score-1;
                            isBChecked=false;

                            holder.radioButtonB.setChecked(false);
                        }else {
                            score=score+1;
                            isBChecked=true;
                        }
                    }
                    isCorrectAnswer=true;
                    break;
                case 3:
                    if (holder.radioButtonC.isChecked()){
                        if (isCChecked){
                            score=score-1;
                            isCChecked=false;
                            holder.radioButtonC.setChecked(false);
                        }else {
                            score=score+1;
                            isCChecked=true;
                        }
                    }
                    isCorrectAnswer=true;
                    break;
                case 4:
                    if (holder.radioButtonD.isChecked()){
                        if (isDChecked){
                            score=score-1;
                            isDChecked=false;
                            holder.radioButtonD.setChecked(false);
                        }else {
                            score=score+1;
                            isDChecked=true;
                        }
                    }
                    isCorrectAnswer=true;
                    break;
            }
            Toast.makeText(context, ""+score, Toast.LENGTH_SHORT).show();

        });

        holder.radioButtonC.setOnClickListener(view -> {
            holder.radioButtonB.setChecked(false);
            holder.radioButtonA.setChecked(false);
            holder.radioButtonD.setChecked(false);
            switch (correctAnswer){
                case 1:
                    if (holder.radioButtonA.isChecked()){
                        if (isAChecked||isCorrectAnswer){
                            score=score-1;
                            isAChecked=false;
                            holder.radioButtonA.setChecked(false);
                        }else {
                            score=score+1;
                            isAChecked=true;
                        }
                    }
                    isCorrectAnswer=true;
                    break;
                case 2:
                    if (holder.radioButtonB.isChecked()){
                        if (isBChecked||isCorrectAnswer){
                            score=score-1;
                            isBChecked=false;
                            holder.radioButtonB.setChecked(false);
                        }else {
                            score=score+1;
                            isBChecked=true;
                        }
                    }
                    isCorrectAnswer=true;
                    break;
                case 3:
                    if (holder.radioButtonC.isChecked()){
                        if (isCChecked||isCorrectAnswer){
                            score=score-1;
                            isCChecked=false;
                            holder.radioButtonC.setChecked(false);
                        }else {
                            score=score+1;
                            isCChecked=true;
                        }
                    }
                    isCorrectAnswer=true;
                    break;
                case 4:
                    if (holder.radioButtonD.isChecked()){
                        if (isDChecked||isCorrectAnswer){
                            score=score-1;
                            isDChecked=false;
                            holder.radioButtonD.setChecked(false);
                        }else {
                            score=score+1;
                            isDChecked=true;
                        }
                    }
                    isCorrectAnswer=true;
                    break;
            }
            Toast.makeText(context, ""+score, Toast.LENGTH_SHORT).show();

        });
        holder.radioButtonD.setOnClickListener(view -> {
            holder.radioButtonB.setChecked(false);
            holder.radioButtonC.setChecked(false);
            holder.radioButtonA.setChecked(false);
            switch (correctAnswer){
                case 1:
                    if (holder.radioButtonA.isChecked()){
                        if (isAChecked||isCorrectAnswer){
                            score=score-1;
                            isAChecked=false;
                            holder.radioButtonA.setChecked(false);
                        }else {
                            score=score+1;
                            isAChecked=true;
                        }
                    }
                    isCorrectAnswer=true;
                    break;
                case 2:
                    if (holder.radioButtonB.isChecked()){
                        if (isBChecked||isCorrectAnswer){
                            score=score-1;
                            isBChecked=false;
                            holder.radioButtonB.setChecked(false);
                        }else {
                            score=score+1;
                            isBChecked=true;

                        }
                    }
                    isCorrectAnswer=true;
                    break;
                case 3:
                    if (holder.radioButtonC.isChecked()){
                        if (isCChecked||isCorrectAnswer){
                            score=score-1;
                            isCChecked=false;
                            holder.radioButtonC.setChecked(false);
                        }else {
                            score=score+1;
                            isCChecked=true;
                        }

                    }
                    isCorrectAnswer=true;
                    break;
                case 4:
                    if (holder.radioButtonD.isChecked()){
                        if (isDChecked||isCorrectAnswer){
                            score=score-1;
                            isDChecked=false;
                            holder.radioButtonD.setChecked(false);
                        }else {
                            score=score+1;
                            isDChecked=true;
                        }

                    }
                    isCorrectAnswer=true;
                    break;
            }
            Toast.makeText(context, ""+score, Toast.LENGTH_SHORT).show();

        });*/

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView questionStudentNumber,questionText;
        RadioButton radioButtonA,radioButtonB,radioButtonC,radioButtonD;
        RadioGroup group;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionStudentNumber=itemView.findViewById(R.id.question_student_number);
            radioButtonA=itemView.findViewById(R.id.radioButtonA);
            radioButtonB=itemView.findViewById(R.id.radioButtonB);
            radioButtonC=itemView.findViewById(R.id.radioButtonC);
            radioButtonD=itemView.findViewById(R.id.radioButtonD);
            questionText=itemView.findViewById(R.id.question_text);
            group=itemView.findViewById(R.id.group);
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
