package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {

    // Changed variable name to camelCase
    public static final Random randomGenerator = new Random();
    private boolean[] alertStates; // Changed variable name to camelCase

    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1]; // Changed variable name to camelCase
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false; // Changed variable name to camelCase
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency // Changed variable name to camelCase
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period // Changed variable name to camelCase
                boolean alertTriggered = randomGenerator.nextDouble() < p; // Changed variable name to camelCase

                if (alertTriggered) {
                    alertStates[patientId] = true; // Changed variable name to camelCase
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            // Adjusted error message for consistency
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
