package fr.iban.common.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Account {

    private UUID uuid;
    private String name;
    private short maxClaims = 5;
    private long lastSeen = 0;
    private Set<Integer> blackListedAnnounces = new HashSet<>();
    private Set<UUID> ignoredPlayers = new HashSet<>();
    private Map<Option, Boolean> options = new HashMap<>();
    private String ip;

    public Account(UUID uuid) {
        this.uuid = uuid;
    }

    public Account() {}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastseen) {
        this.lastSeen = lastseen;
    }

    public UUID getUUID() {
        return uuid;
    }

    public short getMaxClaims() {
        return maxClaims;
    }

    public void setMaxClaims(short maxClaims) {
        this.maxClaims = maxClaims;
    }

    public void addMaxClaims(short nombre) {
        maxClaims += nombre;
    }

    public void removeMaxClaims(short nombre) {
        maxClaims -= nombre;
        if (maxClaims < 0) maxClaims = 0;
    }

    public void setBlackListedAnnounces(Set<Integer> blackListedAnnounces) {
        this.blackListedAnnounces = blackListedAnnounces;
    }

    public void setIgnoredPlayers(Set<UUID> ignoredPlayers) {
        this.ignoredPlayers = ignoredPlayers;
    }

    public void setOptions(Map<Option, Boolean> options) {
        this.options = options;
    }

    public void setOption(Option option, boolean value) {
        options.put(option, value);
    }

    public void toggleOption(Option option) {
        options.put(option, !getOption(option));
    }

    public boolean getOption(Option option) {
        return options.getOrDefault(option, option.getDefaultValue());
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

	public Set<Integer> getBlackListedAnnounces() {
		if (blackListedAnnounces == null) {
			blackListedAnnounces = new HashSet<>();
		}
		return blackListedAnnounces;
	}

	public Set<UUID> getIgnoredPlayers() {
		if (ignoredPlayers == null) {
			ignoredPlayers = new HashSet<>();
		}
		return ignoredPlayers;
	}

	public Map<Option, Boolean> getOptions() {
		if (options == null) {
			options = new HashMap<>();
		}
		return options;
	}
}
