package fr.iban.bukkitcore.manager;

import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.common.manager.PlayerManager;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class BukkitPlayerManager extends PlayerManager {

    private final Map<String, UUID> offlinePlayers = new ConcurrentHashMap<>();
    private final Map<UUID, String> onlinePlayers = new ConcurrentHashMap<>();

    public BukkitPlayerManager() {
        CompletableFuture.supplyAsync(this::getProxyPlayerNamesFromDB).thenAccept(this.onlinePlayers::putAll);
        CompletableFuture.supplyAsync(this::getPlayerNamesFromDb).thenAccept(this.offlinePlayers::putAll);
    }

    public void sendMessageIfOnline(UUID uuid, String message) {
        if(onlinePlayers.containsKey(uuid)) {
            PluginMessageHelper.sendMessage(onlinePlayers.get(uuid), message);
        }
    }

    public void sendMessageRawIfOnline(UUID uuid, String message) {
        if(onlinePlayers.containsKey(uuid)) {
            PluginMessageHelper.sendMessageRaw(onlinePlayers.get(uuid), message);
        }
    }

    public UUID getOfflinePlayerUUID(String name) {
        return offlinePlayers.get(name);
    }

    public UUID getOnlinePlayerUUID(String name) {
        for (Map.Entry<UUID, String> entry : getOnlinePlayers().entrySet()) {
            if(entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Map<UUID, String> getOnlinePlayers() {
        return onlinePlayers;
    }

    public Map<String, UUID> getOfflinePlayers() {
        return offlinePlayers;
    }

    public String getName(UUID uuid) {
        return getOfflinePlayers().entrySet().stream()
                .filter(entry -> entry.getValue().equals(uuid))
                .map(Map.Entry::getKey).findFirst().orElse(null);
    }
}
