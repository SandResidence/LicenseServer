package xyz.license.server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println(String.format("Binding on port %d...", port));
        try {
            ServerSocket server = new ServerSocket(port);

            while (!server.isClosed()) {
                Socket socket = server.accept();
                System.out.println(String.format("New connection from %s.", socket.getInetAddress().getHostAddress()));
                new Connection(socket);
            }
        } catch (IOException e) {
            System.err.println(String.format("Unable to bind server at port %d: %s", port, e.getMessage()));
        }
    }
}
