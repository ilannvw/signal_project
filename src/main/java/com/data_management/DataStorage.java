package com.data_management;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Singleton class for managing patient data storage.
 */
public class DataStorage {

    private final ConcurrentHashMap<Integer, Patient> patientMap;

    private static DataStorage instance;

    /**
     * Private constructor to prevent instantiation.
     */
    private DataStorage() {
        this.patientMap = new ConcurrentHashMap<>();
    }

    /**
     * Returns the singleton instance of DataStorage.
     *
     * @return the singleton instance of DataStorage.
     */
    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    /**
     * Adds patient data to the storage. If the patient does not already exist in the storage,
     * a new patient record is created.
     *
     * @param patientId         the ID of the patient.
     * @param measurementValue  the value of the measurement.
     * @param recordType        the type of the record (e.g., HeartRate).
     * @param timestamp         the timestamp of the record.
     * @throws NullPointerException if recordType is null.
     */
    public synchronized void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        if (recordType == null) {
            throw new NullPointerException("Record type cannot be null");
        }

        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }
        patient.addRecord(measurementValue, recordType, timestamp);
    }

    /**
     * Retrieves patient records within a specified time range.
     *
     * @param patientId the ID of the patient.
     * @param startTime the start of the time range.
     * @param endTime   the end of the time range.
     * @return a list of patient records within the specified time range.
     */
    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>(); // return an empty list if no patient is found
    }

    /**
     * Retrieves all patients in the storage.
     *
     * @return a list of all patients.
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    /**
     * Resets the singleton instance of DataStorage. This method is primarily intended for testing purposes.
     */
    public static synchronized void resetInstance() {
        instance = null;
    }
}
