package fr.iban.bungeecore.teleport;

import org.redisson.api.listener.MessageListener;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.teleport.TeleportToLocation;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TpToSLocListener implements MessageListener<Object> {

	private CoreBungeePlugin plugin;

	public TpToSLocListener(CoreBungeePlugin coreBungeePlugin) {
		this.plugin = coreBungeePlugin;
	}

	@Override
	public void onMessage(String channel, Object msg) {
		if(msg instanceof TeleportToLocation) {
			TeleportToLocation ttl = (TeleportToLocation)msg;
			ProxiedPlayer player = plugin.getProxy().getPlayer(ttl.getUuid());
			
			if(player != null) {
				if(ttl.getDelay() == 0) {
					plugin.getTeleportManager().teleport(player, ttl.getLocation());
				}else {
					plugin.getTeleportManager().delayedTeleport(player, ttl.getLocation(), Math.abs(ttl.getDelay()));
				}
			}
		}
	}

}
