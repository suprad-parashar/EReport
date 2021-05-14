package com.sjbit.ereport.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sjbit.ereport.R;
import com.sjbit.ereport.main.HomeActivity;

/**
 * This class handles the Login Aspect of the Application.
 */
public class LoginActivity extends AppCompatActivity {

	//UI Variables.
	private EditText emailEditText;
	private EditText passwordEditText;

	//Firebase Variables.
	private final FirebaseAuth auth = FirebaseAuth.getInstance();

	/**
	 * If User is already logged in, redirect to HomeActivity.
	 */
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

		//Find Views
		emailEditText = findViewById(R.id.email);
		passwordEditText = findViewById(R.id.password);
		TextView forgotPassword = findViewById(R.id.forgot_password);
		Button signInButton = findViewById(R.id.sign_in);
		TextView registerTextView = findViewById(R.id.register);

		//Handle User Registration
		registerTextView.setOnClickListener(view -> {
			Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
			startActivity(intent);
		});

		//Handle User Sign In.
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

		//Handle Forgot Password Clicks.
		forgotPassword.setOnClickListener(v -> {
			final String email = emailEditText.getText().toString().trim();
			//Check if email address is valid.
			if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
				emailEditText.setError("Invalid Email Address");
				emailEditText.requestFocus();
			} else {
				auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
					if (task.isSuccessful())
						Toast.makeText(LoginActivity.this, "Reset Link sent to mail.", Toast.LENGTH_LONG).show();
					else {
						emailEditText.setError("Email Address not Registered! Register now.");
						emailEditText.requestFocus();
					}
				});
			}
		});
	}
}