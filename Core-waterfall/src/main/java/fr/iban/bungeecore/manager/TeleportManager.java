package fr.iban.bungeecore.manager;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import fr.iban.common.messaging.CoreChannel;
import fr.iban.common.teleport.*;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.utils.ChatUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TeleportManager {

    private final CoreBungeePlugin plugin;
    private final Map<UUID, SLocation> deathLocations = new HashMap<>();
    private final List<UUID> pendingTeleports = new ArrayList<>();
    private final ListMultimap<UUID, TpRequest> tpRequests = ArrayListMultimap.create();


    public TeleportManager(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }

    public void teleport(ProxiedPlayer player, SLocation location) {
        ProxyServer proxy = plugin.getProxy();
        ServerInfo targetServer = proxy.getServerInfo(location.getServer());

        if (targetServer.getName().equals(player.getServer().getInfo().getName())) {
            plugin.getMessagingManager().sendMessage("TeleportToLocationBukkit", new TeleportToLocation(player.getUniqueId(), location));
        } else {
            proxy.getScheduler().runAsync(plugin, () -> player.connect(targetServer, (connected, throwable) -> {
                if (connected) {
                    plugin.getMessagingManager().sendMessage("TeleportToLocationBukkit", new TeleportToLocation(player.getUniqueId(), location));
                }
            }));
        }
    }

    public void delayedTeleport(ProxiedPlayer player, SLocation location, int delay) {
        if (player.hasPermission("spartacube.tp.instant")) {
            teleport(player, location);
            return;
        }

        player.sendMessage(TextComponent.fromLegacyText("§aTéléportation dans " + delay + " secondes. §cNe bougez pas !"));
        if (isTeleportWaiting(player)) {
            player.sendMessage(TextComponent.fromLegacyText("§cUne seule téléportation à la fois !"));
            return;
        }

        setTeleportWaiting(player);

        plugin.getProxy().getScheduler().schedule(plugin, () -> {
            if (isTeleportWaiting(player)) {
                teleport(player, location);
                removeTeleportWaiting(player.getUniqueId());
            }
        }, delay, TimeUnit.SECONDS);
    }

    public void teleport(ProxiedPlayer player, ProxiedPlayer target) {
        ProxyServer proxy = plugin.getProxy();
        ServerInfo targetServer = target.getServer().getInfo();

        if (target == null) {
            player.sendMessage(TextComponent.fromLegacyText("§cLe joueur auquel vous souhaitez vous téléporter n'est pas en ligne."));
            return;
        }

        if (targetServer.getName().equals(player.getServer().getInfo().getName())) {
            plugin.getMessagingManager().sendMessage("TeleportToPlayerBukkit", new TeleportToPlayer(player.getUniqueId(), target.getUniqueId()));
        } else {
            proxy.getScheduler().runAsync(plugin, () -> player.connect(targetServer, (connected, throwable) -> {
                if (connected) {
                    plugin.getMessagingManager().sendMessage("TeleportToPlayerBukkit", new TeleportToPlayer(player.getUniqueId(), target.getUniqueId()));
                }
            }));
        }
    }


    public void delayedTeleport(ProxiedPlayer player, ProxiedPlayer target, int delay) {
        player.sendMessage(TextComponent.fromLegacyText("§aTéléportation dans " + delay + " secondes. §cNe bougez pas !"));
        if (isTeleportWaiting(player)) {
            player.sendMessage(TextComponent.fromLegacyText("§cUne seule téléportation à la fois !"));
            return;
        }

        setTeleportWaiting(player);

        plugin.getProxy().getScheduler().schedule(plugin, () -> {
            if (isTeleportWaiting(player)) {
                teleport(player, target);
                removeTeleportWaiting(player.getUniqueId());
            }
        }, delay, TimeUnit.SECONDS);
    }

    public void sendTeleportRequest(ProxiedPlayer from, ProxiedPlayer to) {
        from.sendMessage(TextComponent.fromLegacyText("§aRequête de téléportation envoyée, en attente d'une réponse..."));
        String bar = "§7§m---------------------------------------------";
        BaseComponent[] accept = new ComponentBuilder("§aACCEPTER").event(ChatUtils.getShowTextHoverEvent("§aCliquez pour accepter la demande")).event(ChatUtils.getCommandClickEvent("/tpyes " + from.getName())).create();
        BaseComponent[] deny = new ComponentBuilder("§cREFUSER").event(ChatUtils.getShowTextHoverEvent("§cCliquez pour refuser la demande")).event(ChatUtils.getCommandClickEvent("/tpno " + from.getName())).create();

        to.sendMessage(TextComponent.fromLegacyText(bar));
        to.sendMessage(TextComponent.fromLegacyText("§6" + from.getName() + "§f souhaite se téléporter à vous."));
        to.sendMessage(new ComponentBuilder("§fVous pouvez ").append(accept).append(TextComponent.fromLegacyText(" ou ")).event((HoverEvent) null).append(deny).append(TextComponent.fromLegacyText(".")).event((HoverEvent) null).create());
        to.sendMessage(TextComponent.fromLegacyText(bar));

        //Retirer la requête déjà existante si il y en a une.
        TpRequest req = getTpRequestFrom(from, to);
        if (req != null) {
            removeTpRequest(to.getUniqueId(), req);
        }

        addTpRequest(to.getUniqueId(), new TpRequest(from.getUniqueId(), to.getUniqueId(), RequestType.TP));

        plugin.getProxy().getScheduler().schedule(plugin, () -> {
            TpRequest req2 = getTpRequestFrom(from, to);
            if (req2 != null) {
                removeTpRequest(to.getUniqueId(), req2);
                from.sendMessage(TextComponent.fromLegacyText("§cVotre requête de téléportation envoyée à " + to.getName() + " a expiré."));
            }
        }, 2, TimeUnit.MINUTES);

    }

    public void sendTeleportHereRequest(ProxiedPlayer from, ProxiedPlayer to) {
        from.sendMessage(TextComponent.fromLegacyText("§aRequête de téléportation envoyée, en attente d'une réponse..."));
        String bar = "§7§m---------------------------------------------";
        BaseComponent[] accept = new ComponentBuilder("§aACCEPTER").event(ChatUtils.getShowTextHoverEvent("§aCliquez pour accepter la demande")).event(ChatUtils.getCommandClickEvent("/tpyes " + from.getName())).create();
        BaseComponent[] deny = new ComponentBuilder("§cREFUSER").event(ChatUtils.getShowTextHoverEvent("§cCliquez pour refuser la demande")).event(ChatUtils.getCommandClickEvent("/tpno " + from.getName())).create();

        to.sendMessage(TextComponent.fromLegacyText(bar));
        to.sendMessage(TextComponent.fromLegacyText("§6" + from.getName() + "§f souhaite vous téléporter à lui/elle."));
        to.sendMessage(new ComponentBuilder("§fVous pouvez ").append(accept).append(TextComponent.fromLegacyText(" ou ")).event((HoverEvent) null).append(deny).append(TextComponent.fromLegacyText(".")).event((HoverEvent) null).create());
        to.sendMessage(TextComponent.fromLegacyText(bar));

        //Retirer la requête déjà existante si il y en a une.
        TpRequest req = getTpRequestFrom(to, from);
        if (req != null) {
            removeTpRequest(to.getUniqueId(), req);
        }

        addTpRequest(to.getUniqueId(), new TpRequest(from.getUniqueId(), to.getUniqueId(), RequestType.TPHERE));

        plugin.getProxy().getScheduler().schedule(plugin, () -> {
            TpRequest req2 = getTpRequestFrom(to, from);
            if (req2 != null) {
                getTpRequests(to).remove(req2);
                from.sendMessage(TextComponent.fromLegacyText("§cVotre requête de téléportation envoyée à " + to.getName() + " a expiré."));
            }
        }, 2, TimeUnit.MINUTES);
    }

    public Map<UUID, SLocation> getDeathLocations() {
        return deathLocations;
    }

    public List<UUID> getPendingTeleports() {
        return pendingTeleports;
    }

    public void setTeleportWaiting(ProxiedPlayer player) {
        pendingTeleports.add(player.getUniqueId());
        plugin.getMessagingManager().sendMessage(CoreChannel.ADD_PENDING_TP_CHANNEL, player.getUniqueId().toString());
    }

    public void removeTeleportWaiting(UUID uuid) {
        pendingTeleports.remove(uuid);
        plugin.getMessagingManager().sendMessage(CoreChannel.REMOVE_PENDING_TP_CHANNEL, uuid.toString());
    }

    public boolean isTeleportWaiting(ProxiedPlayer player) {
        return pendingTeleports.contains(player.getUniqueId());
    }

    public List<TpRequest> getTpRequests(ProxiedPlayer player) {
        return tpRequests.get(player.getUniqueId());
    }

    public ListMultimap<UUID, TpRequest> getTpRequests() {
        return tpRequests;
    }

    public TpRequest getTpRequestFrom(ProxiedPlayer player, ProxiedPlayer from) {
        for (TpRequest request : getTpRequests(player)) {
            if (request.getPlayerFrom().equals(from.getUniqueId())) {
                return request;
            }
        }
        return null;
    }

    public void addTpRequest(UUID uuid, TpRequest tpRequest) {
        tpRequests.put(uuid, tpRequest);
        plugin.getMessagingManager().sendMessage(CoreChannel.ADD_TP_REQUEST_CHANNEL, tpRequest);
    }

    public void removeTpRequest(UUID uuid, TpRequest tpRequest) {
        tpRequests.remove(uuid, tpRequest);
        plugin.getMessagingManager().sendMessage(CoreChannel.REMOVE_TP_REQUEST_CHANNEL, tpRequest);
    }
}
