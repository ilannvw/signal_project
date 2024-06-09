package com.alerts;

public class BPAlertFactory implements AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition) {
        return new BPAlert(patientId, condition, System.currentTimeMillis());
    }
}