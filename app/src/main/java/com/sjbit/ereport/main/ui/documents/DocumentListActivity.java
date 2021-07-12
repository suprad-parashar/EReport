package com.sjbit.ereport.main.ui.documents;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sjbit.ereport.R;
import com.sjbit.ereport.storage.Prescription;
import com.sjbit.ereport.storage.Report;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class DocumentListActivity extends AppCompatActivity {

	FirebaseDatabase database = FirebaseDatabase.getInstance();
	FirebaseAuth auth = FirebaseAuth.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_document_list);

		String type = getIntent().getStringExtra("type");

		RecyclerView recyclerView = findViewById(R.id.docs_list);
		TextView emptyView = findViewById(R.id.empty_view);
		LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
		recyclerView.setLayoutManager(manager);
		assert type != null;
		if (type.equals("report")) {
			database.getReference().child("users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("blockchain").addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
					ArrayList<Report> reports = new ArrayList<>();
					for (DataSnapshot blockSnapshot : snapshot.getChildren()) {
						if (Objects.requireNonNull(blockSnapshot.child("type").getValue()).toString().equals("REPORT")) {
							Report report = blockSnapshot.child("blockObject").getValue(Report.class);
							reports.add(report);
						}
					}
					if (reports.size() == 0) {
						emptyView.setVisibility(View.VISIBLE);
						emptyView.setText("No Reports Uploaded.");
						recyclerView.setVisibility(View.GONE);
					} else {
						emptyView.setVisibility(View.GONE);
						recyclerView.setVisibility(View.VISIBLE);
						recyclerView.setAdapter(new ReportAdapter(DocumentListActivity.this, reports));
					}
				}

				@Override
				public void onCancelled(@NonNull @NotNull DatabaseError error) {

				}
			});
		} else {
			database.getReference().child("users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("blockchain").addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
					ArrayList<Prescription> prescriptions = new ArrayList<>();
					for (DataSnapshot blockSnapshot : snapshot.getChildren()) {
						if (Objects.requireNonNull(blockSnapshot.child("type").getValue()).toString().equals("PRESCRIPTION")) {
							Prescription prescription = blockSnapshot.child("blockObject").getValue(Prescription.class);
							prescriptions.add(prescription);
						}
					}
					if (prescriptions.size() == 0) {
						emptyView.setVisibility(View.VISIBLE);
						emptyView.setText("No Prescriptions Uploaded.");
						recyclerView.setVisibility(View.GONE);
					} else {
						emptyView.setVisibility(View.GONE);
						recyclerView.setVisibility(View.VISIBLE);
						recyclerView.setAdapter(new PrescriptionAdapter(DocumentListActivity.this, prescriptions));
					}
				}

				@Override
				public void onCancelled(@NonNull @NotNull DatabaseError error) {

				}
			});
		}
	}
}