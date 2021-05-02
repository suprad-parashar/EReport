package com.sjbit.ereport.storage;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Uploader {
	private boolean isChainValid() {
		DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("blockchain");
		reference.get().addOnCompleteListener(task -> {
			//TODO: Verify Chain
		});
		return true;
	}

	private void addBlock(Block block) {

	}
}
