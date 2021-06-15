package com.sjbit.ereport.storage;

import java.util.Date;

public class Report extends BlockObject {
	private String testName;
	private String hospitalName;
	private String data;

	public Report() {
		setType(Type.REPORT);
	}

	public Report(Date date, String doctorName, String testName, String hospitalName, String data) {
		this.testName = testName;
		this.hospitalName = hospitalName;
		this.data = data;
		setDate(date);
		setType(Type.REPORT);
		setDoctorName(doctorName);
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
