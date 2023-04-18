package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectcollage.R;
import com.example.projectcollage.model.Post;
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
