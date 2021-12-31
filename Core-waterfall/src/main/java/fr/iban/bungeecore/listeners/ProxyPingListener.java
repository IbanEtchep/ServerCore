package fr.iban.bungeecore.listeners;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.utils.HexColor;
import fr.iban.common.utils.ArrayUtils;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;
import java.util.Random;

public class ProxyPingListener implements Listener {

	private CoreBungeePlugin plugin;
	private Random random = new Random();

	public ProxyPingListener(CoreBungeePlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPing(ProxyPingEvent e) {
		ServerPing serverPing = e.getResponse();
		Players players = serverPing.getPlayers();
		List<String> motd = plugin.getConfiguration().getStringList("motd");
		players.setMax(100);
		serverPing.setDescriptionComponent(new TextComponent(TextComponent.fromLegacyText(HexColor.translateColorCodes(motd.get(random.nextInt(motd.size()))))));
		serverPing.setPlayers(players);
	}

}
