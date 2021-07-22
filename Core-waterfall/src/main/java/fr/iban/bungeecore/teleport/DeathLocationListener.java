package fr.iban.bungeecore.teleport;

import org.redisson.api.listener.MessageListener;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.teleport.DeathLocation;

public class DeathLocationListener implements MessageListener<Object> {
	
	private CoreBungeePlugin plugin;
	

	public DeathLocationListener(CoreBungeePlugin plugin) {
		this.plugin = plugin;
	}


	@Override
	public void onMessage(String channel, Object msg) {
		if(msg instanceof DeathLocation) {
			DeathLocation dl = (DeathLocation)msg;
			plugin.getTeleportManager().getDeathLocations().put(dl.getUuid(), dl.getLocation());
		}
	}

}
