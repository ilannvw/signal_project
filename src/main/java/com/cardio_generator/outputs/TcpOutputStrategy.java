package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * The TcpOutputStrategy class implements the OutputStrategy interface to output patient data over TCP socket connections.
 */
public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket; // TCP server socket
    private Socket clientSocket; // TCP client socket
    private PrintWriter out; // Output stream to client

    /**
     * Constructs a TcpOutputStrategy with the specified port.
     *
     * @param port The port number for the TCP server.
     */
    public TcpOutputStrategy(int port) {
        try {
            // Initialize the TCP server socket
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to avoid blocking the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    // Accept incoming client connections
                    clientSocket = serverSocket.accept();
                    // Initialize the output stream to the client
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Outputs patient data over the TCP connection.
     *
     * @param patientId The ID of the patient.
     * @param timestamp The timestamp of the data.
     * @param label     The label of the data.
     * @param data      The actual data to be output.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            // Format the message and send it to the client
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
