package com.example.projectcollage.customView;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.projectcollage.R;
import com.example.projectcollage.activities.DetailsActivity;
import com.example.projectcollage.activities.ShowImageActivity;
import com.example.projectcollage.activities.ViewMessageUsersActivity;
import com.example.projectcollage.utiltis.Constants;
import com.squareup.picasso.Picasso;

public class CustomDialog extends Dialog {
    public String imageData;
    public String uid;
    public String name;
    public int chatId;
    Context context;
    ImageView chat,details,image;
    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_show_iamge);
        chat=findViewById(R.id.chat_icon);
        details=findViewById(R.id.details_icon);
        details.setOnClickListener(view -> {
            Intent intent=new Intent(context, DetailsActivity.class);
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(image,image.getWidth()/2,image.getHeight()/2,100,100);
            context.startActivity(intent,options.toBundle());
        });
        image=findViewById(R.id.imageCardShow);
        Picasso.get().load(Constants.BASE_URL_PATH_USERS+imageData).into(image);
        image.setOnClickListener(view -> {
            Intent intent=new Intent(context,ShowImageActivity.class);
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(image,image.getWidth()/2,image.getHeight()/2,100,100);
            context.startActivity(intent,options.toBundle());
        });
        chat.setOnClickListener(view -> {
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(image,image.getWidth()/2,image.getHeight()/2,100,100);
            Intent intent=new Intent(context, ViewMessageUsersActivity.class);
            intent.putExtra(Constants.UID, uid);
            intent.putExtra("name",name);
            ViewMessageUsersActivity.data=imageData;
            context.startActivity(intent,options.toBundle());});
            dismiss();
            cancel();
    }

}
