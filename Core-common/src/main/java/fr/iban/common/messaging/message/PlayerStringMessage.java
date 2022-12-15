package fr.iban.common.messaging.message;

import java.util.UUID;

public class PlayerStringMessage {

    private UUID uuid;
    private String string;

    public PlayerStringMessage(UUID uuid, String string) {
        this.uuid = uuid;
        this.string = string;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getString() {
        return string;
    }
}
