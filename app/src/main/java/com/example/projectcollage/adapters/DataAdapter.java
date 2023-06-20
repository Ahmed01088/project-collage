package com.example.projectcollage.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcollage.R;
import com.example.projectcollage.model.Course;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Lecturer;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    ArrayList<T> data;
    T t;

    public DataAdapter(Context context, ArrayList<T> data,T t) {
        this.context = context;
        this.data = data;
        this.t=t;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //check type of data student or lecturer list
        if (t instanceof Student){
            View view= LayoutInflater.from(context).inflate(R.layout.item_show_data_student,parent,false);
            return new ViewHolderStudent(view);
        }else if (t instanceof Lecturer){
            View view= LayoutInflater.from(context).inflate(R.layout.item_show_data_lecturer,parent,false);
            return new ViewHolderLecturer(view);
        }else if (t instanceof Course) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_show_data_course, parent, false);
            return new ViewHolderCourse(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_show_data_department, parent, false);
            return new ViewHolderDepartment(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass()== ViewHolderStudent.class){
            ((ViewHolderStudent) holder).nametudent.setText(String.format("%s %s", ((Student) data.get(position)).getfName(), ((Student) data.get(position)).getlName()));
            ((ViewHolderStudent) holder).nationalId.setText(((Student)data.get(position)).getNationalId());
            ((ViewHolderStudent) holder).levelstudent.setText(((Student)data.get(position)).getLevel());
            ((ViewHolderStudent) holder).departmentstudent.setText(((Student)data.get(position)).getDepartmentName());
            ((ViewHolderStudent) holder).edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //edit student
                }
            });
            ((ViewHolderStudent) holder).delete.setOnClickListener(v -> {
                //delete student
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("مسح الطالب");
                builder.setMessage("هل تريد مسح الطالب؟");
                builder.setPositiveButton("نعم", (dialog, which) -> {
                    deleteStudentById( ((Student) data.get(position)).getUid());
                    notifyItemRemoved(position);
                    data.remove(position);
                });
                builder.setNegativeButton("لا", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();

            });
            //set data for student
        }else if (holder.getClass()== ViewHolderLecturer.class){
            ((ViewHolderLecturer) holder).namelecturer.setText(String.format("%s %s", ((Lecturer) data.get(position)).getfName(), ((Lecturer) data.get(position)).getlName()));
            String nationalId=((Lecturer)data.get(position)).getNationalId();
            ((ViewHolderLecturer) holder).nationalId.setText(nationalId.replace("2060",""));
            ((ViewHolderLecturer) holder).departmentlecturer.setText(((Lecturer)data.get(position)).getDepartmentName());
            ((ViewHolderLecturer) holder).edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //edit lecturer
                    Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                }
            });
            ((ViewHolderLecturer) holder).delete.setOnClickListener(v -> {
                //delete lecturer
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("مسح المحاضر");
                builder.setMessage("هل تريد مسح المحاضر؟");
                builder.setPositiveButton("نعم", (dialog, which) -> {
                    deleteLecturerById(((Lecturer) data.get(position)).getLid());
                    notifyItemRemoved(position);
                    data.remove(position);

                });
                builder.setNegativeButton("لا", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();
            });
            //set data for lecturer
        }else if (holder.getClass()== ViewHolderCourse.class){
            //set data for course
            ((ViewHolderCourse) holder).namecourse.setText(((Course)data.get(position)).getName());
            ((ViewHolderCourse) holder).codecourse.setText(((Course)data.get(position)).getCourseCode());
            ((ViewHolderCourse) holder).levelcourse.setText(((Course)data.get(position)).getLevel());
            ((ViewHolderCourse) holder).departmentcourse.setText(((Course)data.get(position)).getDepartmentName());
            ((ViewHolderCourse) holder).edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //edit course
                    Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                }
            });
            ((ViewHolderCourse) holder).delete.setOnClickListener(v -> {
                //delete course
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("مسح المقرر");
                builder.setMessage("هل تريد مسح المقرر؟");
                builder.setPositiveButton("نعم", (dialog, which) -> {
                    deleteCourseById(((Course) data.get(position)).getCid());
                    notifyItemRemoved(position);
                    data.remove(position);
                });
                builder.setNegativeButton("لا", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();

            });
        }else {
            //set data for department
            ((ViewHolderDepartment) holder).namedepartment.setText(((com.example.projectcollage.model.Department)data.get(position)).getName());
            ((ViewHolderDepartment) holder).semister.setText(((com.example.projectcollage.model.Department)data.get(position)).getSemester());
            ((ViewHolderDepartment) holder).level.setText(((com.example.projectcollage.model.Department)data.get(position)).getLevel());
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolderStudent extends RecyclerView.ViewHolder{
        TextView nametudent,nationalId,levelstudent,departmentstudent;
        ImageView edit,delete;

        public ViewHolderStudent(@NonNull View itemView) {
            super(itemView);
            nametudent=itemView.findViewById(R.id.dataname);
            nationalId=itemView.findViewById(R.id.datanational);
            levelstudent=itemView.findViewById(R.id.datalevel);
            departmentstudent=itemView.findViewById(R.id.datadepartment);
            edit=itemView.findViewById(R.id.editstudent);
            delete=itemView.findViewById(R.id.deletestudent);
        }
    }
    public static class ViewHolderLecturer extends RecyclerView.ViewHolder{
        TextView namelecturer,nationalId,departmentlecturer;
        ImageView edit,delete;

        public ViewHolderLecturer(@NonNull View itemView) {
            super(itemView);
            namelecturer=itemView.findViewById(R.id.datanamelecturer);
            nationalId=itemView.findViewById(R.id.datanationallecturer);
            departmentlecturer=itemView.findViewById(R.id.datadepartmentlecturer);
            edit=itemView.findViewById(R.id.editlecturer);
            delete=itemView.findViewById(R.id.deletelecturer);
        }
    }
    public static class ViewHolderCourse extends RecyclerView.ViewHolder{
        TextView namecourse,codecourse,levelcourse,departmentcourse;
        ImageView edit,delete;

        public ViewHolderCourse(@NonNull View itemView) {
            super(itemView);
            namecourse=itemView.findViewById(R.id.datanamecourse);
            codecourse=itemView.findViewById(R.id.datacodecourse);
            levelcourse=itemView.findViewById(R.id.datalevelcourse);
            departmentcourse=itemView.findViewById(R.id.datadepartmentcourse);
            edit=itemView.findViewById(R.id.editcourse);
            delete=itemView.findViewById(R.id.deletecourse);

        }
    }
    public static class ViewHolderDepartment extends RecyclerView.ViewHolder{
        TextView namedepartment,semister,level;

        public ViewHolderDepartment(@NonNull View itemView) {
            super(itemView);
            namedepartment=itemView.findViewById(R.id.datanamedepartment);
            semister=itemView.findViewById(R.id.datasemisterdepartment);
            level=itemView.findViewById(R.id.dataleveldepartment);

        }
    }

 private void deleteStudentById(int id){
     Call<Data<Student>> call= RetrofitClientLaravelData.getInstance().getApiInterface().deleteStudentById(id);
     call.enqueue(new Callback<Data<Student>>() {
         @Override
         public void onResponse(@NonNull Call<Data<Student>> call, @NonNull Response<Data<Student>> response) {
             if (response.isSuccessful()){
                 Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onFailure(@NonNull Call<Data<Student>> call, @NonNull Throwable t) {
             Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
         }
     });
 }
 private void deleteCourseById(int id){
     Call<Data<Course>> call= RetrofitClientLaravelData.getInstance().getApiInterface().deleteCourseById(id);
     call.enqueue(new Callback<Data<Course>>() {
         @Override
         public void onResponse(@NonNull Call<Data<Course>> call, @NonNull Response<Data<Course>> response) {
             if (response.isSuccessful()){
                 Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onFailure(@NonNull Call<Data<Course>> call, @NonNull Throwable t) {
             Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
         }
     });
 }
 private void deleteLecturerById(int id){
     Call<Data<Lecturer>> call= RetrofitClientLaravelData.getInstance().getApiInterface().deleteLecturerById(id);
     call.enqueue(new Callback<Data<Lecturer>>() {
         @Override
         public void onResponse(@NonNull Call<Data<Lecturer>> call, @NonNull Response<Data<Lecturer>> response) {
             if (response.isSuccessful()){
                 Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onFailure(@NonNull Call<Data<Lecturer>> call, @NonNull Throwable t) {
             Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
         }
     });
 }

}
