package com.sjbit.ereport.main.ui.hospitals;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sjbit.ereport.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Handles the Hospitals View of the User.
 */
public class HospitalsFragment extends Fragment {
	private static final FirebaseAuth auth = FirebaseAuth.getInstance();
	private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

	public static HashSet<String> hospitals= new HashSet<>();

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hospitals, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getHospitalFromFirebase();
		//Setup Recycler View
		RecyclerView hospitalsView = view.findViewById(R.id.hospitals_recycler_view);
		GridLayoutManager manager = new GridLayoutManager(requireContext(), 2);
		manager.setOrientation(RecyclerView.VERTICAL);
		hospitalsView.setAdapter(new HospitalAdapter(hospitals));
		hospitalsView.setLayoutManager(manager);
	}

	public static void getHospitalFromFirebase(){
		FirebaseUser user = auth.getCurrentUser();
		assert user != null;
		DatabaseReference reference = database.getReference().child("users").child(user.getUid()).child("blockchain");
		reference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
				for(DataSnapshot snap:snapshot.getChildren()){
					if(snap.child("blockObject").child("type").getValue().toString().equals("REPORT")){
						hospitals.add(snap.child("blockObject").child("hospitalName").getValue().toString());
					}
				}
				Log.i("Hospitals",hospitals.toString());
			}

			@Override
			public void onCancelled(@NonNull @NotNull DatabaseError error) {

			}
		});

	}
}