package com.sjbit.ereport.main.ui.documents;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sjbit.ereport.R;
import com.sjbit.ereport.storage.Report;

public class ReportDisplayActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_display);

		Report report = (Report) getIntent().getSerializableExtra("obj");
		String dateString = getIntent().getStringExtra("date");
		if (dateString == null)
			dateString = "";

		TextView hospital, test, doctor, date, data;
		hospital = findViewById(R.id.hospital_name);
		test = findViewById(R.id.test_name);
		doctor = findViewById(R.id.doctor_name);
		date = findViewById(R.id.date);
		data = findViewById(R.id.data);

		assert report != null;
		hospital.setText(report.getHospitalName());
		test.setText(report.getTestName());
		doctor.setText(report.getDoctorName());
		date.setText(dateString);
		data.setText(report.getData());
	}
}