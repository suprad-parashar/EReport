package com.sjbit.ereport.object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity {
    private String type;
    private String doctorName;

    private ArrayList<Medicine> medicine;
    private String hospitalName;
    private String testName;
    private String date;
    private String data;
    //Report Activity
    public Activity(String type, String doctorName, String hospitalName, String testName, String date){
        this.type = type;
        this.doctorName = doctorName;
        this.hospitalName = hospitalName;
        this.testName = testName;
        this.date = date;
    }
    //Prescription Activity
    public Activity(String type, String doctorName, ArrayList<Medicine> medicine, String date){
        this.type = type;
        this.doctorName = doctorName;
        this.medicine = medicine;
        this.date = date;
    }

    public ArrayList<Medicine> getMedicine() {
        return medicine;
    }

    public void setMedicine(ArrayList<Medicine> medicine) {
        this.medicine = medicine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
