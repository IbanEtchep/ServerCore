package fr.iban.survivalcore.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

import fr.iban.survivalcore.SurvivalCorePlugin;

public class PortalListeners implements Listener {

	private final SurvivalCorePlugin plugin;

	public PortalListeners(SurvivalCorePlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPortalCreate(PortalCreateEvent e) {
		if(e.getReason() == CreateReason.FIRE && e.getWorld().getEnvironment() == Environment.NETHER && !e.getBlocks().isEmpty()) {
			Location location = e.getBlocks().get(0).getLocation();

			if(location.getY() >= 128) {
				plugin.getScheduler().runAtLocationLater(location, task -> {
					for (BlockState block : e.getBlocks()) {
						if(block.getType() == Material.NETHER_PORTAL) {
							block.getBlock().breakNaturally();
							break;
						}
					}
				}, 6000);
			}
		}
	}
}
