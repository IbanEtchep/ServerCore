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
        if (!ttl.getLocation().getServer().equals(CoreBukkitPlugin.getInstance().getServerName())) {
            return;
        }

        SLocation sloc = ttl.getLocation();
        Location loc = SLocationUtils.getLocation(sloc);

        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {

                Player player = Bukkit.getPlayer(ttl.getUuid());

                if (player != null) {
                    tp(player, loc);
                    cancel();
                }

                count++;

                if (count > 20) {
                    cancel();
                }

            }
        }.runTaskTimer(CoreBukkitPlugin.getInstance(), 1L, 1L);
    }

    private void consumeTeleportToPlayerBukkitMessage(Message message) {
        TeleportToPlayer ttp = gson.fromJson(message.getMessage(), TeleportToPlayer.class);
        Player target = Bukkit.getPlayer(ttp.getTargetId());

        if (target == null) {
            return;
        }

        new BukkitRunnable() {

            @Override
            public void run() {

                Player target = Bukkit.getPlayer(ttp.getTargetId());
                Player player = Bukkit.getPlayer(ttp.getUuid());

                if (target == null) {
                    cancel();
                    return;
                }

                if (player != null) {
                    tp(player, target.getLocation());
                    cancel();
                }

            }
        }.runTaskTimer(CoreBukkitPlugin.getInstance(), 1L, 1L);
    }

    private void tp(Player player, Location loc) {
        player.sendActionBar("§aChargement des chunks...");
        player.teleportAsync(loc).thenAccept(result -> {
            if (result) {
                player.sendActionBar("§aTéléporation effectuée !");
            } else {
                player.sendActionBar("§cLa téléportation a échoué !");
            }
        });
    }
}