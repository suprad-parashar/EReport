package com.sjbit.ereport.main.ui.upload;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sjbit.ereport.R;
import com.sjbit.ereport.main.HomeActivity;
import com.sjbit.ereport.storage.Block;
import com.sjbit.ereport.storage.Report;
import com.sjbit.ereport.storage.Type;
import com.sjbit.ereport.storage.Uploader;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Handles the Report Upload Part of the Application.
 */
public class ReportUploadActivity extends AppCompatActivity {

	//UI Variables.
	private EditText testNameEditText;
	private EditText dataEditText;
	private EditText hospitalNameEditText;

	//Data Variables
	private static final int RC_CHOOSE_IMAGE = 724;
	private EditText referredByEditText;
	private EditText dateOfReportEditText;
	private Date dateOfReport = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_upload);

		//Find Views
		Button uploadImage = findViewById(R.id.upload_image);
		Button openCamera = findViewById(R.id.open_camera);
		testNameEditText = findViewById(R.id.test_name);
		hospitalNameEditText = findViewById(R.id.hospital_name);
		referredByEditText = findViewById(R.id.referred_by);
		dateOfReportEditText = findViewById(R.id.date_of_report);
		dataEditText = findViewById(R.id.data);

		final Calendar newCalendar = Calendar.getInstance();
		final DatePickerDialog StartTime = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
			Calendar newDate = Calendar.getInstance();
			newDate.set(year, monthOfYear, dayOfMonth);
			dateOfReport = newDate.getTime();
			String dateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
			dateOfReportEditText.setText(dateString);
		}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		dateOfReportEditText.setOnClickListener(view -> {
			StartTime.show();
		});

		//Handle Image Uploads from Gallery
		uploadImage.setOnClickListener(v -> Dexter.withContext(this)
				.withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
				.withListener(new PermissionListener() {
					@Override
					public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(intent, RC_CHOOSE_IMAGE);
					}

					@Override
					public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
						AlertDialog.Builder builder = new AlertDialog.Builder(ReportUploadActivity.this);
						builder.setTitle("Permission Required")
								.setMessage("Storage permission is required to upload an image.")
								.setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
								.setCancelable(true);
						builder.create().show();
					}

					@Override
					public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
						permissionToken.continuePermissionRequest();
					}
				}).check());

		//Handle Camera Intent
		openCamera.setOnClickListener(v -> {
			startActivity(new Intent(ReportUploadActivity.this, HomeActivity.class));
			HomeActivity.navView.setSelectedItemId(R.id.navigation_home);
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.save_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
		if (item.getItemId() == R.id.save_button) {
			String testName = testNameEditText.getText().toString();
			String hospitalName = hospitalNameEditText.getText().toString();
			String referredBy = referredByEditText.getText().toString();
			if (referredBy.equals(""))
				referredBy = "Self";
			String data = dataEditText.getText().toString();
			if (testName.equals("")) {
				testNameEditText.setError("Please Enter Test Name");
				testNameEditText.requestFocus();
			} else if (hospitalName.equals("")) {
				hospitalNameEditText.setError("Please Enter Hospital Name");
				hospitalNameEditText.requestFocus();
			} else if (data.equals("")) {
				dataEditText.setError("Data Cannot Be Empty");
				dataEditText.requestFocus();
			} else {
				Report report = new Report(dateOfReport, referredBy, testName, hospitalName, data);
				Block block = new Block(Type.REPORT, report);
				Uploader.addBlock(block);
				Toast.makeText(ReportUploadActivity.this, "Report Saved", Toast.LENGTH_LONG).show();
				startActivity(new Intent(ReportUploadActivity.this, HomeActivity.class));
				finish();
			}
		}
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RC_CHOOSE_IMAGE && resultCode == RESULT_OK && data != null) {
			//Get Image.
			Uri uri = data.getData();
			assert uri != null;

			//Show ML Dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Get Data?")
					.setMessage("Do you want eReports to read the data from the image?")
					.setPositiveButton("YES", (dialog, which) -> fetchDataFromImage(uri))
					.setNegativeButton("NO", (dialog, which) -> dialog.dismiss())
					.setCancelable(false);
			builder.create().show();
		}
	}

	/**
	 * Finds and populates the Data from the Image URI to the respective EditTexts.
	 *
	 * @param uri The URI of the image whose data is to be processed.
	 */
	private void fetchDataFromImage(Uri uri) {
		//Variables.
		InputImage image;

		//Get Data
		try {
			image = InputImage.fromFilePath(this, uri);
			TextRecognizer recognizer = TextRecognition.getClient();
			recognizer.process(image).addOnSuccessListener(visionText -> {
				boolean isTitleFound = false;
				String title;
				StringBuilder data = new StringBuilder();
				for (Text.TextBlock block : visionText.getTextBlocks()) {
					String blockText = block.getText();
					Log.e("BLOCK", blockText);
					if (block.getLines().size() == 1 && !isTitleFound) {
						title = block.getLines().get(0).getText();
						testNameEditText.setText(title);
						isTitleFound = true;
					} else {
						data.append(block.getText()).append("\n");
					}
				}
				dataEditText.setText(data);
			}).addOnFailureListener(e -> Toast.makeText(this, "An error occurred while reading the image", Toast.LENGTH_SHORT).show());
		} catch (IOException e) {
			Toast.makeText(this, "ML IS NOT WORKING", Toast.LENGTH_SHORT).show();
		}
	}
}