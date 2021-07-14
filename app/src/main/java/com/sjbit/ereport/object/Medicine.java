package com.sjbit.ereport.object;

public class Medicine {
    private String medicineName;
    private String morningDose,eveningDose,afternoonDose;

    public Medicine(String medicineName, String morningDose, String afternoonDose, String eveningDose){
        this.medicineName = medicineName;
        this.morningDose = morningDose;
        this.afternoonDose = afternoonDose;
        this.eveningDose = eveningDose;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMorningDose() {
        return morningDose;
    }

    public void setMorningDose(String morningDose) {
        this.morningDose = morningDose;
    }

    public String getEveningDose() {
        return eveningDose;
    }

    public void setEveningDose(String eveningDose) {
        this.eveningDose = eveningDose;
    }

    public String getAfternoonDose() {
        return afternoonDose;
    }

    public void setAfternoonDose(String afternoonDose) {
        this.afternoonDose = afternoonDose;
    }
}
