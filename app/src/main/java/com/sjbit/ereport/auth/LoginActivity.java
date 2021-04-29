package com.sjbit.ereport.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sjbit.ereport.R;

public class LoginActivity extends AppCompatActivity {

	//UI Variables.
	private EditText emailEditText, passwordEditText;
	private Button signInButton;
	private TextView registerTextView;

	//Firebase Variables.
	private FirebaseAuth auth = FirebaseAuth.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		emailEditText = findViewById(R.id.email);
		passwordEditText = findViewById(R.id.password);
		signInButton = findViewById(R.id.sign_in);
		registerTextView = findViewById(R.id.register);

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
							//TODO: Sign In User and Update the UI.
						} else {
							passwordEditText.setError("Invalid Credentials");
							passwordEditText.requestFocus();
						}
					});
		});
	}
}