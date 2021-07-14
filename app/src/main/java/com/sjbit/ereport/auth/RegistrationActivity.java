package com.sjbit.ereport.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.sjbit.ereport.R;
import com.sjbit.ereport.main.HomeActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Handles the Registration of the User.
 */
public class RegistrationActivity extends AppCompatActivity {

	//UI Variables
	private EditText nameEditText, emailEditText;
	private TextInputEditText passwordEditText, confirmPasswordEditText;

	//Firebase Variables
	private final FirebaseAuth auth = FirebaseAuth.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		//Find Views.
		nameEditText = findViewById(R.id.name);
		emailEditText = findViewById(R.id.email);
		passwordEditText = findViewById(R.id.password);
		confirmPasswordEditText = findViewById(R.id.confirm_password);
		Button signUpButton = findViewById(R.id.sign_up);
		TextView signInTextView = findViewById(R.id.sign_in);

		//Redirect to Login Page.
		signInTextView.setOnClickListener(view -> startActivity(new Intent(RegistrationActivity.this, LoginActivity.class)));

		//Register User
		signUpButton.setOnClickListener(view -> {
			//Get Data
			String name = nameEditText.getText().toString();
			String email = emailEditText.getText().toString();
			String password = Objects.requireNonNull(passwordEditText.getText()).toString();
			String confirmPassword = Objects.requireNonNull(confirmPasswordEditText.getText()).toString();

			//Validate Data
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
					//Register User
					auth.createUserWithEmailAndPassword(email, password)
							.addOnCompleteListener(task -> {
								if (task.isSuccessful()) {
									FirebaseUser user = auth.getCurrentUser();
									UserProfileChangeRequest profileUpdates =  new UserProfileChangeRequest.Builder().setDisplayName(name).build();
									user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
										@Override
										public void onComplete(@NonNull @NotNull Task<Void> task) {
											if(task.isSuccessful()){
												Log.d("Success","User Profile Update");
												startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
											}
											else{
												Log.d("Failure","User Profile Failed");
											}
										}
									});
									finish();
								} else {
									Toast.makeText(RegistrationActivity.this, "There was an error during Registration", Toast.LENGTH_LONG).show();
								}
							});
				}
			}
		});
	}
}