package com.sjbit.ereport.main.ui.upload;

import android.content.DialogInterface;
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

public class UploadBottomSheetDialog extends BottomSheetDialogFragment {

	private ImageView reportUploadButton, prescriptionUploadButton;
	private TextView reportUploadTextView, prescriptionUploadTextView;

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
	}
}
