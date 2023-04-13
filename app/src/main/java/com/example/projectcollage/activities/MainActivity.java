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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.ViewPagerAdapter;
import com.example.projectcollage.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    ViewPagerAdapter adapter;
    String username;
    SharedPreferences login;
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
        login=getSharedPreferences("login",MODE_PRIVATE);
        USER_TYPE=login.getString("userType","");
        Drawable drawable= AppCompatResources.getDrawable(this,R.drawable.background_gradient);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
        window.setBackgroundDrawable(drawable);
        setSupportActionBar(binding.toolbar);
        header= LayoutInflater.from(this).inflate(R.layout.header_student_affairse, null);
        initViewPager();
        setHeader();
        binding.navBar.setNavigationItemSelectedListener(this);
        binding.toolbar.setNavigationOnClickListener(view -> binding.drawer.openDrawer(GravityCompat.START));
        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.notification:
                    Toast.makeText(MainActivity.this, "notification", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.search:
                    View view=findViewById(R.id.search);
                    Intent intent=new Intent(this,SearchActivity.class);
                    ActivityOptions options= ActivityOptions.makeClipRevealAnimation(view,
                            view.getWidth()/2,view.getHeight()/2,300,300);
                    startActivity(intent,options.toBundle());
                    return true;
            }
            return false;
        });


    }

    private void initViewPager() {
        if (USER_TYPE!=null){
            if (USER_TYPE.equals("Student Affairs")){
                adapter=new ViewPagerAdapter(this,false);
                nameOfFragments= new String[]{"الصفحة الرئيسية", "الدرداشات"};
                binding.navBar.getMenu().removeItem(R.id.quiz);
                binding.navBar.getMenu().removeItem(R.id.study_table);
                binding.navBar.removeHeaderView(binding.navBar.getHeaderView(0));
                binding.navBar.addHeaderView(header);
            }else {
                adapter=new ViewPagerAdapter(this,true);
                nameOfFragments=new String[]{"الصفحة الرئيسية","الفصول الدراسية","الدرداشات"};
//              binding.navBar.getMenu().removeItem(R.id.study_table);
            }
        }else {
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
        });
        return true;
    }
     @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.quiz:
                Intent intent=new Intent(this,QuizActivity.class);
                ActivityOptions optionsIntent= ActivityOptions.makeClipRevealAnimation(binding.drawer,
                        binding.drawer.getWidth()/2,binding.drawer.getHeight()/2,100,100);
                startActivity(intent,optionsIntent.toBundle());
                binding.drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.study_table:
                Intent pdf=new Intent(this,ViewPDFActivity.class);
                ActivityOptions optionsPdf= ActivityOptions.makeClipRevealAnimation(binding.drawer,
                        binding.drawer.getWidth()/2,binding.drawer.getHeight()/2,100,100);
                startActivity(pdf,optionsPdf.toBundle());
                binding.drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.logout:
                editor=login.edit();
                editor.clear();
                editor.commit();
                Intent logout=new Intent(this,LoginActivity.class);
                ActivityOptions optionsLogout= ActivityOptions.makeClipRevealAnimation(binding.drawer,
                        binding.drawer.getWidth()/2,binding.drawer.getHeight()/2,100,100);
                startActivity(logout,optionsLogout.toBundle());

                finish();
                return true;
            case R.id.about:
                Intent developer=new Intent(this,AboutDeveloperActivity.class);
                ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.drawer,
                        binding.drawer.getWidth()/2,binding.drawer.getHeight()/2,100,100);
                startActivity(developer,options.toBundle());

                return true;

        }
        return false;
    }
    private void setHeader(){
        TextView name=header.findViewById(R.id.student_name);
        TextView email=header.findViewById(R.id.student_email);
        TextView level=header.findViewById(R.id.student_grade);
        TextView department=header.findViewById(R.id.student_department);


        ImageView image=header.findViewById(R.id.student_pic);
        name.setText(login.getString("firstName","")+" "+login.getString("lastName",""));
        email.setText(login.getString("email",""));


    }
}
