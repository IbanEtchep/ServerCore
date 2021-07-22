package fr.iban.bukkitcore.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.teleport.DeathLocation;
import fr.iban.common.teleport.SLocation;

public class DeathListener implements Listener {
	
	private CoreBukkitPlugin plugin;
	
	public DeathListener(CoreBukkitPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		Location loc = player.getLocation();
		
		if(plugin.getServerName() == null) {
			return;
		}
		RedisAccess.getInstance().getRedissonClient().getTopic("DeathLocation").publish(new DeathLocation(e.getEntity().getUniqueId(), new SLocation(plugin.getServerName(), loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw())));
	}

}
