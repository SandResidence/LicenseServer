package xyz.license.server.user;

import lombok.Getter;

import java.time.Instant;

@Getter
public class User {
    private final String login;
    private final String password;
    private final Instant expire;

    public User(String login, String password, Instant expire) {
        this.login = login;
        this.password = password;
        this.expire = expire;
    }

    public boolean isPasswordValid(String input) {
        return input.equals(password);
    }

    public boolean isExpired() {
        return expire.isAfter(Instant.now());
    }
}
