package xyz.license.server.network.packets.server;

import xyz.license.server.LicenseSystem;
import xyz.license.server.network.Packet;
import xyz.license.server.network.streams.CustomInputStream;
import xyz.license.server.network.streams.CustomOutputStream;
import xyz.license.server.server.ValidationStatus;

import java.io.IOException;
import java.security.PrivateKey;

public class ValidationResponsePacket extends Packet {

    private final ValidationStatus status;

    public ValidationResponsePacket(ValidationStatus status) {
        this.status = status;
    }

    @Override
    public void write(CustomOutputStream stream) throws IOException {
        PrivateKey privateKey = LicenseSystem.getKeysManager().getPrivateKey();

        long timestamp = System.currentTimeMillis();
        stream.writeEncryptedString(privateKey, String.valueOf(timestamp));

        byte statusCode = status.getCode();
        String payload = String.format("%f_%d_%f", Math.random(), statusCode, Math.random());
        stream.writeEncryptedString(privateKey, payload);
    }

    @Override
    public void read(CustomInputStream stream) throws IOException {
    }
}
