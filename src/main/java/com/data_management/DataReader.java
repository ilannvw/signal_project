package com.data_management;

import java.io.IOException;
import java.nio.file.Path;

public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     * 
     * @throws IOException if there is an error reading the data
     */
    void readData(DataStorage dataStorage) throws IOException;
}
