package fr.iban.bungeecore.listeners;

import java.util.List;

import fr.iban.bungeecore.CoreBungeePlugin;
import io.github.waterfallmc.waterfall.event.ProxyDefineCommandsEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CommandListener implements Listener {

	private CoreBungeePlugin plugin;

	public CommandListener(CoreBungeePlugin coreBungeePlugin) {
		this.plugin = coreBungeePlugin;
	}
	
	@EventHandler
	public void onCommandDefine(ProxyDefineCommandsEvent e) {
		ProxiedPlayer player = (ProxiedPlayer) e.getReceiver();
		
		if(player.hasPermission("spartacube.admin")) {
			return;
		}
				
		List<String> allowed = plugin.getConfiguration().getStringList("tabcomplete.global");
		
		if(player.hasPermission("spartacube.moderation")) {
			allowed.addAll(plugin.getConfiguration().getStringList("tabcomplete.moderation"));
		}
		
		e.getCommands().entrySet().removeIf(cmd -> !allowed.contains(cmd.getKey()));
	}

}
