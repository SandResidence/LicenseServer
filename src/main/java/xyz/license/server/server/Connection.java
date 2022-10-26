package xyz.license.server.server;

import lombok.Getter;
import lombok.Setter;
import xyz.license.server.network.streams.CustomInputStream;
import xyz.license.server.network.streams.CustomOutputStream;
import xyz.license.server.network.threads.ReceivingThread;
import xyz.license.server.network.threads.SendingThread;

import java.net.Socket;

@Getter
public class Connection {
    private final Socket socket;
    private final ValidationData validationData;
    private CustomInputStream inputStream;
    private CustomOutputStream outputStream;

    private ReceivingThread receivingThread;
    private SendingThread sendingThread;

    public Connection(Socket socket) {
        this.socket = socket;
        this.validationData = new ValidationData();

        setupDataStreams();
        runThreads();
    }

    private void setupDataStreams() {
        try {
            inputStream = new CustomInputStream(socket.getInputStream());
            outputStream = new CustomOutputStream(socket.getOutputStream());
        } catch (Exception exception) {
            System.err.println(String.format("Failed to setup data streams: %s", exception.getMessage()));
            disconnect();
        }
    }

    private void runThreads() {
        receivingThread = new ReceivingThread(this);
        new Thread(receivingThread).start();

        sendingThread = new SendingThread(this);
        new Thread(sendingThread).start();
    }

    public void disconnect() {
        try {
            socket.close();
            System.out.println(String.format("Connection %s closed.", socket.getInetAddress().getHostAddress()));
        } catch (Exception exception) {
            System.err.println(String.format("Failed to close connection: %s", exception.getMessage()));
        }
    }

    @Getter
    @Setter
    public class ValidationData {
        private String login;
        private String password;
        private long timestamp;
    }
}