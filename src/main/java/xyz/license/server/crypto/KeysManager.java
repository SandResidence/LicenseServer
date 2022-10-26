package xyz.license.server.crypto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class KeysManager {
    private static final File privateKeyFile = new File("private.key");
    private static final File publicKeyFile = new File("public.key");

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeysManager() {
        init();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    private void init() {
        if (shouldKeysBeGenerated()) {
            KeyPair keyPair = KeysGenerator.generateKeyPair(2048);

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
            saveToFile(privateKey, publicKey);

            prettyPrintPublicKey();
        } else {
            privateKey = KeysLoader.loadPrivateKey(privateKeyFile);
            publicKey = KeysLoader.loadPublicKey(publicKeyFile);
        }
    }

    private void prettyPrintPublicKey() {
        byte[] data = publicKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(data);
        System.out.println("\n**********************************");
        System.out.println(" Base64-Encoded public key for client:");
        System.out.println(" " + encodedKey);
        System.out.println("**********************************\n");
    }

    private boolean shouldKeysBeGenerated() {
        return !publicKeyFile.exists() ||
                !privateKeyFile.exists();
    }

    private void saveToFile(Key... keys) {
        for (Key key : keys) {
            byte[] keyData = key.getEncoded();
            File file = key instanceof PublicKey ? publicKeyFile : privateKeyFile;
            try {
                Files.write(file.toPath(), keyData);
            } catch (IOException e) {
                System.err.println(String.format("Failed to save %s: %s", file.getName(), e.getMessage()));
            }
        }
    }
}
