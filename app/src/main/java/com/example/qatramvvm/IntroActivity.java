package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    Button letsGetStarted, next_btn;
    TextView[] dots;
    Animation animation;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.slider);
        letsGetStarted = findViewById(R.id.get_started_btn);
        next_btn = findViewById(R.id.next_btn);
        dotsLayout = findViewById(R.id.dots);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void skip(View view){
        startActivity(new Intent(IntroActivity.this,LoginActivity.class));
        finish();
    }

    public void next(View view){
        viewPager.setCurrentItem(currentPosition + 1);
        if(currentPosition==2){
            next_btn.setVisibility(View.INVISIBLE);
        }
        else {
            next_btn.setVisibility(View.VISIBLE);
        }
    }

    private void addDots(int position){
        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i = 0; i <dots.length ; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(25);

            dotsLayout.addView(dots[i]);
        }
        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPosition = position;
            if(position == 0 || position == 1){
                letsGetStarted.setVisibility(View.INVISIBLE);
                next_btn.setVisibility(View.VISIBLE);
            }
            else{
                animation = AnimationUtils.loadAnimation(IntroActivity.this,R.anim.lets_get_started_animation);
                letsGetStarted.setAnimation(animation);
                next_btn.setVisibility(View.INVISIBLE);
                letsGetStarted.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}