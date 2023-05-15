package com.example.projectcollage.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.animation.AnimatorSet;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.ActivitySplashBinding;
public class SplashActivity extends AppCompatActivity {
     ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setNavigationBarColor(getColor(R.color.main_bar));
        Window window=this.getWindow();
        Drawable drawable= AppCompatResources.getDrawable(this,R.drawable.background_gradient);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
        window.setNavigationBarColor(getColor(R.color.statesOnBoarding));
        window.setBackgroundDrawable(drawable);
        new Handler().postDelayed(() -> {
            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
            ActivityOptions options= ActivityOptions.makeClipRevealAnimation(binding.animationView,binding.animationView.getWidth()/2,
                    binding.animationView.getHeight()/2,300,300);
            startActivity(intent,options.toBundle());
            finish();
        }, 2000);


    }

}