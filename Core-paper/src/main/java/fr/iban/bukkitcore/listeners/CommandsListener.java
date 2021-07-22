package fr.iban.bukkitcore.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import fr.iban.bukkitcore.CoreBukkitPlugin;

public class CommandsListener implements Listener {
	
	private CoreBukkitPlugin plugin;

	public CommandsListener(CoreBukkitPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onCommandSend(PlayerCommandSendEvent e) {
		Player player = (Player) e.getPlayer();
		
		if(player.hasPermission("spartacube.admin")) {
			return;
		}
				
		List<String> allowed = plugin.getConfig().getStringList("tabcomplete.global");
		
		if(player.hasPermission("spartacube.moderation")) {
			allowed.addAll(plugin.getConfig().getStringList("tabcomplete.moderation"));
		}
	
		e.getCommands().clear();
		e.getCommands().addAll(allowed);
	}

}
