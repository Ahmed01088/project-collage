package com.example.projectcollage.adapters;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.activities.ViewMessageClassroomActivity;
import com.example.projectcollage.models.Course;

import java.util.ArrayList;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ViewHolder> {
    Context context;
    ArrayList<Course>courses;
    public ClassroomAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_classroom, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           holder.nameOfCourse.setText(courses.get(position).getName());
           holder.itemView.setOnClickListener(view -> {
               Intent intent=new Intent(context, ViewMessageClassroomActivity.class);
               intent.putExtra("nameOfCourse", courses.get(position).getName());
               intent.putExtra("id", position);
               ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,view.getWidth()/2,view.getHeight()/2,300,300);
               context.startActivity(intent,options.toBundle());
           });
    }
    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfCourse;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfCourse=itemView.findViewById(R.id.nameOfCourse);
        }
    }
}
