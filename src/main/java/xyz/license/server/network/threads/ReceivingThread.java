package xyz.license.server.network.threads;

import xyz.license.server.network.Packet;
import xyz.license.server.network.PacketRegistry;
import xyz.license.server.network.streams.CustomInputStream;
import xyz.license.server.server.Connection;

public class ReceivingThread implements Runnable {

    private final Connection connection;

    public ReceivingThread(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        while (!connection.getSocket().isClosed())
            listen();
        throw new ThreadDeath();
    }

    private void listen() {
        try {
            CustomInputStream stream = connection.getInputStream();
            if (stream.available() < 1)
                return;

            int packetId = stream.readInt();

            Class<? extends Packet> packetClass = PacketRegistry.getPacket(packetId);
            if (packetClass == null)
                throw new IllegalStateException(String.format("No such packet, id: %d", packetId));

            Packet packet = packetClass.getConstructor(Connection.class).newInstance(connection);
            packet.read(stream);
        } catch (Exception exception) {
            System.err.println(String.format("Failed to read packet from %s: %s", connection.getSocket().getInetAddress().getHostAddress(), exception.getMessage()));
            connection.disconnect();
        }
    }
}
