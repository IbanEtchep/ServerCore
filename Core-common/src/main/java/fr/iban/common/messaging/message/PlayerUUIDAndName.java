package fr.iban.common.messaging.message;

import java.util.UUID;

public class PlayerUUIDAndName {

    private UUID uuid;
    private String name;

    public PlayerUUIDAndName(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
