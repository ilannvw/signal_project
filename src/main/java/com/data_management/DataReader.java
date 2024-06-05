package com.data_management;

import java.io.IOException;
import java.nio.file.Path;

public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     * 
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    void readData(Path directoryPath, DataStorage dataStorage) throws IOException;
}
