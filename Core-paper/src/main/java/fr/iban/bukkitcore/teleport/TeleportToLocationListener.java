package fr.iban.bukkitcore.teleport;

import fr.iban.bukkitcore.utils.SLocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.redisson.api.listener.MessageListener;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.teleport.SLocation;
import fr.iban.common.teleport.TeleportToLocation;

public class TeleportToLocationListener implements MessageListener<TeleportToLocation> {

	@Override
	public void onMessage(String channel, TeleportToLocation ttl) {
			if(!ttl.getLocation().getServer().equals(CoreBukkitPlugin.getInstance().getServerName())) {
				return;
			}

			SLocation sloc = ttl.getLocation();
			Location loc = SLocationUtils.getLocation(sloc);

			new BukkitRunnable() {

				int count = 0;

				@Override
				public void run() {

					Player player = Bukkit.getPlayer(ttl.getUuid());
					
					if(player != null) {
						tp(player, loc);
						cancel();
					}
					
					count++;
					
					if(count > 20) {
						cancel();
					}

				}
			}.runTaskTimer(CoreBukkitPlugin.getInstance(), 1L, 1L);
	}


	private void tp(Player player, Location loc) {
		player.sendActionBar("§aChargement des chunks...");
		player.teleportAsync(loc).thenAccept(result -> {
			if (result.booleanValue()) {
				player.sendActionBar("§aTéléporation effectuée !");
			} else {
				player.sendActionBar("§cLa téléportation a échoué !");
			}
		});
	}

}
