package com.ajizamotors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    //splash variable
    private static final int SPLASH_SCREEN = 5000;
    Animation Top,Bottom;
    ImageView TitleImage;
    TextView Title,Slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);
        //ids
        TitleImage = findViewById(R.id.SplashImage);
        Title = findViewById(R.id.SplashTitle);
        Slogan = findViewById(R.id.SplashSlogan);

        //animation
        Top = AnimationUtils.loadAnimation(this,R.anim.topanimation);
        Bottom = AnimationUtils.loadAnimation(this,R.anim.bottomanimation);

        //set animation
        TitleImage.setAnimation(Top);
        Title.setAnimation(Bottom);
        Slogan.setAnimation(Bottom);

        //Go to main page
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,FirstPage.class));
                finish();
            }
        },SPLASH_SCREEN);
    }
}