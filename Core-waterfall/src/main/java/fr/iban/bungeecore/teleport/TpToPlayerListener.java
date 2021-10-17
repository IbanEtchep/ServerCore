package fr.iban.bungeecore.teleport;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.teleport.TeleportToLocation;
import fr.iban.common.teleport.TeleportToPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.redisson.api.listener.MessageListener;

public class TpToPlayerListener implements MessageListener<TeleportToPlayer> {

	private CoreBungeePlugin plugin;

	public TpToPlayerListener(CoreBungeePlugin coreBungeePlugin) {
		this.plugin = coreBungeePlugin;
	}

	@Override
	public void onMessage(String channel, TeleportToPlayer ttp) {
		plugin.getLogger().info("TpToPlayer");
		ProxiedPlayer player = plugin.getProxy().getPlayer(ttp.getUuid());
		if(player != null) {
			if(ttp.getDelay() == 0) {
				plugin.getTeleportManager().teleport(player, plugin.getProxy().getPlayer(ttp.getTargetId()));
			}else {
				plugin.getTeleportManager().delayedTeleport(player, plugin.getProxy().getPlayer(ttp.getTargetId()), Math.abs(ttp.getDelay()));
			}
		}
	}
}
