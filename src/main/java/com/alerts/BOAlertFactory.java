package com.alerts;

public class BOAlertFactory implements AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition) {
        return new BOAlert(patientId, condition, System.currentTimeMillis());
    }
}