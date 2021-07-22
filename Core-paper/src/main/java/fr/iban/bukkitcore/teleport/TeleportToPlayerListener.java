package fr.iban.bukkitcore.teleport;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.redisson.api.listener.MessageListener;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.teleport.TeleportToPlayer;

public class TeleportToPlayerListener implements MessageListener<Object> {

	@Override
	public void onMessage(String channel, Object msg) {
		if(msg instanceof TeleportToPlayer) {
			TeleportToPlayer ttp = (TeleportToPlayer)msg;
			Player target = Bukkit.getPlayer(ttp.getTargetId());

			if(target == null) {
				return;
			}

			new BukkitRunnable() {
								
				@Override
				public void run() {
					
					Player target = Bukkit.getPlayer(ttp.getTargetId());
					Player player = Bukkit.getPlayer(ttp.getUuid());
					
					if(target == null) {
						cancel();
						return;
					}
					
					if(player != null) {
						tp(player, target);
						cancel();
					}
					
				}
			}.runTaskTimer(CoreBukkitPlugin.getInstance(), 1L, 1L);
		}
	}

	private void tp(Player player, Player target) {
		player.sendActionBar("§aChargement des chunks...");
		player.teleportAsync(target.getLocation()).thenAccept(result -> {
			if (result.booleanValue()) {
				player.sendActionBar("§aTéléporation effectuée !");
			} else {
				player.sendActionBar("§cLa téléportation a échoué !");
			}
		});
	}

}
