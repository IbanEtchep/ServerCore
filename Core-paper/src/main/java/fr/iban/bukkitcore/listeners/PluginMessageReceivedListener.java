package fr.iban.bukkitcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import fr.iban.bukkitcore.utils.PluginMessageHelper;

public class PluginMessageReceivedListener implements PluginMessageListener{

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
		PluginMessageHelper.receivePluginMessage(channel, bytes);
	}

}
