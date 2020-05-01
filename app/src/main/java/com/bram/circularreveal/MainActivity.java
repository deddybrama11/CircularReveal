package com.bram.circularreveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bram.circularreveal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    ImageView bgone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        bgone = (ImageView) findViewById(R.id.bgone);
        bgone.animate().scaleX(1).scaleY(1).setDuration(7000);

    }

    public void load(View view){
        animateButtonWidth();
        fadeOutTextAndSetProgressDialog();
        nextAction();
    }
    private void animateButtonWidth(){
        ValueAnimator anim = ValueAnimator.ofInt(mainBinding.signInBtn.getMeasuredWidth(),getFinalWidth());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mainBinding.signInBtn.getLayoutParams();
                layoutParams.width = value;
                mainBinding.signInBtn.requestLayout();
            }
        });
        anim.setDuration(250);
        anim.start();
    }
    private void fadeOutTextAndSetProgressDialog(){
        mainBinding.signInBtn.animate().alpha(0f).setDuration(2000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }
        }).start();
    }
    private void nextAction(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealButton();
                delayedStartNextActivity();
            }
        },500);
    }
    private void revealButton(){
        mainBinding.signInBtn.setElevation(0f);
        mainBinding.revealView.setVisibility(View.VISIBLE);
        int x = mainBinding.revealView.getWidth();
        int y = mainBinding.revealView.getHeight();

        int startX = (int) (getFinalWidth()/2+mainBinding.signInBtn.getX());
        int startY = (int) (getFinalWidth()/2+mainBinding.signInBtn.getY());

        float radius = Math.max(x,y)*1.2f;
        Animator reveal= ViewAnimationUtils.createCircularReveal(mainBinding.revealView,startX,startY,getFinalWidth(),radius);
        reveal.setDuration(500);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });
        reveal.start();
    }
    private void delayedStartNextActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        }, 100);
    }

    private int getFinalWidth(){
        return (int) getResources().getDimension(R.dimen.get_width);
    }
}
