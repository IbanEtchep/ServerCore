package fr.iban.bungeecore.listeners;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.utils.HexColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {

	private CoreBungeePlugin plugin;

	public ProxyPingListener(CoreBungeePlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPing(ProxyPingEvent e) {
		ServerPing serverPing = e.getResponse();
		Players players = serverPing.getPlayers();
		players.setMax(100);
		serverPing.setDescriptionComponent(new TextComponent(HexColor.translateColorCodes(plugin.getConfiguration().getString("motd"))));
		serverPing.setPlayers(players);
	}

}
