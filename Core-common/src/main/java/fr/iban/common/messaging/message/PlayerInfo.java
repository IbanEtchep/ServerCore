package fr.iban.common.messaging.message;

import java.util.UUID;

public class PlayerInfo {

    private UUID uuid;
    private String name;
    private String ip;

    public PlayerInfo(UUID uuid, String name, String ip) {
        this(uuid, name);
        this.ip = ip;
    }

    public PlayerInfo(UUID uuid, String name) {
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

    public String getIp() {
        return ip;
    }
}
