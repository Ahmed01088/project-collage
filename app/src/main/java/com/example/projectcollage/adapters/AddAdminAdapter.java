package com.example.projectcollage.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectcollage.R;
import com.example.projectcollage.models.Admin;
import java.util.List;
public class AddAdminAdapter extends RecyclerView.Adapter<AddAdminAdapter.ViewHolder> {
    Context context;
    List<Admin>admins;

    public AddAdminAdapter(Context context, List<Admin> admins) {
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
                       admins.remove(position);
                       notifyItemRemoved(position);
                    })
                    .setNegativeButton("الغاء", (dialogInterface, i) -> {
                    })
                    .setMessage("هل تريد ازاله هذا الادمن ؟");
            builder.show();
        });

    }

    @Override
    public int getItemCount() {
        return admins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView iconDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconDelete=itemView.findViewById(R.id.icon_delete);
        }
    }
}
