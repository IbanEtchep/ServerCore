package fr.iban.bungeecore.teleport;

import org.redisson.api.listener.MessageListener;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.teleport.DeathLocation;

public class DeathLocationListener implements MessageListener<DeathLocation> {
	
	private CoreBungeePlugin plugin;
	

	public DeathLocationListener(CoreBungeePlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onMessage(CharSequence channel, DeathLocation dl) {
		plugin.getTeleportManager().getDeathLocations().put(dl.getUuid(), dl.getLocation());
	}
}
