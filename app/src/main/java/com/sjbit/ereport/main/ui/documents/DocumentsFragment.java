package com.sjbit.ereport.main.ui.documents;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.sjbit.ereport.R;

/**
 * Handles the Documents of the User.
 */
public class DocumentsFragment extends Fragment {

	CardView reportsCard, prescriptionsCard;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_documents, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		reportsCard = view.findViewById(R.id.reports_card);
		prescriptionsCard = view.findViewById(R.id.prescriptions_card);
		reportsCard.setOnClickListener(v -> {
			Intent intent = new Intent(requireActivity(), DocumentListActivity.class);
			intent.putExtra("type", "report");
			startActivity(intent);
		});
		prescriptionsCard.setOnClickListener(v -> {
			Intent intent = new Intent(requireActivity(), DocumentListActivity.class);
			intent.putExtra("type", "prescription");
			startActivity(intent);
		});
	}
}