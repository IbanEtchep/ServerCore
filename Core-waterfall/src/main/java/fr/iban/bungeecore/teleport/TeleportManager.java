package fr.iban.bungeecore.teleport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.utils.ChatUtils;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.teleport.SLocation;
import fr.iban.common.teleport.TeleportToLocation;
import fr.iban.common.teleport.TeleportToPlayer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TeleportManager {

	private CoreBungeePlugin instance;
	private RedissonClient redis = RedisAccess.getInstance().getRedissonClient();
	
	private Map<UUID, SLocation> deathLocations = new HashMap<>();

	public TeleportManager(CoreBungeePlugin instance) {
		this.instance = instance;
	}

	public void teleport(ProxiedPlayer player, SLocation location) {
		ProxyServer proxy = instance.getProxy();
		ServerInfo targetServer = proxy.getServerInfo(location.getServer());

		if(targetServer.getName().equals(player.getServer().getInfo().getName())) {
			redis.getTopic("TeleportToLocation").publish(new TeleportToLocation(player.getUniqueId(), location));
		}else {
			proxy.getScheduler().runAsync(instance, () ->  player.connect(targetServer, (connected, throwable) -> {
				if (connected.booleanValue()) {
					redis.getTopic("TeleportToLocation").publish(new TeleportToLocation(player.getUniqueId(), location));
				}
			}));
		}
	}
	
	public void delayedTeleport(ProxiedPlayer player, SLocation location, int delay) {
		if(player.hasPermission("spartacube.tp.instant")) {
			teleport(player, location);
			return;
		}
		
		player.sendMessage(TextComponent.fromLegacyText("§aTéléportation dans " + delay + " secondes. §cNe bougez pas !"));
		if(isTeleportWaiting(player)) {
			player.sendMessage(TextComponent.fromLegacyText("§cUne seule téléportation à la fois !"));
			return;
		}

		redis.getMap("PendingTeleports").fastPut(player.getUniqueId(), Boolean.TRUE);

		instance.getProxy().getScheduler().schedule(instance, () -> {
			if(isTeleportWaiting(player) && ((Boolean)redis.getMap("PendingTeleports").get(player.getUniqueId())).booleanValue()) {
				teleport(player, location);
			}
			redis.getMap("PendingTeleports").fastRemove(player.getUniqueId());
		}, delay, TimeUnit.SECONDS);	
	}

	public void teleport(ProxiedPlayer player, ProxiedPlayer target) {
		ProxyServer proxy = instance.getProxy();
		ServerInfo targetServer = target.getServer().getInfo();

		if(targetServer.getName().equals(player.getServer().getInfo().getName())) {
			redis.getTopic("TeleportToPlayer").publish(new TeleportToPlayer(player.getUniqueId(), target.getUniqueId()));
		}else {
			proxy.getScheduler().runAsync(instance, () ->  player.connect(targetServer, (connected, throwable) -> {
				if (connected.booleanValue()) {
					redis.getTopic("TeleportToPlayer").publish(new TeleportToPlayer(player.getUniqueId(), target.getUniqueId()));
				}
			}));
		}
	}
	
	
	public void delayedTeleport(ProxiedPlayer player, ProxiedPlayer target, int delay) {
		player.sendMessage(TextComponent.fromLegacyText("§aTéléportation dans " + delay + " secondes. §cNe bougez pas !"));
		if(isTeleportWaiting(player)) {
			player.sendMessage(TextComponent.fromLegacyText("§cUne seule téléportation à la fois !"));
			return;
		}		

		redis.getMap("PendingTeleports").fastPut(player.getUniqueId(), Boolean.TRUE);

		instance.getProxy().getScheduler().schedule(instance, () -> {
			if(isTeleportWaiting(player) && ((Boolean)redis.getMap("PendingTeleports").get(player.getUniqueId())).booleanValue()) {
				teleport(player, target);
			}
			redis.getMap("PendingTeleports").fastRemove(player.getUniqueId());
		}, delay, TimeUnit.SECONDS);
	}

	public void sendTeleportRequest(ProxiedPlayer from, ProxiedPlayer to) {
		from.sendMessage(TextComponent.fromLegacyText("§aRequête de téléportation envoyée, en attente d'une réponse..."));
		String bar = "§7§m---------------------------------------------";
		BaseComponent[] accept = new ComponentBuilder("§aACCEPTER").event(ChatUtils.getShowTextHoverEvent("§aCliquez pour accepter la demande")).event(ChatUtils.getCommandClickEvent("/tpyes " + from.getName())).create();
		BaseComponent[] deny = new ComponentBuilder("§cREFUSER").event(ChatUtils.getShowTextHoverEvent("§cCliquez pour refuser la demande")).event(ChatUtils.getCommandClickEvent("/tpno " + from.getName())).create();
		
		to.sendMessage(TextComponent.fromLegacyText(bar));
		to.sendMessage(TextComponent.fromLegacyText("§6"+ from.getName() + "§f souhaite se téléporter à vous."));
		to.sendMessage(new ComponentBuilder("§fVous pouvez ").append(accept).append(TextComponent.fromLegacyText(" ou ")).event((HoverEvent)null).append(deny).append(TextComponent.fromLegacyText(".")).event((HoverEvent)null).create());
		to.sendMessage(TextComponent.fromLegacyText(bar));
		
		//Retirer la requête déjà existante si il y en a une.
		TpRequest req = getTpRequestFrom(from, to);
		if(req != null) {
			getTpRequests(to).remove(req);
		}
		
		redis.getListMultimap("TeleportRequests").put(to.getUniqueId(), new TpRequest(from.getUniqueId(), RequestType.TP));
		
		instance.getProxy().getScheduler().schedule(instance, () ->{
			TpRequest req2 = getTpRequestFrom(from, to);
			if(req2 != null) {
				getTpRequests(to).remove(req2);
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
		to.sendMessage(TextComponent.fromLegacyText("§6"+ from.getName() + "§f souhaite vous téléporter à lui/elle."));
		to.sendMessage(new ComponentBuilder("§fVous pouvez ").append(accept).append(TextComponent.fromLegacyText(" ou ")).event((HoverEvent)null).append(deny).append(TextComponent.fromLegacyText(".")).event((HoverEvent)null).create());
		to.sendMessage(TextComponent.fromLegacyText(bar));
		
		//Retirer la requête déjà existante si il y en a une.
		TpRequest req = getTpRequestFrom(to, from);
		if(req != null) {
			getTpRequests(to).remove(req);
		}
		
		redis.getListMultimap("TeleportRequests").put(to.getUniqueId(), new TpRequest(from.getUniqueId(), RequestType.TPHERE));
		
		instance.getProxy().getScheduler().schedule(instance, () ->{
			TpRequest req2 = getTpRequestFrom(to, from);
			if(req2 != null) {
				getTpRequests(to).remove(req2);
				from.sendMessage(TextComponent.fromLegacyText("§cVotre requête de téléportation envoyée à " + to.getName() + " a expiré."));
			}
		}, 2, TimeUnit.MINUTES);	
	}
	
	public RList<Object> getTpRequests(ProxiedPlayer player) {
		return redis.getListMultimap("TeleportRequests").get(player.getUniqueId());
	}

	private boolean isTeleportWaiting(ProxiedPlayer player) {
		return redis.getMap("PendingTeleports").containsKey(player.getUniqueId());
	}
	
	public TpRequest getTpRequestFrom(ProxiedPlayer player, ProxiedPlayer from) {
		for (Object object : getTpRequests(player)) {
			TpRequest request = (TpRequest)object;
			if(request.getPlayerID().equals(from.getUniqueId())) {
				return request;
			}
		}
		return null;
	}

	public Map<UUID, SLocation> getDeathLocations() {
		return deathLocations;
	}

}
