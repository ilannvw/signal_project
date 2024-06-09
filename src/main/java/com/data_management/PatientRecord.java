package com.data_management;

/**
 * A class that represents a record for a patient.
 */
public class PatientRecord {
    private int patientId;
    private String recordType; // Example: ECG, blood pressure, etc.
    private double measurementValue; // Example: heart rate
    private long timestamp;

    /**
     * Constructs a PatientRecord with the specified details.
     *
     * @param patientId        the ID of the patient.
     * @param measurementValue the value of the measurement.
     * @param recordType       the type of the record (e.g., HeartRate).
     * @param timestamp        the timestamp of the record.
     */
    public PatientRecord(int patientId, double measurementValue, String recordType, long timestamp) {
        this.patientId = patientId;
        this.measurementValue = measurementValue;
        this.recordType = recordType;
        this.timestamp = timestamp;
    }

    /**
     * Returns the ID of the patient.
     *
     * @return the ID of the patient.
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Returns the value of the measurement.
     *
     * @return the value of the measurement.
     */
    public double getMeasurementValue() {
        return measurementValue;
    }

    /**
     * Returns the timestamp of the record.
     *
     * @return the timestamp of the record.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the type of the record.
     *
     * @return the type of the record.
     */
    public String getRecordType() {
        return recordType;
    }
}
