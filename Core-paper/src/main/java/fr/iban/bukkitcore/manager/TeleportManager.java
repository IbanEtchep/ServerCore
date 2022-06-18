package fr.iban.bukkitcore.manager;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.teleport.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeleportManager {

    private final CoreBukkitPlugin plugin;
    private final ListMultimap<UUID, TpRequest> tpRequests = ArrayListMultimap.create();
    private final List<UUID> pendingTeleports = new ArrayList<>();

    public TeleportManager(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Teleport player to SLocation
     */
    public void teleport(Player player, SLocation sloc) {
        teleport(player, sloc, 0);
    }

    /**
     * Delayed teleport player to SLocation.
     *
     * @param delay  delay in seconds
     */
    public void teleport(Player player, SLocation sloc, int delay) {
        setLastLocation(player.getUniqueId());
        plugin.getMessagingManager().sendMessage("TeleportToLocationBungee", new TeleportToLocation(player.getUniqueId(), sloc, delay));
    }

    /**
     * Teleport player to another player
     *
     * @param uuid   - player's uuid
     */
    public void teleport(UUID uuid, UUID target) {
        teleport(uuid, target, 0);
    }

    /**
     * Delayed teleport player to another player
     *
     * @param uuid   - player's uuid
     * @param target - target player's uuid
     * @param delay in seconds
     */
    public void teleport(UUID uuid, UUID target, int delay) {
        setLastLocation(uuid);
		plugin.getMessagingManager().sendMessage("TeleportToPlayerBungee", new TeleportToPlayer(uuid, target, delay));
    }

    private void setLastLocation(UUID uuid) {
        Essentials essentials = plugin.getEssentials();

        if (essentials != null) {
            User user = essentials.getUser(uuid);
            if (user != null) {
                user.setLastLocation();
            }
        }
    }

    /**
     * Send teleport request to player.
     */
    public void sendTeleportRequest(UUID from, UUID to, RequestType type) {
		plugin.getMessagingManager().sendMessage("TeleportRequestBungee", new TpRequest(from, to, type));
    }

    public List<UUID> getPendingTeleports() {
        return pendingTeleports;
    }

    public void removeTeleportWaiting(UUID uuid) {
        pendingTeleports.remove(uuid);
        plugin.getMessagingManager().sendMessageAsync(CoreBukkitPlugin.REMOVE_PENDING_TP_CHANNEL, uuid.toString());
    }

    public boolean isTeleportWaiting(UUID uuid) {
        return pendingTeleports.contains(uuid);
    }

    public ListMultimap<UUID, TpRequest> getTpRequests() {
        return tpRequests;
    }

    public List<TpRequest> getTpRequests(Player player) {
        return tpRequests.get(player.getUniqueId());
    }

    public TpRequest getTpRequestFrom(Player player, UUID from) {
        for (TpRequest request : getTpRequests(player)) {
            if (request.getPlayerFrom().equals(from)) {
                return request;
            }
        }
        return null;
    }

    public void addTpRequest(UUID uuid, TpRequest tpRequest) {
        tpRequests.put(uuid, tpRequest);
        plugin.getMessagingManager().sendMessageAsync(CoreBukkitPlugin.ADD_TP_REQUEST_CHANNEL, tpRequest);
    }

    public void removeTpRequest(UUID uuid, TpRequest tpRequest) {
        tpRequests.remove(uuid, tpRequest);
        plugin.getMessagingManager().sendMessageAsync(CoreBukkitPlugin.REMOVE_TP_REQUEST_CHANNEL, tpRequest);
    }
}