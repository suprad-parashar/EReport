package com.sjbit.ereport.main.ui.documents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.sjbit.ereport.R;
import com.sjbit.ereport.storage.Prescription;

public class PrescriptionDisplayActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prescription_display);

		Prescription prescription = (Prescription) getIntent().getSerializableExtra("obj");
		assert prescription != null;
		String doctor = getIntent().getStringExtra("dname");
		String dateString = getIntent().getStringExtra("date");
		if (dateString == null)
			dateString = "";

		TextView doctorName = findViewById(R.id.doctor_name);
		TextView dateView = findViewById(R.id.date);

		doctorName.setText(doctor);
		dateView.setText(dateString);
		RecyclerView recyclerView = findViewById(R.id.medicines);
		LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
		recyclerView.setLayoutManager(manager);
		recyclerView.setAdapter(new MedicineAdapter(prescription.getMedicines()));
	}
}