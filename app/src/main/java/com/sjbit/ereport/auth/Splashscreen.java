package com.sjbit.ereport.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.sjbit.ereport.R;

public class Splashscreen extends AppCompatActivity {

	private static final int SPLASH_TIMER = 4000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splashscreen);

		//Hooks
		//Variables
		ImageView backgroundImage = findViewById(R.id.splash_background_image);

		//Animations
		//Animations
		Animation sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);

		//set animations on elements
		backgroundImage.setAnimation(sideAnim);

		new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splashscreen.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIMER);
	}
}