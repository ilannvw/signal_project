package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class AlertGenerator {
    private DataStorage dataStorage;
    private List<Alert> alerts;

    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.alerts = new ArrayList<>();
    }

    /**
     * Constructs an AlertGenerator with the provided DataStorage.
     *
     * @param patient The data storage to retrieve patient records.
     */
    public void evaluateData(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());

        for (PatientRecord record : records) {
            System.out.println("Evaluating record type: " + record.getRecordType() + ", value: " + record.getMeasurementValue());

            if ("HeartRate".equals(record.getRecordType()) && record.getMeasurementValue() > 100) {
                raiseAlert(patient.getPatientId(), "High Heart Rate", record.getTimestamp(), "Heart rate exceeds 100 bpm.");
            }
            if ("BloodPressure".equals(record.getRecordType()) && record.getMeasurementValue() < 80) {
                raiseAlert(patient.getPatientId(), "Low Blood Pressure", record.getTimestamp(), "Blood pressure below 80 mmHg.");
            }
            if ("BloodPressure".equals(record.getRecordType()) && record.getMeasurementValue() > 140) {
                raiseAlert(patient.getPatientId(), "High Blood Pressure", record.getTimestamp(), "Blood pressure exceeds 140 mmHg.");
            }
            if ("Saturation".equals(record.getRecordType()) && record.getMeasurementValue() < 90) {
                raiseAlert(patient.getPatientId(), "Low Blood Saturation", record.getTimestamp(), "Blood saturation below 90%.");
            }
            if ("ECG".equals(record.getRecordType()) && record.getMeasurementValue() > 1.5) {
                raiseAlert(patient.getPatientId(), "Abnormal ECG", record.getTimestamp(), "ECG value exceeds 1.5 mV.");
            }
        }
    }

    /**
     * Raises an alert for a specific condition.
     *
     * @param patientId  The ID of the patient for whom the alert is being raised.
     * @param condition  The condition triggering the alert.
     * @param timestamp  The time at which the alert was raised.
     * @param message    A message describing the alert condition.
     */
    private void raiseAlert(int patientId, String condition, long timestamp, String message) {
        Alert alert = new Alert(String.valueOf(patientId), condition, timestamp);
        alerts.add(alert);
        System.out.println("Alert raised: " + message);
    }

    /**
     * Retrieves a copy of the list of alerts.
     *
     * @return A list containing alerts.
     */
    public List<Alert> getAlerts() {
        return new ArrayList<>(alerts);
    }
}