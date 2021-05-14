package com.sjbit.ereport.main.ui.upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sjbit.ereport.R;

/**
 * Handles Uploading of the Documents.
 */
public class UploadFragment extends Fragment {

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_upload, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		//Create a Bottom Sheet Dialog.
		UploadBottomSheetDialog sheetDialogFragment = new UploadBottomSheetDialog();
		sheetDialogFragment.show(getChildFragmentManager(), "bottomSheet");
	}
}