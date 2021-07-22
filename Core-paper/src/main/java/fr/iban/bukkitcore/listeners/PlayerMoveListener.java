package fr.iban.bukkitcore.listeners;

import java.util.concurrent.CompletableFuture;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.redisson.api.RedissonClient;

import fr.iban.common.data.redis.RedisAccess;

public class PlayerMoveListener implements Listener {

	private RedissonClient redis = RedisAccess.getInstance().getRedissonClient();

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		final Location from = e.getFrom();
		final Location to = e.getTo();
		
		int x = Math.abs(from.getBlockX() - to.getBlockX());
		int y = Math.abs(from.getBlockY() - to.getBlockY());
		int z = Math.abs(from.getBlockZ() - to.getBlockZ());

		if (x == 0 && y == 0 && z == 0) return;

		CompletableFuture.runAsync(() -> {
			if(isTeleportWaiting(player)) {
				redis.getMap("PendingTeleports").fastPut(player.getUniqueId(), Boolean.FALSE);
				player.sendMessage("§cVous avez bougé, téléportation annulée.");
			}
		});
	}

	private boolean isTeleportWaiting(Player player) {
		return redis.getMap("PendingTeleports").containsKey(player.getUniqueId()) && ((Boolean)redis.getMap("PendingTeleports").get(player.getUniqueId())).booleanValue();
	}
}
