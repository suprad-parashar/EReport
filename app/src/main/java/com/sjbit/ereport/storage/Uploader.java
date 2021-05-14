package com.sjbit.ereport.storage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Handles the Uploading of the Block to the BlockChain.
 */
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
