package xyz.license.server.user;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class UsersRepository {
    private final List<User> users;

    public UsersRepository() {
        this.users = new ArrayList<>();
        loadUsers();
    }

    private void loadUsers() {
        // Hardcoded users with different expire times
        users.add(new User("WaterFox", "mypassword", Instant.now()));
        users.add(new User("InferiorText3", "^5trongPa55w0rd!", Instant.now().plus(3, ChronoUnit.DAYS)));
        users.add(new User("IntelCorei5", "H3ll0W0rld", Instant.now().plus(1, ChronoUnit.HOURS)));
        users.add(new User("RandomUser", "1234", Instant.now().plus(20, ChronoUnit.MINUTES)));
    }

    public User getUser(String login) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }
}
