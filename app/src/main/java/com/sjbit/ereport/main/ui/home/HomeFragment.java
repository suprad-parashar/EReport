package com.sjbit.ereport.main.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sjbit.ereport.R;
import com.sjbit.ereport.storage.Block;
import com.sjbit.ereport.storage.Report;
import com.sjbit.ereport.storage.Type;
import com.sjbit.ereport.storage.Uploader;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Handles the Main Page of the User's Dashboard.
 */
public class HomeFragment extends Fragment {

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
		TextView userView = view.findViewById(R.id.welcome_user);
		userView.setOnClickListener(view1 -> {
			Toast.makeText(requireContext(), "TOUCH", Toast.LENGTH_LONG).show();
			Report report = new Report(new Date(), "Dr. ABC", "Urine Routine", "Kanva Laboratories", "WBC: 16\nRBC: 0");
			Block block = new Block(Type.REPORT, report);
			Uploader.addBlock(block);
		});
	}
}