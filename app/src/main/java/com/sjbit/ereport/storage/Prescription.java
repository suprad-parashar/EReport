package com.sjbit.ereport.storage;

import java.util.ArrayList;
import java.util.Date;

public class Prescription extends BlockObject {
	private ArrayList<Medicine> medicines;

	public Prescription() {
		setType(Type.PRESCRIPTION);
	}

	public Prescription(Date date, String doctorName, ArrayList<Medicine> medicines) {
		setDate(date);
		setDoctorName(doctorName);
		setType(Type.PRESCRIPTION);
		this.medicines = medicines;
	}

	public ArrayList<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(ArrayList<Medicine> medicines) {
		this.medicines = medicines;
	}

	public void addMedicine(Medicine medicine) {
		this.medicines.add(medicine);
	}
}
