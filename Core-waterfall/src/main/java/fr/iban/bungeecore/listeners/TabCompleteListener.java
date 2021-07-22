package fr.iban.bungeecore.listeners;

import fr.iban.bungeecore.CoreBungeePlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class TabCompleteListener implements Listener {
	
	
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabComplete(TabCompleteEvent e)
    {
        String partialPlayerName = e.getCursor().toLowerCase();

        int lastSpaceIndex = partialPlayerName.lastIndexOf(' ');
        if (lastSpaceIndex >= 0)
        {
            partialPlayerName = partialPlayerName.substring(lastSpaceIndex + 1);
        }

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers())
        {
            if (p.getName().toLowerCase().startsWith(partialPlayerName))
            {
                e.getSuggestions().add(p.getName());
            }
        }
    
		ProxiedPlayer player = (ProxiedPlayer) e.getSender();
		Configuration config = CoreBungeePlugin.getInstance().getConfiguration();
		
		if(e.getCursor() != "/")
			return;
		
		if(player.hasPermission("group.administrateur"))
			return;
		
		if(player.hasPermission("group.default")) {
			e.getSuggestions().clear();
			e.getSuggestions().addAll(config.getStringList("tabcomplete.visiteur"));
		}
		if(player.hasPermission("group.support")) {
			e.getSuggestions().addAll(config.getStringList("tabcomplete.support"));
		}
		if(player.hasPermission("group.moderateur")) {
			e.getSuggestions().addAll(config.getStringList("tabcomplete.moderateur"));
		}
	}
	

}
