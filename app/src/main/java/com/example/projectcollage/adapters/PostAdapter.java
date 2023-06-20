package com.example.projectcollage.adapters;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Post;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Post> posts;
    SharedPreferences sharedPreferences;
    int counterOfReacts;
    public PostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
        sharedPreferences=context.getSharedPreferences(Constants.DATA,Context.MODE_PRIVATE);
        setupPusher();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (posts.get(position).getLecturerId() != null) {
            holder.ic_verified_account.setVisibility(View.VISIBLE);
            holder.levelPost.setVisibility(View.GONE);
            holder.name.setText(String.format(" الدكتور %s", posts.get(position).getPersonName()));
        } else if (posts.get(position).getStudentAffairsId() != null){
            holder.ic_verified_account.setVisibility(View.VISIBLE);
            holder.levelPost.setVisibility(View.GONE);
            holder.name.setText(String.format("شئوان طلاب %s", posts.get(position).getPersonName()));
        }
        else {
            holder.name.setText(String.format("الطالب %s", posts.get(position).getPersonName()));
            holder.ic_verified_account.setVisibility(View.GONE);
        }
        holder.counterReact.setText(String.format(Locale.ENGLISH,"%d مشاهدة ", posts.get(position).getLikes()));
        holder.number_of_comments.setText(String.format("%d تعليق", posts.get(position).getNumberOfComments()));
       if (posts.get(position).getPersonImage()!=null){
           Picasso.get()
                   .load(Constants.BASE_URL_PATH_USERS+posts.get(position).getPersonImage())
                   .placeholder(R.drawable.avatar)
                   .error(R.drawable.ic_image)
                   .into(holder.profilePic);
       }else {
           holder.profilePic.setImageResource(R.drawable.avatar);
       }
        holder.time.setText(posts.get(position).getPosted_at());
        if(posts.get(position).getImage()!=null) {
             Picasso.get()
                    .load(Constants.BASE_URL_PATH_POSTS+posts.get(position).getImage())
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .into(holder.post_image);
        }else {
            holder.post_image.setVisibility(View.GONE);
        }
        String string = posts.get(position).getContent();
        if (!(string.isEmpty())){
            holder.question.setText(string);
            holder.question.setVisibility(View.VISIBLE);
            String text=holder.question.getText().toString();
            Pattern pattern=Pattern.compile(Constants.REGEX_LINKS);
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
        holder.manage_post.setOnClickListener(view -> {
            Call<Data<Post>> call= RetrofitClientLaravelData.getInstance().getApiInterface().deletePostByStudentId(posts.get(position).getId(),sharedPreferences.getInt(Constants.UID,0));
            call.enqueue(new Callback<Data<Post>>() {
                @Override
                public void onResponse(@NonNull Call<Data<Post>> call, @NonNull Response<Data<Post>> response) {
                    if (response.isSuccessful()){
                        if (response.body().getData()!=null){
                            showMenu(context,holder.manage_post,position).show();
                        }else {
                            showMenu(context,holder.manage_post,position).dismiss();

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Data<Post>> call, @NonNull Throwable t) {
                    Toast.makeText(context, "خطا در حذف پست", Toast.LENGTH_SHORT).show();
                }
            });
        });
        holder.comment.setOnClickListener(view -> {
            Intent intent=new Intent(context, PostCommentsActivity.class);
            intent.putExtra(Constants.POST_ID,posts.get(position).getId());
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,view.getWidth()/2,view.getHeight()/2,300,300);
            context.startActivity(intent,options.toBundle());
        });
        counterOfReacts = posts.get(position).getLikes();
        boolean[] check = {false};
        holder.counterReact.setVisibility(View.VISIBLE);
        holder.react.setOnClickListener(view -> {
            if (check[0]) {
                check[0] =false;
                counterOfReacts--;
                addRectOnPost(posts.get(position).getId(),counterOfReacts);
                holder.react.setTextColor(Color.WHITE);
                holder.counterReact.setText(String.format(Locale.ENGLISH,"%d مشاهده", counterOfReacts));
                if (counterOfReacts!=0){
                    holder.counterReact.setVisibility(View.VISIBLE);
                }else {
                    holder.counterReact.setVisibility(View.GONE);
                }
                holder.react.setCompoundDrawableTintList(ColorStateList.valueOf(Color.WHITE));
            }else {
                check[0]=true;
                counterOfReacts++;
                addRectOnPost(posts.get(position).getId(),counterOfReacts);
                holder.react.setTextColor(Color.BLUE);
                holder.counterReact.setText(String.format(Locale.ENGLISH,"%d مشاهده", counterOfReacts));
                if (counterOfReacts!=0){
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
                        ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,view.getWidth()/2,view.getHeight()/2,300,300);
                        intent.putExtra(Constants.PATH,Constants.BASE_URL_PATH_POSTS+posts.get(position).getImage());
            context.startActivity(intent,options.toBundle());
        });
        holder.question.setOnLongClickListener(view -> {
            ClipboardManager clipboardManager=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData=ClipData.newPlainText("text",holder.question.getText().toString());
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(context, "تم نسخ ...", Toast.LENGTH_SHORT).show();
            return true;
        });
        holder.name.setOnClickListener(view -> {
            Intent intent=new Intent(context, DetailsActivity.class);
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(holder.itemView,holder.itemView.getWidth()/2,holder.itemView.getHeight()/2,300,300);
            intent.putExtra(Constants.IMAGE,Constants.BASE_URL_PATH_USERS+posts.get(position).getPersonImage());
            context.startActivity(intent,options.toBundle());

        });
        holder.profilePic.setOnClickListener(view -> {
            Intent intent=new Intent(context, DetailsActivity.class);
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(holder.itemView,holder.itemView.getWidth()/2,holder.itemView.getHeight()/2,300,300);
            intent.putExtra(Constants.IMAGE,Constants.BASE_URL_PATH_USERS+posts.get(position).getPersonImage());
            context.startActivity(intent,options.toBundle());

        });


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,question,time,comment,react,counterReact,number_of_comments,levelPost;
        ImageView manage_post,post_image,profilePic,ic_verified_account;
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
            number_of_comments=itemView.findViewById(R.id.numberOfComments);
            ic_verified_account=itemView.findViewById(R.id.ic_verified_account);
            levelPost=itemView.findViewById(R.id.levelPost);
        }
    }
    public  void removeItem(int position){
        posts.remove(position);
        notifyItemRemoved(position);
    }
    private PopupMenu showMenu(Context context, View view, int position){
        PopupMenu menu=new PopupMenu(context, view);
        menu.getMenuInflater().inflate(R.menu.popup_menu_post, menu.getMenu());
        menu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.delete:
                    Toast.makeText(context, "تم ازاله المنشور... ", Toast.LENGTH_SHORT).show();
                    removeItem(position);
                    notifyDataSetChanged();
                    return true;
                case R.id.update:
                    Toast.makeText(context, "تم تحديث المنشور ", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });
       return menu;
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.itemView.clearAnimation();

    }
    private void setupPusher(){
        PusherOptions options = new PusherOptions();
        options.setCluster(Constants.PUSHER_APP_CLUSTER);
        Pusher pusher = new Pusher(Constants.PUSHER_APP_KEY, options);
        Channel channel = pusher.subscribe("react-post");
        channel.bind("react-post", event -> {
            try {
                JSONObject jsonObject=new JSONObject(event.getData());
                counterOfReacts= jsonObject.getInt("likes");


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });
        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {

            }

            @Override
            public void onError(String message, String code, Exception e) {

            }
        }, ConnectionState.ALL);
    }
    private void addRectOnPost(int id,int likes){
        RequestBody likese = RequestBody.create(String.valueOf(likes), MediaType.parse("text/plain"));

        Call<Data<Post>> addRectOnPost=RetrofitClientLaravelData.getInstance().getApiInterface().addRectOnPost(id,likese);
        addRectOnPost.enqueue(new Callback<Data<Post>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Post>> call, @NonNull Response<Data<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "حدث خطأ ما", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<Data<Post>> call, @NonNull Throwable t) {
                Toast.makeText(context, "حدث خطأ ما", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
