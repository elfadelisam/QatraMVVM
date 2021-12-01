package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView logo;
    TextView app_name, slogan;
    SharedPreferences IntroSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        init();

        logo.setAnimation(topAnim);
        app_name.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);


        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2500);

                    IntroSlider = getSharedPreferences("IntroSlider",MODE_PRIVATE);
                    boolean isFirstTime = IntroSlider.getBoolean("firstTime",true);

                    if(isFirstTime){
                        SharedPreferences.Editor editor = IntroSlider.edit();
                        editor.putBoolean("firstTime", false);
                        editor.apply();

                        Intent intent=new Intent(SplashActivity.this,IntroActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void init(){
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        logo = findViewById(R.id.logo);
        app_name = findViewById(R.id.app_name);
        slogan = findViewById(R.id.slogan);
    }
}