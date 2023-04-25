package com.example.projectcollage.activities;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.ChatUserAdapter;
import com.example.projectcollage.database.Database;
import com.example.projectcollage.databinding.ActivityViewMessageUsersBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Message;
import com.example.projectcollage.models.MessageUser;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMessageUsersActivity extends AppCompatActivity {
    public static String data;
    ActivityViewMessageUsersBinding binding;
    ArrayList<Message> messages;
    Bitmap bitmap;
    Uri uriImage;
    File file;
    ChatUserAdapter adapter;
    LinearLayoutManager manager;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewMessageUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        preferences=getSharedPreferences(Constants.DATA,MODE_PRIVATE);
        manager =new LinearLayoutManager(this);
        //Chat Id
        int id=getIntent().getIntExtra(Constants.CHAT_ID,0);
        getMessages(id);
        //Name
        String fullname=getIntent().getStringExtra(Constants.FULL_NAME);
        int senderId=preferences.getInt(Constants.UID,0);
        int receiverId=getIntent().getIntExtra(Constants.RECEIVER_ID,0);
        //Image
        if (getIntent().getStringExtra(Constants.IMAGE)!=null){
            String image=getIntent().getStringExtra(Constants.IMAGE);
            if (!image.isEmpty()){
                Picasso.get().load(Constants.BASE_URL_PATH_USERS+image).into(binding.profileImage);
            }
        }
        binding.senderMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!binding.senderMessage.getText().toString().isEmpty()) {
                    binding.iconSend.setImageResource(R.drawable.ic_send);
                } else {
                    binding.iconSend.setImageResource(R.drawable.ic_mic);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                binding.rvMessageUsers.smoothScrollToPosition(messages.size());


            }
        });
        binding.iconSend.setOnClickListener(v -> {
            String content=binding.senderMessage.getText().toString();
            if (!content.isEmpty()){
                SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss aa", Locale.getDefault());
                String date=dateFormat.format(new Date());
                Message message=new Message(content,date,id,senderId,receiverId);
                if (uriImage!=null){
                    createMessage(message,file);
                }else {
                    sendMessage(message);
                }
                adapter=new ChatUserAdapter(ViewMessageUsersActivity.this,messages);
                messages.add(message);
                binding.rvMessageUsers.setLayoutManager(manager);
                binding.rvMessageUsers.setAdapter(adapter);
                binding.rvMessageUsers.scrollToPosition(messages.size()-1);
                adapter.notifyItemInserted(messages.size()-1);
                binding.rvMessageUsers.setHasFixedSize(true);
                binding.senderMessage.setText("");

            }
        });
        binding.nameOfPerson.setText(fullname);
        binding.iconSelectImage.setOnClickListener(v -> {
            Intent intent=new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);
        });
     }
    ActivityResultLauncher<Intent> someActivityResultLauncher
            = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (!(data == null)) {
                            uriImage = data.getData();
                            try {
                                if (uriImage != null) {
                                    file = new File(getRealPathFromURI(uriImage));
                                }
                                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriImage));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }

    private void sendMessage(Message message){
         Call<Data<Message>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addMessage(message);
            call.enqueue(new Callback<Data<Message>>() {
                @Override
                public void onResponse(@NonNull Call<Data<Message>> call, @NonNull retrofit2.Response<Data<Message>> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(ViewMessageUsersActivity.this, "تم ارسال رساله", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Data<Message>> call, @NonNull Throwable t) {

                }
            });
     }
     private void createMessage(Message message, File file){
         RequestBody content=RequestBody.create(message.getContent(),MediaType.parse("text/plain"));
         RequestBody sentAt=RequestBody.create(message.getSentAt(),MediaType.parse("text/plain"));
         RequestBody imageBody = RequestBody.create(file,MediaType.parse("image/*"));
         MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), imageBody);
         Call<Data<Message>> call= RetrofitClientLaravelData.getInstance().getApiInterface().createMessage(
                    content,
                    sentAt,
                    message.getClassroomId(),
                    message.getChatId(),
                    message.getSender(),
                    message.getReceiver(),
                   imagePart
         );
         call.enqueue(new Callback<Data<Message>>() {
             @Override
             public void onResponse(@NonNull Call<Data<Message>> call, @NonNull retrofit2.Response<Data<Message>> response) {
                 if (response.isSuccessful()){
                     Toast.makeText(ViewMessageUsersActivity.this, "تم ارسال رساله", Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(@NonNull Call<Data<Message>> call, @NonNull Throwable t) {

             }
         });
     }
     private void getMessages(int chatId){
         Call<Data<List<Message>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getMessagesByChatId(chatId);
         call.enqueue(new Callback<Data<List<Message>>>() {
             @Override
             public void onResponse(@NonNull Call<Data<List<Message>>> call, @NonNull Response<Data<List<Message>>> response) {
                 if (response.isSuccessful()){
                     messages= (ArrayList<Message>) response.body().getData();
                     adapter=new ChatUserAdapter(ViewMessageUsersActivity.this,messages);
                     binding.rvMessageUsers.setAdapter(adapter);
                     binding.rvMessageUsers.setLayoutManager(manager);
                     binding.rvMessageUsers.scrollToPosition(messages.size()-1);
                     manager.setStackFromEnd(true);
                 }
             }

             @Override
             public void onFailure(@NonNull Call<Data<List<Message>>> call, @NonNull Throwable t) {
                 Toast.makeText(ViewMessageUsersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

             }
         });

    }
}