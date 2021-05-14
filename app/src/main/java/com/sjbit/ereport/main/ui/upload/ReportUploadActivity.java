package com.sjbit.ereport.main.ui.upload;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.IOException;

public class ReportUploadActivity extends AppCompatActivity {

	private Button uploadImage, openCamera;
	//	private ImageView imageView;
	private EditText testNameEditText, hospitalNameEditText, referredByEditText, dateOfReportEditText, dataEditText;

	private static final int RC_CHOOSE_IMAGE = 724;
	Bitmap bitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_upload);

		uploadImage = findViewById(R.id.upload_image);
		openCamera = findViewById(R.id.open_camera);
		testNameEditText = findViewById(R.id.test_name);
		hospitalNameEditText = findViewById(R.id.hospital_name);
		referredByEditText = findViewById(R.id.referred_by);
		dateOfReportEditText = findViewById(R.id.date_of_report);
		dataEditText = findViewById(R.id.data);
//		imageView = findViewById(R.id.image);

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
								.setPositiveButton("OK", (dialog, which) -> {
									dialog.dismiss();
								})
								.setCancelable(true);
						builder.create().show();
					}

					@Override
					public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
						permissionToken.continuePermissionRequest();
					}
				}).check());

		openCamera.setOnClickListener(v -> Dexter.withContext(this)
				.withPermission(Manifest.permission.CAMERA)
				.withListener(new PermissionListener() {
					@Override
					public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
						Toast.makeText(ReportUploadActivity.this, "THANK YOU", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
						AlertDialog.Builder builder = new AlertDialog.Builder(ReportUploadActivity.this);
						builder.setTitle("Permission Required")
								.setMessage("Camera is required to capture your document.")
								.setPositiveButton("OK", (dialog, which) -> {
									dialog.dismiss();
								})
								.setCancelable(true);
						builder.create().show();
					}

					@Override
					public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
						permissionToken.continuePermissionRequest();
					}
				}).check());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RC_CHOOSE_IMAGE && resultCode == RESULT_OK && data != null) {
			Uri uri = data.getData();
			assert uri != null;
//			Uri imageUri = Uri.fromFile(new File(this.getCacheDir(), "IMG_" + System.currentTimeMillis()));
//			getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//			UCrop.of(uri, imageUri)
//					.withAspectRatio(1, 1)
//					.withMaxResultSize(512, 512) // any resolution you want
//					.start(this);
//			imageView.setImageURI(uri);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Get Data?")
					.setMessage("Do you want eReports to read the data from the image?")
					.setPositiveButton("YES", (dialog, which) -> {
						fetchDataFromImage(uri);
					})
					.setNegativeButton("NO", (dialog, which) -> {
						dialog.dismiss();
					})
					.setCancelable(false);
			builder.create().show();
		}
	}

	private void fetchDataFromImage(Uri uri) {
		InputImage image;
		try {
			image = InputImage.fromFilePath(this, uri);
			TextRecognizer recognizer = TextRecognition.getClient();
			recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
				@Override
				public void onSuccess(Text visionText) {
//									String resultText = visionText.getText();
//									Log.e("RESULT", resultText);
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
//										Point[] blockCornerPoints = block.getCornerPoints();
//										Rect blockFrame = block.getBoundingBox();
//										for (Text.Line line : block.getLines()) {
//											String lineText = line.getText();
//											Point[] lineCornerPoints = line.getCornerPoints();
//											Rect lineFrame = line.getBoundingBox();
//											for (Text.Element element : line.getElements()) {
//												String elementText = element.getText();
//												Point[] elementCornerPoints = element.getCornerPoints();
//												Rect elementFrame = element.getBoundingBox();
////											}
////										}
					}
					dataEditText.setText(data);
				}
			})
					.addOnFailureListener(
							new OnFailureListener() {
								@Override
								public void onFailure(@NonNull Exception e) {
									// Task failed with an exception
									// ...
								}
							});
		} catch (IOException e) {
			Toast.makeText(this, "ML IS NOT WORKING", Toast.LENGTH_SHORT).show();
		}
	}
}