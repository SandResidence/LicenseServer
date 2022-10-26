package xyz.license.server.network;

import xyz.license.server.network.packets.client.CredentialsPacket;
import xyz.license.server.network.packets.client.TimestampPacket;
import xyz.license.server.network.packets.client.ValidationRequestPacket;
import xyz.license.server.network.packets.server.ValidationResponsePacket;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {
    private static final HashMap<Integer, Class<? extends Packet>> registredPackets = new HashMap<>();

    static {
        registredPackets.put(1, CredentialsPacket.class);
        registredPackets.put(2, TimestampPacket.class);
        registredPackets.put(3, ValidationRequestPacket.class);

        registredPackets.put(4, ValidationResponsePacket.class);
    }

    public static int getPacketId(Class<? extends Packet> packet) {
        return registredPackets.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(packet))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
    }

    public static Class<? extends Packet> getPacket(int packetId) {
        return registredPackets.getOrDefault(packetId, null);
    }
}
