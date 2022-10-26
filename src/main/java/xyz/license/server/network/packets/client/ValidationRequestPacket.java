package xyz.license.server.network.packets.client;

import xyz.license.server.LicenseSystem;
import xyz.license.server.network.Packet;
import xyz.license.server.network.packets.server.ValidationResponsePacket;
import xyz.license.server.network.streams.CustomInputStream;
import xyz.license.server.network.streams.CustomOutputStream;
import xyz.license.server.server.Connection;
import xyz.license.server.server.ValidationStatus;
import xyz.license.server.user.User;

import java.io.IOException;

public class ValidationRequestPacket extends Packet {
    private final Connection connection;

    public ValidationRequestPacket(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void write(CustomOutputStream stream) throws IOException {
    }

    @Override
    public void read(CustomInputStream stream) throws IOException {

        Connection.ValidationData data = connection.getValidationData();
        User user = LicenseSystem.getUsersRepository().getUser(data.getLogin());

        ValidationStatus result = null;
        if (!isTimestampValid())
            result = ValidationStatus.BAD_TIMESTAMP;
        if (user == null || user.isPasswordValid(data.getPassword()))
            result = ValidationStatus.INVALID_USER_DATA;
        if (user.isExpired())
            result = ValidationStatus.EXPIRED_LICENSE;

        if (result == null)
            result = ValidationStatus.SUCCESS;

        System.out.println(String.format("Connection %s validated with status: %s", connection.getSocket().getInetAddress().getHostAddress(), result.name()));

        ValidationResponsePacket response = new ValidationResponsePacket(result);
        connection.getSendingThread().getPacketQueue().add(response);
    }

    private boolean isTimestampValid() {
        long currentTimestamp = System.currentTimeMillis();
        long providedTimestamp = connection.getValidationData().getTimestamp();

        long difference = currentTimestamp - providedTimestamp;

        return difference < 10000;
    }
}
