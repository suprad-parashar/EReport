package com.sjbit.ereport.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sjbit.ereport.R;
import com.sjbit.ereport.main.HomeActivity;

public class LoginActivity extends AppCompatActivity {

	//UI Variables.
	private EditText emailEditText, passwordEditText;

	//Firebase Variables.
	private final FirebaseAuth auth = FirebaseAuth.getInstance();

	@Override
	protected void onStart() {
		super.onStart();
		if (auth.getCurrentUser() != null) {
			startActivity(new Intent(LoginActivity.this, HomeActivity.class));
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

//		FirebaseApp.initializeApp(this);
//		auth = FirebaseAuth.getInstance();

		emailEditText = findViewById(R.id.email);
		passwordEditText = findViewById(R.id.password);
		Button signInButton = findViewById(R.id.sign_in);
		TextView registerTextView = findViewById(R.id.register);

		registerTextView.setOnClickListener(view -> {
			Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
			startActivity(intent);
		});

		signInButton.setOnClickListener(view -> {
			String email = emailEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			auth.signInWithEmailAndPassword(email, password)
					.addOnCompleteListener(task -> {
						if (task.isSuccessful()) {
							startActivity(new Intent(LoginActivity.this, HomeActivity.class));
							finish();
						} else {
							passwordEditText.setError("Invalid Credentials");
							passwordEditText.requestFocus();
						}
					});
		});
	}
}