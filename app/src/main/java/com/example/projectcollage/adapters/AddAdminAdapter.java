package com.example.projectcollage.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectcollage.R;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.StudentAffairs;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAdminAdapter extends RecyclerView.Adapter<AddAdminAdapter.ViewHolder> {
    Context context;
    List<StudentAffairs>admins;
    public AddAdminAdapter(Context context, List<StudentAffairs> admins) {
        this.context = context;
        this.admins = admins;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_student_affairs_admin, parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.iconDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(context,R.style.AlertDialogStyle)
                    .setPositiveButton("ازالة", (dialogInterface, i) -> {
                       deleteAdmin(position);
                       delete(position);
                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {
                    })
                    .setMessage("هل تريد ازاله هذا الادمن ؟");
            builder.show();
        });
        holder.name.setText(admins.get(position).getFirstName()+" "+admins.get(position).getLastName());
        holder.notionalId.setText("الرقم القومي : "+admins.get(position).getNationalId());
        holder.email.setText(admins.get(position).getEmail());
        holder.dateAdded.setText(admins.get(position).getDateAdded());
        holder.level.setText("مسئول الفرقة : "+admins.get(position).getResponsibleLevel());
    }

    @Override
    public int getItemCount() {
        return admins.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView iconDelete;
        TextView name,level,email,dateAdded,notionalId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconDelete=itemView.findViewById(R.id.icon_delete);
            name=itemView.findViewById(R.id.nameAdmin);
            level=itemView.findViewById(R.id.respnsiple_level);
            email=itemView.findViewById(R.id.emailAdmin);
            dateAdded=itemView.findViewById(R.id.data_add);
            notionalId=itemView.findViewById(R.id.nationalIdAdmin);
        }
    }
    private void deleteAdmin(int position){
        RetrofitClientLaravelData.getInstance().getApiInterface().
                deleteStudentAffairs(admins.get(position).getSaid())
                .enqueue(new Callback<Data<StudentAffairs>>() {
                    @Override
                    public void onResponse(@NonNull Call<Data<StudentAffairs>> call, @NonNull Response<Data<StudentAffairs>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            Toast.makeText(context, "تم ازاله الادمن", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Data<StudentAffairs>> call, @NonNull Throwable t) {
                        Toast.makeText(context, "خطاء", Toast.LENGTH_SHORT).show();

                    }
                });


    }
    private void delete(int position){
        admins.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    private void update(int position){
        notifyItemChanged(position);
        notifyDataSetChanged();
    }
}
