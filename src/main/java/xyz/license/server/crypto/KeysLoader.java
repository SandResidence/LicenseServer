package xyz.license.server.crypto;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

public class KeysLoader {
    public static PrivateKey loadPrivateKey(File file) {
        Objects.requireNonNull(file, "null private key file");
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePrivate(new PKCS8EncodedKeySpec(data));
        } catch (Throwable e) {
            System.err.println(String.format("Failed to load private key (%s): %s", file.getName(), e.getMessage()));
        }
        return null;
    }

    public static PublicKey loadPublicKey(File file) {
        Objects.requireNonNull(file, "null public key file");
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(new X509EncodedKeySpec(data));
        } catch (Throwable e) {
            System.err.println(String.format("Failed to load public key (%s): %s", file.getName(), e.getMessage()));
        }
        return null;
    }
}
