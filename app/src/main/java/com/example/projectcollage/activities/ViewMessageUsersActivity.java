package com.example.projectcollage.activities;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.ChatUserAdapter;
import com.example.projectcollage.database.Database;
import com.example.projectcollage.databinding.ActivityViewMessageUsersBinding;
import com.example.projectcollage.models.MessageUser;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ViewMessageUsersActivity extends AppCompatActivity {
    public static Bitmap data;
    ActivityViewMessageUsersBinding binding;
    Database database;
    ArrayList<MessageUser> messageUsers;
    Bitmap bitmap;
    Uri uriImage;
    String uid;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewMessageUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayoutManager manager =new LinearLayoutManager(this);
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        uid=getIntent().getStringExtra("uid");
        name=getIntent().getStringExtra("name");
        database=new Database(this);
        binding.profileImage.setImageBitmap(data);
        SQLiteDatabase db=database.getWritableDatabase();
        database.createTableChatUsers(db,uid);
        binding.nameOfPerson.setText(name);
//        binding.profileImage.setImageURI(uriImageProfile);
        if (database.getAllMessageUser(uid)==null){
            messageUsers=new ArrayList<>();
        }else {
            messageUsers=database.getAllMessageUser(uid);
        }
        ChatUserAdapter adapter=new ChatUserAdapter(this,messageUsers,uid);
        binding.rvMessageUsers.setLayoutManager(manager);
        binding.rvMessageUsers.setAdapter(adapter);
        binding.senderMessage.setImeOptions(EditorInfo.IME_ACTION_SEND);
        manager.setStackFromEnd(true);
        binding.senderMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Objects.requireNonNull(binding.senderMessage.getText()).toString().isEmpty()){
                    binding.iconSend.setImageResource(R.drawable.ic_mic);

                }else {
                    binding.iconSend.setImageResource(R.drawable.ic_send);
                    String firstLetter=binding.senderMessage.getText().toString().substring(0,1);
                    String lastLetter=binding.senderMessage.getText().toString().substring(binding.senderMessage.getText().toString().length()-1);
                    if (firstLetter.equals(lastLetter) && firstLetter.equals("$")){
                        binding.senderMessage.setTextColor(Color.RED);
                    }else {
                        binding.senderMessage.setTextColor(Color.BLACK);

                    }
                }
                manager.setStackFromEnd(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.rvMessageUsers.smoothScrollToPosition(messageUsers.size());

            }
        });
        binding.iconSelectImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);
        });
        binding.profileImage.setOnClickListener(view -> {
            Intent intent=new Intent(this,ShowImageActivity.class);
            ShowImageActivity.DATA=data;
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.rvMessageUsers,
                    binding.rvMessageUsers.getWidth()/2,binding.rvMessageUsers.getHeight()/2,300,300);
            startActivity(intent,options.toBundle());


            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        });
        binding.iconSend.setOnClickListener(view -> {
            String message= Objects.requireNonNull(binding.senderMessage.getText()).toString();
            if (!message.isEmpty()){
                Date date=new Date();
                SimpleDateFormat format=new SimpleDateFormat("HH:mm aa", Locale.ENGLISH);
                String time=format.format(date);
                database.insertMessageUser(new MessageUser(message, time, bitmap),uid);
                messageUsers.add(new MessageUser(message,time,uriImage));
                bitmap=null;
                adapter.notifyItemInserted(messageUsers.size());
                binding.senderMessage.setText("");
            }

        });
        binding.rvMessageUsers.setLayoutManager(manager);
        binding.btnBack.setOnClickListener(view -> finish());

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher
            =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data=result.getData();
                        if (!(data == null)) {
                            uriImage = data.getData();
                            try {
                                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriImage));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }                }
            });

}