package xyz.license.server.network.streams;

import xyz.license.server.crypto.RSAUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class CustomOutputStream extends DataOutputStream {
    public CustomOutputStream(OutputStream out) {
        super(out);
    }

    public void writeEncryptedString(Key key, String text) throws IOException {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedData = RSAUtils.encryptData(key, data);

        writeInt(encryptedData.length);
        write(encryptedData);
    }
}
