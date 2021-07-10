package com.sjbit.ereport.main.ui.upload;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sjbit.ereport.R;
import com.sjbit.ereport.main.HomeActivity;

/**
 * Handles showing of the Upload Bottom Sheet Modal.
 */
public class UploadBottomSheetDialog extends BottomSheetDialogFragment {

	@Override
	public void onCancel(@NonNull DialogInterface dialog) {
		super.onCancel(dialog);
		HomeActivity.navView.setSelectedItemId(R.id.navigation_home);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.modal_upload_choice_layout, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		//Find Views
		TextView reportUploadTextView = view.findViewById(R.id.upload_report);
		ImageView reportUploadButton = view.findViewById(R.id.upload_report_icon);
		TextView prescriptionUploadTextView = view.findViewById(R.id.upload_prescription);
		ImageView prescriptionUploadButton = view.findViewById(R.id.upload_prescription_icon);

		//Handle Report Upload
		reportUploadButton.setOnClickListener(v -> startActivity(new Intent(requireActivity(), ReportUploadActivity.class)));
		reportUploadTextView.setOnClickListener(v -> startActivity(new Intent(requireActivity(), ReportUploadActivity.class)));
		prescriptionUploadButton.setOnClickListener(v -> startActivity(new Intent(requireActivity(), PresciptionUploadActivity.class)));
		prescriptionUploadTextView.setOnClickListener(v -> startActivity(new Intent(requireActivity(), PresciptionUploadActivity.class)));
	}
}
