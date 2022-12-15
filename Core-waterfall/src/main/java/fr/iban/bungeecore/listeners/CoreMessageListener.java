package fr.iban.bungeecore.listeners;

import com.google.gson.Gson;
import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.event.CoreMessageEvent;
import fr.iban.common.messaging.CoreChannel;
import fr.iban.common.messaging.Message;
import fr.iban.common.messaging.message.EventAnnounce;
import fr.iban.common.messaging.message.PlayerBoolean;
import fr.iban.common.messaging.message.PlayerSLocationMessage;
import fr.iban.common.teleport.RequestType;
import fr.iban.common.teleport.TeleportToLocation;
import fr.iban.common.teleport.TeleportToPlayer;
import fr.iban.common.teleport.TpRequest;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class CoreMessageListener implements Listener {

    private final CoreBungeePlugin plugin;
    private final Gson gson = new Gson();

    public CoreMessageListener(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCoreMessage(CoreMessageEvent e) {
        Message message = e.getMessage();

        switch (e.getMessage().getChannel()) {
            case "EventAnnounce" -> consumeAnnounceMessage(message);
            case "DeathLocation" -> consumeDeathLocationMessage(message);
            case "LastRTPLocation" -> consumeLastRTPLocationMessage(message);
            case "TeleportToLocationBungee" -> consumeTeleportToLocationBungeeMessage(message);
            case "TeleportToPlayerBungee" -> consumeTeleportToPlayerBungeeMessage(message);
            case "TeleportRequestBungee" -> consumeTeleportRequestBungeeMessage(message);
            case CoreChannel.SYNC_ACCOUNT_CHANNEL ->
                    plugin.getAccountManager().reloadAccount(UUID.fromString(message.getMessage()));
            case CoreChannel.REMOVE_PENDING_TP_CHANNEL ->
                    plugin.getTeleportManager().getPendingTeleports().remove(UUID.fromString(message.getMessage()));
            case CoreChannel.REMOVE_TP_REQUEST_CHANNEL -> consumeRemoveTpRequestMessage(message);
            case CoreChannel.VANISH_STATUS_CHANGE_CHANNEL -> consumeVanishStatusChangeMessage(message);
        }
    }

    public void consumeRemoveTpRequestMessage(Message message) {
        TpRequest request = gson.fromJson(message.getMessage(), TpRequest.class);
        plugin.getTeleportManager().getTpRequests().remove(request.getPlayerTo(), request);
    }

    private void consumeTeleportToLocationBungeeMessage(Message message) {
        TeleportToLocation teleportToLocation = gson.fromJson(message.getMessage(), TeleportToLocation.class);
        ProxiedPlayer player = plugin.getProxy().getPlayer(teleportToLocation.getUuid());
        if (player != null) {
            if (teleportToLocation.getDelay() == 0) {
                plugin.getTeleportManager().teleport(player, teleportToLocation.getLocation());
            } else {
                plugin.getTeleportManager().delayedTeleport(player, teleportToLocation.getLocation(), Math.abs(teleportToLocation.getDelay()));
            }
        }
    }

    private void consumeTeleportToPlayerBungeeMessage(Message message) {
        TeleportToPlayer teleportToPlayer = gson.fromJson(message.getMessage(), TeleportToPlayer.class);
        ProxiedPlayer player = plugin.getProxy().getPlayer(teleportToPlayer.getUuid());
        if (player != null) {
            if (teleportToPlayer.getDelay() == 0 || player.hasPermission("bungeeteleport.instant")) {
                plugin.getTeleportManager().teleport(player, plugin.getProxy().getPlayer(teleportToPlayer.getTargetId()));
            } else {
                plugin.getTeleportManager().delayedTeleport(player, plugin.getProxy().getPlayer(teleportToPlayer.getTargetId()), Math.abs(teleportToPlayer.getDelay()));
            }
        }
    }

    private void consumeTeleportRequestBungeeMessage(Message message) {
        TpRequest request = gson.fromJson(message.getMessage(), TpRequest.class);
        ProxiedPlayer from = plugin.getProxy().getPlayer(request.getPlayerFrom());
        ProxiedPlayer to = plugin.getProxy().getPlayer(request.getPlayerTo());

        if (request.getRequestType() == RequestType.TP) {
            plugin.getTeleportManager().sendTeleportRequest(from, to);
        } else if (request.getRequestType() == RequestType.TPHERE) {
            plugin.getTeleportManager().sendTeleportHereRequest(from, to);
        }
    }

    private void consumeDeathLocationMessage(Message message) {
        PlayerSLocationMessage deathLocation = gson.fromJson(message.getMessage(), PlayerSLocationMessage.class);
        plugin.getTeleportManager().getDeathLocations().put(deathLocation.getUuid(), deathLocation.getLocation());
    }

    private void consumeLastRTPLocationMessage(Message message) {
        PlayerSLocationMessage lastRTPLocation = gson.fromJson(message.getMessage(), PlayerSLocationMessage.class);
        plugin.getTeleportManager().getLastRTPLocations().put(lastRTPLocation.getUuid(), lastRTPLocation.getLocation());
    }

    private void consumeAnnounceMessage(Message message) {
        EventAnnounce announce = gson.fromJson(message.getMessage(), EventAnnounce.class);
        String key = announce.getName() + ":" + announce.getArena();

        if (announce.getLocation() == null) {
            if (plugin.getCurrentEvents().containsKey(key)) {
                plugin.getCurrentEvents().remove(key);
                return;
            }
        }

        if (!plugin.getCurrentEvents().containsKey(key)) {
            plugin.getCurrentEvents().put(key, announce.getLocation());
            plugin.getProxy().broadcast(new ComponentBuilder(getLine(30)).create());
            plugin.getProxy().broadcast(new ComponentBuilder("§5§l" + announce.getHostName() + " a lancé un event " + announce.getName()).create());
        }

        plugin.getProxy().broadcast(new ComponentBuilder(getCentered("§f §5§l" + announce.getName() + " ", 30)).create());
        plugin.getProxy().broadcast(TextComponent.fromLegacyText(announce.getDesc()));
        plugin.getProxy().broadcast(TextComponent.fromLegacyText("§fArene : " + announce.getArena()));
        plugin.getProxy().broadcast(new ComponentBuilder("§d§lCliquez pour rejoindre").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/joinevent " + key)).create());
        plugin.getProxy().broadcast(new ComponentBuilder(getLine(30)).create());
    }


    /*
    UTILS
     */

    private String getLine(int length) {
        StringBuilder sb = new StringBuilder("§8§m");
        for (int i = 0; i < length; i++) {
            sb.append("-");
        }
        return sb.toString();
    }

    private String getCentered(String string, int lineLength) {
        StringBuilder sb = new StringBuilder("§8§m");
        int line = (lineLength - string.length()) / 2 + 2;
        for (int i = 0; i < line; i++) {
            sb.append("-");
        }
        sb.append(string);
        sb.append("§8§m");
        for (int i = 0; i < line; i++) {
            sb.append("-");
        }
        return sb.toString();
    }

    private void consumeVanishStatusChangeMessage(Message message) {
        PlayerBoolean playerBoolean = message.getMessage(PlayerBoolean.class);
        plugin.getPlayerManager().setVanished(playerBoolean.getUuid(), playerBoolean.isValue());
    }

}
