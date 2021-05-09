package com.sjbit.ereport.auth;

import androidx.appcompat.app.AppCompatActivity;
import com.sjbit.ereport.R;
import com.sjbit.ereport.main.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splashscreen extends AppCompatActivity {

    private static int SPLASH_TIMER=2500;
    //variables
    ImageView backgroundImage;

    //Animations
    Animation sideAnim;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        //Hooks
        backgroundImage=findViewById(R.id.splash_background_image);

        //Animations
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);

        //set animations on elements
        backgroundImage.setAnimation(sideAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splashscreen.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);
    }
}