package com.data_management;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class to read data from files and store it in a data storage.
 */
public class FileReader implements DataReader {

    @Override
    public void readData(Path filePath, DataStorage dataStorage) throws IOException {
        // Ensure the file exists
        if (!Files.isRegularFile(filePath)) {
            throw new IllegalArgumentException("Specified path is not a regular file: " + filePath);
        }

        // Read data from the file and store it in the data storage
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseAndStoreData(line, dataStorage);
            }
        }
    }

    private void parseAndStoreData(String line, DataStorage dataStorage) {
        // Parse each line to extract data
        String[] parts = line.split(",");
        if (parts.length == 4) {
            int patientId = Integer.parseInt(parts[0].trim());
            double measurementValue = Double.parseDouble(parts[1].trim());
            String recordType = parts[2].trim();
            long timestamp = Long.parseLong(parts[3].trim());

            // Add parsed data to the data storage
            dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
        } else {
            System.err.println("Invalid data format: " + line);
        }
    }
}