package fr.iban.bukkitcore.listeners;

import com.google.gson.Gson;
import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.event.CoreMessageEvent;
import fr.iban.bukkitcore.utils.SLocationUtils;
import fr.iban.common.messaging.CoreChannel;
import fr.iban.common.messaging.Message;
import fr.iban.common.messaging.message.PlayerUUIDAndName;
import fr.iban.common.teleport.SLocation;
import fr.iban.common.teleport.TeleportToLocation;
import fr.iban.common.teleport.TeleportToPlayer;
import fr.iban.common.teleport.TpRequest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CoreMessageListener implements Listener {

    private final CoreBukkitPlugin plugin;
    private final Gson gson = new Gson();

    public CoreMessageListener(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCoreMessage(CoreMessageEvent e) {
        Message message = e.getMessage();

        switch (e.getMessage().getChannel()) {
            case CoreChannel.SYNC_ACCOUNT_CHANNEL ->
                    plugin.getAccountManager().reloadAccount(UUID.fromString(message.getMessage()));
            case "TeleportToLocationBukkit" -> consumeTeleportToLocationBukkitMessage(message);
            case "TeleportToPlayerBukkit" -> consumeTeleportToPlayerBukkitMessage(message);
            case CoreChannel.ADD_PENDING_TP_CHANNEL ->
                    plugin.getTeleportManager().getPendingTeleports().add(UUID.fromString(e.getMessage().getMessage()));
            case CoreChannel.REMOVE_PENDING_TP_CHANNEL ->
                    plugin.getTeleportManager().getPendingTeleports().remove(UUID.fromString(e.getMessage().getMessage()));
            case CoreChannel.ADD_TP_REQUEST_CHANNEL -> consumeAddTpRequestMessage(message);
            case CoreChannel.REMOVE_TP_REQUEST_CHANNEL -> consumeRemoveTpRequestMessage(message);
            case CoreChannel.PLAYER_JOIN_CHANNEL -> consumePlayerJoinMessage(message);
            case CoreChannel.PLAYER_QUIT_CHANNEL -> consumePlayerQuitMessage(message);
        }
    }

    public void consumePlayerJoinMessage(Message message) {
        PlayerUUIDAndName playerMessage = gson.fromJson(message.getMessage(), PlayerUUIDAndName.class);
        plugin.getPlayerManager().getOnlinePlayers().put(playerMessage.getUuid(), playerMessage.getName());
        plugin.getPlayerManager().getOfflinePlayers().putIfAbsent(playerMessage.getName(), playerMessage.getUuid());
    }

    public void consumePlayerQuitMessage(Message message) {
        plugin.getPlayerManager().getOnlinePlayers().remove(UUID.fromString(message.getMessage()));
    }

    public void consumeAddTpRequestMessage(Message message) {
        TpRequest request = gson.fromJson(message.getMessage(), TpRequest.class);
        plugin.getTeleportManager().getTpRequests().put(request.getPlayerTo(), request);
    }

    public void consumeRemoveTpRequestMessage(Message message) {
        TpRequest request = gson.fromJson(message.getMessage(), TpRequest.class);
        plugin.getTeleportManager().getTpRequests().remove(request.getPlayerTo(), request);
    }

    private void consumeTeleportToLocationBukkitMessage(Message message) {
        TeleportToLocation ttl = gson.fromJson(message.getMessage(), TeleportToLocation.class);
        plugin.getTeleportManager().performTeleportToLocation(ttl);
    }

    private void consumeTeleportToPlayerBukkitMessage(Message message) {
        TeleportToPlayer ttp = gson.fromJson(message.getMessage(), TeleportToPlayer.class);
        plugin.getTeleportManager().performTeleportToPlayer(ttp);
    }

}