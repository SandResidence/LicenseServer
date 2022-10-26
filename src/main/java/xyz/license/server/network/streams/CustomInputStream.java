package xyz.license.server.network.streams;

import xyz.license.server.crypto.RSAUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;

public class CustomInputStream extends DataInputStream {
    public CustomInputStream(InputStream stream) {
        super(stream);
    }

    public String readEncryptedString(Key key) throws IOException {
        int length = readInt();
        byte[] buffer = new byte[length];
        readFully(buffer, 0, length);

        byte[] decryptedData = RSAUtils.decryptData(key, buffer);
        return new String(decryptedData);
    }
}
