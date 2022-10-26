package xyz.license.server.server;

import lombok.Getter;

public enum ValidationStatus {
    INVALID_USER_DATA((byte) 1),
    BAD_TIMESTAMP((byte) 2),
    EXPIRED_LICENSE((byte) 3),
    SUCCESS((byte) 4);

    @Getter
    private byte code;

    ValidationStatus(byte code) {
        this.code = code;
    }
}
