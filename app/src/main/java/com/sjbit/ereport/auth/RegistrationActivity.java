package com.sjbit.ereport.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.sjbit.ereport.R;

public class RegistrationActivity extends AppCompatActivity {

	//UI Variables
	private EditText nameEditText, emailEditText;
	private TextInputEditText passwordEditText, confirmPasswordEditText;
	private Button signUpButton;
	private TextView signInTextView;

	//Firebase Variables
	private FirebaseAuth auth = FirebaseAuth.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		nameEditText = findViewById(R.id.name);
		emailEditText = findViewById(R.id.email);
		passwordEditText = findViewById(R.id.password);
		confirmPasswordEditText = findViewById(R.id.confirm_password);
		signUpButton = findViewById(R.id.sign_up);
		signInTextView = findViewById(R.id.sign_in);

		signInTextView.setOnClickListener(view -> {
			startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
		});

		signUpButton.setOnClickListener(view -> {
			String name = nameEditText.getText().toString();
			String email = emailEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			String confirmPassword = confirmPasswordEditText.getText().toString();
			if (name.equals("")) {
				nameEditText.setError("Name cannot be empty");
				nameEditText.requestFocus();
			} else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
				emailEditText.setError("Email is not valid");
				emailEditText.requestFocus();
			} else if (password.equals("")) {
				passwordEditText.setError("Password cannot be empty");
				passwordEditText.requestFocus();
			} else if (password.length() < 8) {
				passwordEditText.setError("Length must be at least 8 characters.");
				passwordEditText.requestFocus();
			} else {
				boolean hasUpperCase = false, hasLowerCase = false, hasNumber = false, hasSpecial = false;
				for (int i = 0; i < password.length(); i++) {
					char c = password.charAt(i);
					if (Character.isLowerCase(c))
						hasLowerCase = true;
					else if (Character.isUpperCase(c))
						hasUpperCase = true;
					else if (Character.isDigit(c))
						hasNumber = true;
					else
						hasSpecial = true;
				}
				if (!hasLowerCase || !hasUpperCase || !hasNumber || !hasSpecial) {
					passwordEditText.setError("Password must have at least one of Uppercase, Lowercase, Digit and Special Characters");
					passwordEditText.requestFocus();
				} else if (!password.equals(confirmPassword)) {
					confirmPasswordEditText.setError("Passwords do not match");
					confirmPasswordEditText.requestFocus();
				} else {
					//TODO: Register User.
					auth.createUserWithEmailAndPassword(email, password)
							.addOnCompleteListener(task -> {
								if (task.isSuccessful()) {
									//TODO: Update UI
								} else {
									Toast.makeText(RegistrationActivity.this, "There was an error during Registration", Toast.LENGTH_LONG).show();
								}
							});
				}
			}
		});
	}
}