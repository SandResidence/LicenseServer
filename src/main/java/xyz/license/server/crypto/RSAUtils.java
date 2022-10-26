package xyz.license.server.crypto;

import javax.crypto.Cipher;
import java.security.Key;
import java.util.Objects;

public class RSAUtils {
    private RSAUtils() {
    }

    public static byte[] encryptData(Key key, byte[] data) {
        Objects.requireNonNull(key, "null key");
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception exception) {
            System.err.println(String.format("Failed to encrypt: %s", exception.getMessage()));
        }
        return null;
    }

    public static byte[] decryptData(Key key, byte[] data) {
        Objects.requireNonNull(key, "null key");
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception exception) {
            System.err.println(String.format("Failed to decrypt: %s", exception.getMessage()));
        }
        return null;
    }
}
