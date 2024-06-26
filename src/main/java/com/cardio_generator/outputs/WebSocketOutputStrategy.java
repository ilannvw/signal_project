package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * A class that implements the OutputStrategy interface to send output data
 * via a WebSocket server.
 */
public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;

    /**
     * Constructs a WebSocketOutputStrategy with the specified port.
     *
     * @param port the port on which the WebSocket server will listen.
     */
    public WebSocketOutputStrategy(int port) {
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();
    }

    /**
     * Outputs data to all connected WebSocket clients.
     *
     * @param patientId the ID of the patient.
     * @param timestamp the timestamp of the data.
     * @param label     the label describing the data.
     * @param data      the data to be sent.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        // Validate input data
        if (label == null || data == null || label.isEmpty() || data.isEmpty()) {
            System.err.println("Invalid data: label and data must be non-null and non-empty.");
            return;
        }

        // Format the message
        String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);

        // Broadcast the message to all connected clients
        for (WebSocket conn : server.getConnections()) {
            conn.send(message);
        }
    }

    /**
     * A simple implementation of WebSocketServer to handle WebSocket connections.
     */
    private static class SimpleWebSocketServer extends WebSocketServer {

        /**
         * Constructs a SimpleWebSocketServer with the specified address.
         *
         * @param address the address on which the WebSocket server will listen.
         */
        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }

    /**
     * Sets a mock WebSocket server. This method is not implemented.
     *
     * @param mockServer the mock WebSocket server to set.
     */
    public void setMockServer(WebSocketServer mockServer) {
        this.server = server;
        throw new UnsupportedOperationException("Unimplemented method 'setMockServer'");
    }
}
