package com.sjbit.ereport.storage;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

/**
 * Handles the Uploading of the Block to the BlockChain.
 */
public class Uploader {

	private static final FirebaseAuth auth = FirebaseAuth.getInstance();
	private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

	private boolean isChainValid() {
		FirebaseUser user = auth.getCurrentUser();
		assert user != null;
		DatabaseReference reference = database.getReference().child("users").child(user.getUid()).child("blockchain");
		reference.get().addOnCompleteListener(task -> {
			//TODO: Verify Chain
		});
		return true;
	}

	public static void addBlock(Block block) {
		FirebaseUser user = auth.getCurrentUser();
		assert user != null;
		DatabaseReference reference = database.getReference().child("users").child(user.getUid()).child("blockchain");
		reference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
				if (snapshot.getChildrenCount() == 0) {
					block.setPreviousBlockHash(user.getUid());
					reference.child("Block 0").setValue(block);
				} else {
					String previousBlockHash = (String) snapshot.child("Block " + (snapshot.getChildrenCount() - 1)).child("blockHash").getValue();
					block.setPreviousBlockHash(previousBlockHash);
					reference.child("Block " + snapshot.getChildrenCount()).setValue(block);
				}
			}

			@Override
			public void onCancelled(@NonNull @NotNull DatabaseError error) {
				Log.e("UPLOAD", error.toString());
			}
		});
	}
}
