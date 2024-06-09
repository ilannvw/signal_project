package com.data_management;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a patient and manages their records.
 */
public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;

    /**
     * Constructs a Patient with the specified patient ID.
     *
     * @param patientId the ID of the patient.
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    /**
     * Adds a record to the patient's list of records.
     *
     * @param measurementValue the value of the measurement.
     * @param recordType       the type of the record (e.g., HeartRate).
     * @param timestamp        the timestamp of the record.
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }

    /**
     * Retrieves the patient's records within a specified time range.
     *
     * @param startTime the start of the time range.
     * @param endTime   the end of the time range.
     * @return a list of patient records within the specified time range.
     */
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        List<PatientRecord> recordsInRange = new ArrayList<>();
        for (PatientRecord record : patientRecords) {
            if (record.getTimestamp() >= startTime && record.getTimestamp() <= endTime) {
                recordsInRange.add(record);
            }
        }
        return recordsInRange;
    }

    /**
     * Returns the ID of the patient.
     *
     * @return the ID of the patient.
     */
    public int getPatientId() {
        return patientId;
    }
}
