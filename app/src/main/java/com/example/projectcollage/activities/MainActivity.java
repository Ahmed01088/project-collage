package com.example.projectcollage.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.GravityCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.ViewPagerAdapter;
import com.example.projectcollage.databinding.ActivityMainBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pusher.pushnotifications.PushNotifications;
import com.squareup.picasso.Picasso;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    ViewPagerAdapter adapter;
    SharedPreferences login;
    int uid;
    SharedPreferences.Editor editor;
    String []nameOfFragments;
    View header;
    String USER_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        Window window=this.getWindow();
        subscribeToTopic();
        login=getSharedPreferences(Constants.DATA,MODE_PRIVATE);
        USER_TYPE=login.getString(Constants.USER_TYPE,"");
        Drawable drawable= AppCompatResources.getDrawable(this,R.drawable.background_gradient);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
        window.setBackgroundDrawable(drawable);
        setSupportActionBar(binding.toolbar);
                initViewPager();
        setHeader();
        binding.navBar.setNavigationItemSelectedListener(this);
        binding.toolbar.setNavigationOnClickListener(view -> binding.drawer.openDrawer(GravityCompat.START));
        binding.toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.notification) {
                Toast.makeText(MainActivity.this, "notification", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.search) {
                View view = findViewById(R.id.search);
                Intent intent = new Intent(this, SearchActivity.class);
                ActivityOptions options = ActivityOptions.makeClipRevealAnimation(view,
                        view.getWidth() / 2, view.getHeight() / 2, 300, 300);
                startActivity(intent, options.toBundle());
                return true;
            }
            return false;
        });
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                return;
            }
            String token = task.getResult();
            Log.d("TAG", "Token==== "+token);
            uid = login.getInt(Constants.UID, 0);
            storeToken(uid,token);
        });



    }

    private void initViewPager() {
        if (USER_TYPE!=null){
            if (USER_TYPE.equals(Constants.STUDENT_AFFAIRS)){
                header= LayoutInflater.from(this).inflate(R.layout.header_student_affairse, null);
                adapter=new ViewPagerAdapter(this,false);
                nameOfFragments= new String[]{"الصفحة الرئيسية", "الدرداشات"};
                binding.navBar.getMenu().removeItem(R.id.quiz);
                binding.navBar.getMenu().removeItem(R.id.study_table);
            }else {
                header= LayoutInflater.from(this).inflate(R.layout.header, null);
                adapter=new ViewPagerAdapter(this,true);
                nameOfFragments=new String[]{"الصفحة الرئيسية","الفصول الدراسية","الدرداشات"};
//              binding.navBar.getMenu().removeItem(R.id.study_table);
            }
            binding.navBar.removeHeaderView(binding.navBar.getHeaderView(0));
            binding.navBar.addHeaderView(header);
        }else {
            header= LayoutInflater.from(this).inflate(R.layout.header, null);
            adapter=new ViewPagerAdapter(this,true);
            nameOfFragments=new String[]{"الصفحة الرئيسية","الفصول الدراسية","الدرداشات"};
        }
        binding.viewTabs.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewTabs, (tab, position) -> {
            tab.setText(nameOfFragments[position]);
        }).attach();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar,menu);
        MenuItem item=menu.findItem(R.id.notification);
        View view=item.getActionView();
        TextView text=view.findViewById(R.id.nuo_badge);
        ImageView notification=view.findViewById(R.id.noti_ic);
        text.setText("49");
        notification.setOnClickListener(view1 -> {
            Intent intent=new Intent(MainActivity.this,NotificationActivity.class);
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(text,text.getWidth()/2,text.getHeight()/2,300,300);
            startActivity(intent,options.toBundle());
            binding.drawer.closeDrawer(GravityCompat.START);
            menu.getItem(0).getActionView().setVisibility(View.GONE);
        });
        // hide item in menu
        return true;
    }
     @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         int itemId = item.getItemId();
         if (itemId == R.id.quiz) {
             Intent intent = new Intent(this, QuizActivity.class);
             ActivityOptions optionsIntent = ActivityOptions.makeClipRevealAnimation(binding.drawer,
                     binding.drawer.getWidth() / 2, binding.drawer.getHeight() / 2, 100, 100);
             startActivity(intent, optionsIntent.toBundle());
             binding.drawer.closeDrawer(GravityCompat.START);
             return true;
         } else if (itemId == R.id.study_table) {
             Intent pdf = new Intent(this, ViewPDFActivity.class);
             ActivityOptions optionsPdf = ActivityOptions.makeClipRevealAnimation(binding.drawer,
                     binding.drawer.getWidth() / 2, binding.drawer.getHeight() / 2, 100, 100);
             startActivity(pdf, optionsPdf.toBundle());
             binding.drawer.closeDrawer(GravityCompat.START);
             return true;
         } else if (itemId == R.id.logout) {
             editor = login.edit();
             editor.clear();
             editor.commit();
             Intent logout = new Intent(this, LoginActivity.class);
             ActivityOptions optionsLogout = ActivityOptions.makeClipRevealAnimation(binding.drawer,
                     binding.drawer.getWidth() / 2, binding.drawer.getHeight() / 2, 100, 100);
             startActivity(logout, optionsLogout.toBundle());
             finish();
             return true;
         } else if (itemId == R.id.about) {
             Intent developer = new Intent(this, AboutDeveloperActivity.class);
             ActivityOptions options = ActivityOptions.makeClipRevealAnimation(binding.drawer,
                     binding.drawer.getWidth() / 2, binding.drawer.getHeight() / 2, 100, 100);
             startActivity(developer, options.toBundle());
             return true;
         }
        return false;
    }
    private void setHeader(){
        View header=binding.navBar.getHeaderView(0);
        TextView name=header.findViewById(R.id.student_name);
        TextView email=header.findViewById(R.id.student_email);
        TextView level=header.findViewById(R.id.student_grade);
        TextView department=header.findViewById(R.id.student_department);
        ImageView image=header.findViewById(R.id.student_pic);
        name.setText(String.format("%s %s", login.getString(Constants.FIRSTNAME, ""), login.getString(Constants.LASTNAME, "")));
        email.setText(String.format(" البريد الالكتروني : %s", login.getString(Constants.EMAIL, "")));
        level.setText(String.format("المستوى : %s", login.getString(Constants.STUDENT_LEVEL, "")));
        department.setText(String.format("القسم : %s", login.getString(Constants.STUDENT_DEPARTMENT, "")));
        Picasso.get()
                .load(Constants.BASE_URL_PATH_USERS+login.getString(Constants.IMAGE,""))
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(image);
    }
private void subscribeToTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void storeToken(int studentId, String token) {
        RequestBody body = RequestBody.create(token, MediaType.parse("text/plain"));
        Call<Data<Student>> call = RetrofitClientLaravelData.getInstance().getApiInterface().updateFcmToken(studentId,body);
        call.enqueue(new Callback<Data<Student>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Student>> call, @NonNull Response<Data<Student>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Token stored failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data<Student>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Token stored failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
