package com.alerts;

public class BPAlert extends Alert {
    public BPAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public void alertAction() {
        System.out.println("Blood Pressure Alert: " + getCondition() + " for Patient ID: " + getPatientId());
    }
}