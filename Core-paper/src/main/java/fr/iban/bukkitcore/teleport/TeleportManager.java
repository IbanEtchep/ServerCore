package fr.iban.bukkitcore.teleport;

import fr.iban.bukkitcore.utils.ChatUtils;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.teleport.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import org.redisson.api.RList;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TeleportManager {

	private CoreBukkitPlugin plugin;

	public TeleportManager(CoreBukkitPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Teleport player to SLocation
	 * @param player
	 * @param sloc
	 */
	public void teleport(Player player, SLocation sloc) {
		teleport(player, sloc, 0);
	}

	/**
	 * Delayed teleport player to SLocation.
	 * @param player
	 * @param sloc
	 * @param delay delay in seconds
	 */
	public void teleport(Player player, SLocation sloc, int delay) {
		setLastLocation(player.getUniqueId());

		if (delay <= 0) {
			plugin.getRedisClient().getTopic("TpToSLoc").publish(new TeleportToLocation(player.getUniqueId(), sloc));
		} else {
			plugin.getRedisClient().getTopic("TpToSLoc").publish(new TeleportToLocation(player.getUniqueId(), sloc, delay));
		}
	}

	/**
	 * Teleport player to another player
	 * @param uuid - player's uuid
	 * @param target
	 */
	public void teleport(UUID uuid, UUID target) {
		teleport(uuid, target, 0);
	}

	/**
	 * Delayed teleport player to another player
	 * @param uuid - player's uuid
	 * @param target - target player's uuid
	 * @param delay
	 */
	public void teleport(UUID uuid, UUID target, int delay) {
		setLastLocation(uuid);
		if (delay <= 0 || Bukkit.getPlayer(uuid).hasPermission("bungeeteleport.instant")) {
			plugin.getRedisClient().getTopic("TpToPlayer").publish(new TeleportToPlayer(uuid, target));
		} else {
			plugin.getRedisClient().getTopic("TpToPlayer").publish(new TeleportToPlayer(uuid, target, delay));
		}
	}

	private void setLastLocation(UUID player){
		Essentials essentials = plugin.getEssentials();

		if (essentials != null) {
			essentials.getUser(player).setLastLocation();
		}
	}

	/**
	 * Send teleport request to player.
	 * @param from
	 * @param to
	 */
	public void sendTeleportRequest(UUID from, UUID to, RequestType type) {
		plugin.getRedisClient().getTopic("TpRequest").publish(new TpRequest(from, to, type));
	}

	/**
	 * Send teleport here request to player.
	 * @param from
	 * @param to
	 */
	public void sendTeleportHereRequest(UUID from, UUID to) {

	}

	public RList<Object> getTpRequests(Player player) {
		return RedisAccess.getInstance().getRedissonClient().getListMultimap("TeleportRequests").get(player.getUniqueId());
	}

	private boolean isTeleportWaiting(Player player) {
		return RedisAccess.getInstance().getRedissonClient().getMap("PendingTeleports").containsKey(player.getUniqueId());
	}

	public TpRequest getTpRequestFrom(Player player, UUID from) {
		for (Object object : getTpRequests(player)) {
			TpRequest request = (TpRequest)object;
			if(request.getPlayerFrom().equals(from)) {
				return request;
			}
		}
		return null;
	}
}