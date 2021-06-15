package com.sjbit.ereport.storage;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Block {
	private Type type;
	private String previousBlockHash;
	private String blockHash;
	private BlockObject blockObject;
	private int nonce = -1;

	public Block(String previousBlockHash, Type type, BlockObject blockObject) {
		this.type = type;
		this.previousBlockHash = previousBlockHash;
		this.blockObject = blockObject;
		this.blockHash = calculateHash();
	}

	private String getHash()
	{
		byte[] data = (previousBlockHash + type.toString() + blockObject.toString() + nonce).getBytes(StandardCharsets.UTF_8);
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(data);

			BigInteger number = new BigInteger(1, hash);
			StringBuilder hexString = new StringBuilder(number.toString(16));
			while (hexString.length() < 32)
				hexString.insert(0, '0');
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}

	private String calculateHash() {
		String hash;
		do {
			nonce++;
			hash = getHash();
		} while (!hash.startsWith("abcd"));
		return hash;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
		blockHash = calculateHash();
	}

	public String getPreviousBlockHash() {
		return previousBlockHash;
	}

	public String getBlockHash() {
		return blockHash;
	}

	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}

	public void setPreviousBlockHash(String previousBlockHash) {
		this.previousBlockHash = previousBlockHash;
		blockHash = calculateHash();
	}

	public BlockObject getBlockObject() {
		return blockObject;
	}

	public void setBlockObject(BlockObject blockObject) {
		this.blockObject = blockObject;
		blockHash = calculateHash();
	}
}
