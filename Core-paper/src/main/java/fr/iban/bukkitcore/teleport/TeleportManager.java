package fr.iban.bukkitcore.teleport;

import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.teleport.SLocation;
import fr.iban.common.teleport.TeleportToLocation;

public class TeleportManager {
	
	private CoreBukkitPlugin plugin;

	public TeleportManager(CoreBukkitPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void teleport(Player player, SLocation sloc) {
		teleport(player, sloc, 0);
	}
	
	public void teleport(Player player, SLocation sloc, int delay) {
		Essentials essentials = plugin.getEssentials();
		
		if(essentials != null) {
			essentials.getUser(player).setLastLocation();
		}
		
		if(delay <= 0) {
			plugin.getRedisClient().getTopic("TeleportToSLoc").publish(new TeleportToLocation(player.getUniqueId(), sloc));
		}else {
			plugin.getRedisClient().getTopic("TeleportToSLoc").publish(new TeleportToLocation(player.getUniqueId(), sloc, delay));
		}
	}
}