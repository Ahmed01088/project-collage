package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.models.Question;

import java.util.ArrayList;
import java.util.Locale;

public class AddDataQuizAdapter extends RecyclerView.Adapter<AddDataQuizAdapter.ViewHolder> {
    Context context;
    ArrayList<Question>questions;

    public AddDataQuizAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_question_editable, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.question_num.setText(String.format(Locale.ENGLISH," ( %d ) ", position + 1));

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView question_num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question_num=itemView.findViewById(R.id.question_number);
        }
    }
}
