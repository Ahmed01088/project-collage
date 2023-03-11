package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectcollage.R;
import com.example.projectcollage.model.Post;
import com.example.projectcollage.models.Hero;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TestApiAdapter extends RecyclerView.Adapter<TestApiAdapter.ViewHolder> {
    Context context;
    List<Post> heroes;

    public TestApiAdapter(Context context, List<Post> heroes) {
        this.context = context;
        this.heroes = heroes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_api, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.team.setText(heroes.get(position).getTitle());
        holder.name.setText(heroes.get(position).getBody());
        holder.realname.setText(position+"");
        Picasso.get().load(heroes.get(position).getUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,realname,team;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameApi);
            realname=itemView.findViewById(R.id.realName);
            imageView=itemView.findViewById(R.id.image);
            team=itemView.findViewById(R.id.team);
        }
    }
}
