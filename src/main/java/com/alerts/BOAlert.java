package com.alerts;

public class BOAlert extends Alert {
    public BOAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public void alertAction() {
        System.out.println("Blood Oxygen Alert: " + getCondition() + " for Patient ID: " + getPatientId());
    }
}
