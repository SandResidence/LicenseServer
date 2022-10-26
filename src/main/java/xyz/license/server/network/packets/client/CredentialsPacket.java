package xyz.license.server.network.packets.client;

import xyz.license.server.LicenseSystem;
import xyz.license.server.network.Packet;
import xyz.license.server.network.streams.CustomInputStream;
import xyz.license.server.network.streams.CustomOutputStream;
import xyz.license.server.server.Connection;

import java.io.IOException;
import java.security.PrivateKey;

public class CredentialsPacket extends Packet {
    private final Connection connection;

    public CredentialsPacket(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void write(CustomOutputStream stream) throws IOException {}

    @Override
    public void read(CustomInputStream stream) throws IOException {
        PrivateKey privateKey = LicenseSystem.getKeysManager().getPrivateKey();

        String login = stream.readEncryptedString(privateKey);
        String password = stream.readEncryptedString(privateKey);

        connection.getValidationData().setLogin(login);
        connection.getValidationData().setPassword(password);
    }
}
