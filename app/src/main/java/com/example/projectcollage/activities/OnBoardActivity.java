package com.example.projectcollage.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.text.HtmlCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.projectcollage.R;
import com.example.projectcollage.adapters.ViewPagerOnBoardingAdapter;
import com.example.projectcollage.databinding.ActivityOnBoradBinding;
public class OnBoardActivity extends AppCompatActivity {
    ActivityOnBoradBinding binding;
    private ViewPagerOnBoardingAdapter adapter;
    private TextView []dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOnBoradBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.statesOnBoarding));
        getWindow().setStatusBarColor(getColor(R.color.statesOnBoarding));
  /*      Window window=this.getWindow();
//        isFirstTime();
//        Drawable drawable= AppCompatResources.getDrawable(this,R.drawable.background_gradient);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(getColor(android.R.color.transparent));
//        window.setBackgroundDrawable(drawable);

*/
        adapter=new ViewPagerOnBoardingAdapter(this);
        addDots(0);
        new Handler().postDelayed(() -> binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem()+1,true), 5000);
        binding.viewPager.addOnPageChangeListener(listener);
        binding.viewPager.setAdapter(adapter);
        binding.btnNext.setOnClickListener(view -> {
            if (binding.btnNext.getText().toString().equals("التالي")){
                binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem()+1,true);
            }else {

                Intent intent=new Intent(this,LoginActivity.class);
                ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.viewPager,binding.viewPager.getWidth()/2,
                        binding.viewPager.getHeight()/2,300,300);
                startActivity(intent,options.toBundle());
                finish();
            }
        });
        binding.btnSkip.setOnClickListener(view -> {
            Intent intent=new Intent(this,LoginActivity.class);
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.main,binding.main.getWidth()/2,
                    binding.main.getHeight()/2,300,300);
            startActivity(intent,options.toBundle());
        });
    }
    private void addDots(int position){
        binding.dotsLayout.removeAllViews();
        dots=new TextView[3];
        for (int i = 0; i < dots.length; i++) {
            dots[i]=new TextView(this);
            dots[i].setText(HtmlCompat.fromHtml("&#8226", HtmlCompat.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dots[i].setGravity(View.TEXT_ALIGNMENT_CENTER);
            dots[i].setTextColor(getColor(R.color.main_bar));
            binding.dotsLayout.addView(dots[i]);

        }
        if (dots.length>0){
            dots[position].setTextColor(getColor(R.color.white));
            dots[position].setTextSize(40);
        }
    }
    private final ViewPager.OnPageChangeListener listener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            if (position==0){
                binding.btnSkip.setVisibility(View.VISIBLE);
                binding.btnSkip.setEnabled(true);
                binding.btnNext.setText("التالي");
                new Handler().postDelayed(() -> binding.viewPager.setCurrentItem(
                        binding.viewPager.getCurrentItem()+1,true),
                        5000);

            }else if (position==1){
                binding.btnSkip.setVisibility(View.GONE);
                binding.btnSkip.setEnabled(false);
                binding.btnNext.setText("التالي");
                new Handler().postDelayed(() ->
                        binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem()+1,true),
                        5000);

            }else {
                binding.btnSkip.setVisibility(View.GONE);
                binding.btnSkip.setEnabled(false);
                binding.btnNext.setText("انهاء");
                new Handler().postDelayed(() ->
                        binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem()-2,true),
                        5000);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    void isFirstTime(){
        SharedPreferences preferences=getSharedPreferences("onBoard", MODE_PRIVATE);
        boolean isFirstTime=preferences.getBoolean("isFirstTime", true);
        if (isFirstTime){
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("isFirstTime",false);
            editor.apply();
            startActivity(new Intent(this, OnBoardActivity.class));
        }else {
            startActivity(new Intent(this,SplashActivity.class));

        }
        finish();
    }


}