package com.sjbit.ereport.main.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sjbit.ereport.R;
import com.sjbit.ereport.auth.LoginActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;

/**
 * Handles the User's Profile.
 */
public class ProfileFragment extends Fragment {

	private TextView hospitalsCountTextView, prescriptionCountTextView, reportCountTextView;
	private TextView profileSettings, notifications, about, logout;
	private TextView userName, userEmail;

	private final FirebaseDatabase database = FirebaseDatabase.getInstance();
	private final FirebaseAuth auth = FirebaseAuth.getInstance();

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_profile, container, false);
	}

	@Override
	public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		hospitalsCountTextView = view.findViewById(R.id.hospitals_visited);
		prescriptionCountTextView = view.findViewById(R.id.no_of_prescriptions);
		reportCountTextView = view.findViewById(R.id.no_of_reports);

		userName = view.findViewById(R.id.Username);
		userEmail = view.findViewById(R.id.email);

//		profileSettings = view.findViewById(R.id.profile_settings_button);
//		notifications = view.findViewById(R.id.notifications_button);
		about = view.findViewById(R.id.notifications_button);
		logout = view.findViewById(R.id.logout_button);

		FirebaseUser user = auth.getCurrentUser();
		assert user != null;
		userName.setText(user.getDisplayName());
		userEmail.setText(user.getEmail());

		about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
				builder.setTitle("About")
						.setMessage("This application is Created by Sandeep, Srivalli and Suprad of ISE B for the Final Year Project.")
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								dialogInterface.dismiss();
							}
						});
				builder.create().show();
			}
		});

//		profileSettings.setOnClickListener(v -> {
//			//TODO: Profile Settings
//		});
//		notifications.setOnClickListener(v -> {
//			//TODO: Notifications
//		});
//		about.setOnClickListener(v -> {
//			//TODO: About
//		});
		logout.setOnClickListener(v -> {
			auth.signOut();
			startActivity(new Intent(requireActivity(), LoginActivity.class));
			requireActivity().finish();
		});

		DatabaseReference reference = database.getReference().child("users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("blockchain");
		reference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
				HashSet<String> hospitals = new HashSet<>();
				int reportCount = 0, prescriptionCount = 0;

				for (DataSnapshot blockSnapshot: snapshot.getChildren()) {
					if (Objects.requireNonNull(blockSnapshot.child("type").getValue()).toString().equals("REPORT")) {
						reportCount++;
						hospitals.add(Objects.requireNonNull(blockSnapshot.child("blockObject").child("hospitalName").getValue()).toString());
					} else {
						prescriptionCount++;
					}
				}
				hospitalsCountTextView.setText(String.valueOf(hospitals.size()));
				reportCountTextView.setText(String.valueOf(reportCount));
				prescriptionCountTextView.setText(String.valueOf(prescriptionCount));
			}

			@Override
			public void onCancelled(@NonNull @NotNull DatabaseError error) {

			}
		});
	}
}