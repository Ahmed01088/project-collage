package com.example.projectcollage.adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.activities.ViewMessageUsersActivity;
import com.example.projectcollage.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    Context context;
    ArrayList<Student> students;

    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_student,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameOfStudent.setText(String.format("%s %s", students.get(position).getfName(), students.get(position).getlName()));

        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, ViewMessageUsersActivity.class);
            intent.putExtra("firstna", students.get(position).getfName());
            intent.putExtra("uid", students.get(position).getUid());
//            ViewMessageUsersActivity.data=students.get(position).getImageBitmap();
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfStudent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfStudent=itemView.findViewById(R.id.firstname);
        }
    }
}
