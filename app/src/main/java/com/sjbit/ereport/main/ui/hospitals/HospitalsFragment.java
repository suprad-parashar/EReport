package com.sjbit.ereport.main.ui.hospitals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjbit.ereport.R;


public class HospitalsFragment extends Fragment {

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hospitals, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		RecyclerView hospitalsView = view.findViewById(R.id.hospitals_recycler_view);
		GridLayoutManager manager = new GridLayoutManager(requireContext(), 2);
		manager.setOrientation(RecyclerView.VERTICAL);
		hospitalsView.setAdapter(new HospitalAdapter());
		hospitalsView.setLayoutManager(manager);
	}
}