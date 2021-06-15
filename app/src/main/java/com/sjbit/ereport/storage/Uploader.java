package com.sjbit.ereport.storage;

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

	private FirebaseAuth auth = FirebaseAuth.getInstance();
	private FirebaseDatabase database = FirebaseDatabase.getInstance();

	private boolean isChainValid() {
		FirebaseUser user = auth.getCurrentUser();
		assert user != null;
		DatabaseReference reference = database.getReference().child("users").child(user.getUid()).child("blockchain");
		reference.get().addOnCompleteListener(task -> {
			//TODO: Verify Chain
		});
		return true;
	}

	private void addBlock(Block block) {
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
					Block previousBlock = snapshot.child("Block " + (snapshot.getChildrenCount() - 1)).getValue(Block.class);
					assert previousBlock != null;
					block.setPreviousBlockHash(previousBlock.getBlockHash());
					reference.child("Block " + snapshot.getChildrenCount()).setValue(block);
				}
			}

			@Override
			public void onCancelled(@NonNull @NotNull DatabaseError error) {

			}
		});
	}
}
