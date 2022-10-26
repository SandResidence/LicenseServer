package xyz.license.server;

import lombok.Getter;
import xyz.license.server.crypto.KeysManager;
import xyz.license.server.server.Server;
import xyz.license.server.user.UsersRepository;

public class LicenseSystem {
    @Getter
    private static KeysManager keysManager;
    @Getter
    private static UsersRepository usersRepository;

    public LicenseSystem() {
        keysManager = new KeysManager();
        usersRepository = new UsersRepository();

        Server server = new Server(8443);
        server.run();
    }
}
