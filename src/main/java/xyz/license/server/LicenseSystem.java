package xyz.license.server;

import lombok.Getter;
import xyz.license.server.crypto.KeysManager;
import xyz.license.server.server.Server;
import xyz.license.server.user.UsersManager;

public class LicenseSystem {
    @Getter
    private static KeysManager keysManager;
    @Getter
    private static UsersManager usersManager;

    public LicenseSystem() {
        keysManager = new KeysManager();
        usersManager = new UsersManager();

        Server server = new Server(8443);
        server.run();
    }
}
