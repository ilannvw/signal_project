package com.cardio_generator.outputs;

/**
 * The OutputStrategy interface defines a method for outputting patient data.
 */
public interface OutputStrategy {

    /**
     * Outputs patient data.
     *
     * @param patientId The ID of the patient.
     * @param timestamp The timestamp of the data.
     * @param label     The label of the data.
     * @param data      The actual data to be output.
     */
    void output(int patientId, long timestamp, String label, String data);
}
