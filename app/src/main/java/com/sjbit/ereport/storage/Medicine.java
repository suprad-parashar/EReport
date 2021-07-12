package com.sjbit.ereport.storage;

import java.io.Serializable;

public class Medicine implements Serializable {
	String medicineName;
	private int morningDose;
	private int afternoonDose;
	private int eveningDose;

	public Medicine() {

	}

	public Medicine(String medicineName, int morningDose, int afternoonDose, int eveningDose) {
		this.medicineName = medicineName;
		this.morningDose = morningDose;
		this.afternoonDose = afternoonDose;
		this.eveningDose = eveningDose;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public int getMorningDose() {
		return morningDose;
	}

	public int getAfternoonDose() {
		return afternoonDose;
	}

	public int getEveningDose() {
		return eveningDose;
	}
}
