package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The AlertGenerator class generates alert data for patients.
 */
public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random(); // Random number generator

    private boolean[] alertStates; // Array to track alert states for each patient

    /**
     * Constructs an AlertGenerator with the specified number of patients.
     *
     * @param patientCount The number of patients.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1]; // Initialize alert states for each patient
    }

    /**
     * Generates alert data for the specified patient.
     *
     * @param patientId      The ID of the patient.
     * @param outputStrategy The output strategy to use for the generated data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                // Check if alert should be resolved
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false; // Resolve the alert
                    // Output the resolved alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // Calculate probability of alert trigger
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period

                // Check if alert is triggered
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true; // Set alert state to true
                    // Output the triggered alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            // Handle any exceptions gracefully
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
