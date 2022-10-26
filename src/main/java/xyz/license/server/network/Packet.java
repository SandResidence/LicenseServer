package xyz.license.server.network;

import xyz.license.server.network.streams.CustomInputStream;
import xyz.license.server.network.streams.CustomOutputStream;

import java.io.IOException;

public abstract class Packet {
    public Packet() {
    }

    public abstract void write(CustomOutputStream stream) throws IOException;

    public abstract void read(CustomInputStream stream) throws IOException;
}
