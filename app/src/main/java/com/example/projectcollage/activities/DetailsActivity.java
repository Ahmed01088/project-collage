package com.example.projectcollage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivityDetailsBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Rating;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        String image;
        if (getIntent().getStringExtra(Constants.IMAGE)!=null){
            image=getIntent().getStringExtra(Constants.IMAGE);
        }else {
            image="https://www.flaticon.com/free-icon/hacker_924915?term=avatar&page=1&position=8&origin=search&related_id=924915";
        }
        sharedPreferences=getSharedPreferences(Constants.DATA,MODE_PRIVATE);
        int uid = sharedPreferences.getInt(Constants.UID, 0);
        String fullName=sharedPreferences.getString(Constants.FIRSTNAME,"")
                +" "+sharedPreferences.getString(Constants.LASTNAME,"");
        String email=sharedPreferences.getString(Constants.EMAIL,"");
        String phone=sharedPreferences.getString(Constants.PHONE,"");
        String departmentName=sharedPreferences.getString(Constants.STUDENT_DEPARTMENT,"");
        String level=sharedPreferences.getString(Constants.STUDENT_LEVEL,"");
        if (sharedPreferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[0]) )
        {
            binding.fullNmae.setText(String.format("الطالب  %s", fullName));

        }else if (sharedPreferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[1])){
            binding.fullNmae.setText(String.format("الدكتور  %s", fullName));
        }else {
            binding.fullNmae.setText(fullName);
        }


        binding.email.setText(String.format("البريد الالكتروني : %s", email));
        binding.phoneNumberD.setText(String.format("رقم الهاتف : %s", phone));
        binding.departmentD.setText(String.format("القسم : %s", departmentName));
        binding.levelD.setText(String.format("المستوى : %s", level));
        Picasso.get().load(image).into(binding.profileDetailsPic);
        if (sharedPreferences.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[0])){
            String status=sharedPreferences.getString(Constants.STUDENT_STAT,"");
            binding.statuteD.setText(String.format("الحالة : %s", status));
        }else{
            binding.statuteD.setVisibility(View.GONE);
        }
        binding.rateing.setOnClickListener(view -> {
            binding.layoutRateing.setVisibility(View.VISIBLE);


        });
        binding.sendRate.setOnClickListener(view -> {
            binding.layoutRateing.setVisibility(View.GONE);
            Rating rating=new Rating(uid,binding.textRating.getText().toString());
            addRate(rating);
        });

    }
    private void addRate(Rating rating){
        Call<Data<Rating>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addRating(rating);
        call.enqueue(new Callback<Data<Rating>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Rating>> call, @NonNull Response<Data<Rating>> response) {
                if (response.isSuccessful()){
                        Toast.makeText(DetailsActivity.this, "تم ارسال تقييمك شكرا لمشاركة رايك ...", Toast.LENGTH_SHORT).show();
                        binding.textRating.setText("");
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<Rating>> call, @NonNull Throwable t) {
                Toast.makeText(DetailsActivity.this, "حدث خطأ ما", Toast.LENGTH_SHORT).show();
            }
        });

    }
}