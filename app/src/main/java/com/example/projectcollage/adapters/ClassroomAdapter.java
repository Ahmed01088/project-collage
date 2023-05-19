package com.example.projectcollage.adapters;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectcollage.R;
import com.example.projectcollage.activities.ViewMessageClassroomActivity;
import com.example.projectcollage.model.Classroom;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ViewHolder> {
    Context context;
    ArrayList<Classroom>classrooms;
    public ClassroomAdapter(Context context, ArrayList<Classroom> courses) {
        this.context = context;
        this.classrooms = courses;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_classroom, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Classroom classroom=classrooms.get(position);
            holder.nameOfCourse.setText(classroom.getCourseName());
            holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, ViewMessageClassroomActivity.class);
            intent.putExtra(Constants.CLASSROOM_ID, classroom.getId());
            intent.putExtra("courseName", classroom.getCourseName());
            intent.putExtra("courseId", classroom.getCourseId());
            intent.putExtra(Constants.LECTURER_NAME, classroom.getLecturerName());
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,view.getWidth()/2,view.getHeight()/2,300,300);
            context.startActivity(intent,options.toBundle());
           });
    }
    @Override
    public int getItemCount() {
        return classrooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfCourse;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfCourse=itemView.findViewById(R.id.nameOfCourse);
        }
    }
}
