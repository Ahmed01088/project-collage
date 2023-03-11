package com.example.projectcollage.adapters;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectcollage.R;
import com.example.projectcollage.activities.DetailsActivity;
import com.example.projectcollage.activities.PostCommentsActivity;
import com.example.projectcollage.activities.ShowImageActivity;
import com.example.projectcollage.database.Database;
import com.example.projectcollage.models.Post;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Post>posts;
    private Database database;
    private boolean isLarge=false;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.counterReact.setText(""+CommentsAdapter.counterComments);
        holder.name.setText(posts.get(position).getNameOfPerson());
        String string = posts.get(position).getQuestion();
        if (!(string.isEmpty())){
            holder.question.setText(string);
            holder.question.setVisibility(View.VISIBLE);
            String text=holder.question.getText().toString();
            String regexLinks = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))";
            Pattern pattern=Pattern.compile(regexLinks);
            Matcher matcher=pattern.matcher(text);
            while (matcher.find()){
                String match=matcher.group();
                String newTeat=text.replaceAll(match, "");
                holder.question.setText(HtmlCompat.fromHtml(newTeat+" "+"<br><a href=\""+match+"\">"+match+"</a>",HtmlCompat.FROM_HTML_MODE_LEGACY));
                holder.question.setMovementMethod(LinkMovementMethod.getInstance());
            }

        }else {
            holder.question.setVisibility(View.GONE);
        }
        holder.time.setText(posts.get(position).getTime());
        holder.post_image.setImageBitmap(posts.get(position).getBitmap());
        database=new Database(context);
        holder.manage_post.setOnClickListener(view -> {
            PopupMenu menu=new PopupMenu(context, holder.manage_post);
            menu.getMenuInflater().inflate(R.menu.popup_menu_post, menu.getMenu());
            menu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.delete:
                        Toast.makeText(context, "تم ازاله المنشور... ", Toast.LENGTH_SHORT).show();
                        database.deletePost(posts.get(position).getId());
                        removeItem(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        return true;
                    case R.id.update:
                        Toast.makeText(context, "تم تحديث المنشور ", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            });
            menu.show();
        });
        holder.comment.setOnClickListener(view -> {
            Intent intent=new Intent(context, PostCommentsActivity.class);
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,view.getWidth()/2,view.getHeight()/2,300,300);
            context.startActivity(intent,options.toBundle());

        });

        int[] counterOfReacts = {100};
        boolean[] check = {false};
        holder.counterReact.setVisibility(View.VISIBLE);
        holder.react.setOnClickListener(view -> {
            if (check[0]) {
                check[0] =false;
                counterOfReacts[0]--;
                holder.react.setTextColor(Color.WHITE);
                holder.counterReact.setText(String.format(Locale.ENGLISH,"%d مشاهده", counterOfReacts[0]));
                if (counterOfReacts[0]!=0){
                    holder.counterReact.setVisibility(View.VISIBLE);
                }else {
                    holder.counterReact.setVisibility(View.GONE);

                }
                holder.react.setCompoundDrawableTintList(ColorStateList.valueOf(Color.WHITE));
            }else {
                check[0]=true;
                counterOfReacts[0]++;
                holder.react.setTextColor(Color.BLUE);
                holder.counterReact.setText(String.format(Locale.ENGLISH,"%d مشاهده", counterOfReacts[0]));
                if (counterOfReacts[0]!=0){
                    holder.counterReact.setVisibility(View.VISIBLE);
                }else {
                    holder.counterReact.setVisibility(View.GONE);

                }
                holder.react.setCompoundDrawableTintList(ColorStateList.valueOf(context.getColor(R.color.statesBar)));
                holder.react.setTextColor(context.getColor(R.color.statesBar));
            }
        });
        holder.post_image.setOnClickListener(view -> {
            Intent intent=new Intent(context, ShowImageActivity.class);
            ShowImageActivity.DATA=posts.get(position).getBitmap();
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,view.getWidth()/2,view.getHeight()/2,300,300);
            context.startActivity(intent,options.toBundle());
        });
        holder.name.setOnClickListener(view -> {
            Intent intent=new Intent(context, DetailsActivity.class);
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(holder.itemView,holder.itemView.getWidth()/2,holder.itemView.getHeight()/2,300,300);
            context.startActivity(intent,options.toBundle());

        });
        holder.profilePic.setOnClickListener(view -> {
            Intent intent=new Intent(context, DetailsActivity.class);
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(holder.itemView,holder.itemView.getWidth()/2,holder.itemView.getHeight()/2,300,300);
            context.startActivity(intent,options.toBundle());

        });


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,question,time,comment,react,counterReact;
        ImageView manage_post,post_image,profilePic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameOfPostPerson);
            profilePic=itemView.findViewById(R.id.profile_pic);
            question=itemView.findViewById(R.id.question_post);
            comment=itemView.findViewById(R.id.comment);
            time=itemView.findViewById(R.id.time_post);
            react=itemView.findViewById(R.id.view_post);
            counterReact=itemView.findViewById(R.id.counterReact);
            manage_post=itemView.findViewById(R.id.manage_post);
            post_image=itemView.findViewById(R.id.post_image);
        }
    }
    public  void removeItem(int position){
        posts.remove(position);
        notifyItemRemoved(position);


    }

}
