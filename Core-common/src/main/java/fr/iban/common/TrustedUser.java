package fr.iban.common;

import java.util.UUID;

public class TrustedUser {

    private UUID uuid;
    private String ip;

    public TrustedUser(UUID uuid, String ip) {
        this.uuid = uuid;
        this.ip = ip;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getIp() {
        return ip;
    }
}
