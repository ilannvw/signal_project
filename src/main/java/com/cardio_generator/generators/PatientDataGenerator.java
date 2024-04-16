package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The PatientDataGenerator interface defines a method for generating patient data.
 */
public interface PatientDataGenerator {

    /**
     * Generates patient data.
     *
     * @param patientId      The ID of the patient.
     * @param outputStrategy The output strategy to use for the generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
