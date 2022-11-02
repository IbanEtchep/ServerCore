package fr.iban.common.messaging.message;

import java.util.UUID;

public class PlayerBoolean {

    private UUID uuid;
    private boolean value;

    public PlayerBoolean(UUID uuid, boolean value) {
        this.uuid = uuid;
        this.value = value;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isValue() {
        return value;
    }
}
