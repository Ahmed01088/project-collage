package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.model.Question;

import java.util.ArrayList;
import java.util.Locale;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    Context context;
    ArrayList<Question>questions;

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
        holder.questionStudentNumber.setText(String.format(Locale.ENGLISH," ( %d ) ",(position+1)));
        holder.radioButtonA.toggle();
        holder.radioButtonA.setOnClickListener(view -> {
            holder.radioButtonB.setChecked(false);
            holder.radioButtonC.setChecked(false);
            holder.radioButtonD.setChecked(false);
            questions.get(position).setQid(position);
            questions.get(position).setCorrectAnswer(true);

        });
        holder.radioButtonB.setOnClickListener(view -> {
            holder.radioButtonA.setChecked(false);
            holder.radioButtonC.setChecked(false);
            holder.radioButtonD.setChecked(false);

        });

        holder.radioButtonC.setOnClickListener(view -> {
            holder.radioButtonB.setChecked(false);
            holder.radioButtonA.setChecked(false);
            holder.radioButtonD.setChecked(false);

        });
        holder.radioButtonD.setOnClickListener(view -> {
            holder.radioButtonB.setChecked(false);
            holder.radioButtonC.setChecked(false);
            holder.radioButtonA.setChecked(false);

        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView questionStudentNumber;
        RadioButton radioButtonA,radioButtonB,radioButtonC,radioButtonD;
        RadioGroup group;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionStudentNumber=itemView.findViewById(R.id.question_student_number);
            radioButtonA=itemView.findViewById(R.id.radioButtonA);
            radioButtonB=itemView.findViewById(R.id.radioButtonB);
            radioButtonC=itemView.findViewById(R.id.radioButtonC);
            radioButtonD=itemView.findViewById(R.id.radioButtonD);
            group=itemView.findViewById(R.id.group);
        }
    }
}
