package xyz.license.server.network.packets.client;

import xyz.license.server.LicenseSystem;
import xyz.license.server.crypto.RSAUtils;
import xyz.license.server.network.Packet;
import xyz.license.server.network.streams.CustomInputStream;
import xyz.license.server.network.streams.CustomOutputStream;
import xyz.license.server.server.Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.PrivateKey;

public class TimestampPacket extends Packet {
    private final Connection connection;

    public TimestampPacket(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void write(CustomOutputStream stream) throws IOException {
    }

    @Override
    public void read(CustomInputStream stream) throws IOException {
        PrivateKey privateKey = LicenseSystem.getKeysManager().getPrivateKey();
        String timestampData = stream.readEncryptedString(privateKey);
        long timestamp = Long.parseLong(timestampData);
        connection.getValidationData().setTimestamp(timestamp);
    }
}
