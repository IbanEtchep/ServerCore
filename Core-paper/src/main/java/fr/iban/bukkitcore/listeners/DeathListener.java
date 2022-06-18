package fr.iban.bukkitcore.listeners;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.messaging.message.DeathLocation;
import fr.iban.common.teleport.SLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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
		plugin.getMessagingManager().sendMessageAsync("DeathLocation", new DeathLocation(e.getEntity().getUniqueId(), new SLocation(plugin.getServerName(), loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw())));
	}

}
