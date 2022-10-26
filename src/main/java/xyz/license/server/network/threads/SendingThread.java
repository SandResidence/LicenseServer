package xyz.license.server.network.threads;

import lombok.Getter;
import xyz.license.server.network.Packet;
import xyz.license.server.network.PacketRegistry;
import xyz.license.server.network.streams.CustomOutputStream;
import xyz.license.server.server.Connection;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SendingThread implements Runnable {

    private final Connection connection;
    @Getter
    private final List<Packet> packetQueue;

    public SendingThread(Connection connection) {
        this.connection = connection;
        this.packetQueue = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!connection.getSocket().isClosed())
            tick();
        throw new ThreadDeath();
    }

    private void tick() {
        if (packetQueue.isEmpty())
            return;

        Packet packet = packetQueue.remove(0);
        int packetId = PacketRegistry.getPacketId(packet.getClass());

        CustomOutputStream stream = connection.getOutputStream();
        try {
            stream.writeInt(packetId);
            packet.write(stream);
        } catch (Exception exception) {
            System.err.println(String.format("Failed to send packet to %s: %s", connection.getSocket().getInetAddress().getHostAddress(), exception.getMessage()));
            connection.disconnect();
        }
    }
}
