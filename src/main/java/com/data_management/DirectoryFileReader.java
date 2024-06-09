package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * A class that implements the DataReader interface to read data from files in a specified directory.
 */
public class DirectoryFileReader implements DataReader {
    private final Path directoryPath;

    /**
     * Constructs a DirectoryFileReader with the specified directory path.
     *
     * @param directoryPath the path to the directory containing the files to be read.
     * @throws IllegalArgumentException if the specified path is not a directory.
     */
    public DirectoryFileReader(Path directoryPath) {
        if (!Files.isDirectory(directoryPath)) {
            throw new IllegalArgumentException("Specified path is not a directory: " + directoryPath);
        }
        this.directoryPath = directoryPath;
    }

    /**
     * Reads data from all files in the directory and stores it in the provided DataStorage instance.
     *
     * @param dataStorage the DataStorage instance where the parsed data will be stored.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        // Iterate through files in the directory
        try (Stream<Path> stream = Files.list(directoryPath)) {
            stream.filter(Files::isRegularFile)
                    .forEach(file -> parseAndStoreData(file, dataStorage));
        }
    }

    /**
     * Parses and stores data from the specified file.
     *
     * @param filePath the path to the file to be read.
     * @param dataStorage the DataStorage instance where the parsed data will be stored.
     */
    public void parseAndStoreData(Path filePath, DataStorage dataStorage) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse each line to extract data
                // Assuming data format: <patientId>,<measurementValue>,<recordType>,<timestamp>
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int patientId = Integer.parseInt(parts[0].trim());
                    double measurementValue = Double.parseDouble(parts[1].trim());
                    String recordType = parts[2].trim();
                    long timestamp = Long.parseLong(parts[3].trim());

                    // Add parsed data to DataStorage
                    dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
    }
}
