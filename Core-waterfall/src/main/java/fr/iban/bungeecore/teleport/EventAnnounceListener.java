package fr.iban.bungeecore.teleport;

import org.redisson.api.listener.MessageListener;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.teleport.EventAnnouce;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class EventAnnounceListener implements MessageListener<Object> {

	private CoreBungeePlugin plugin;

	public EventAnnounceListener(CoreBungeePlugin coreBungeePlugin) {
		this.plugin = coreBungeePlugin;
	}

	@Override
	public void onMessage(String channel, Object msg) {
		if(msg instanceof EventAnnouce) {
			EventAnnouce announce = (EventAnnouce)msg;

			String key = announce.getName()+":"+announce.getArena();
			
			if(announce.getLocation() == null) {
				if(plugin.getCurrentEvents().containsKey(key)) {
					plugin.getCurrentEvents().remove(key);
					return;	
				}
			}
			
			if(!plugin.getCurrentEvents().containsKey(key)) {
				plugin.getCurrentEvents().put(key, announce.getLocation());
				plugin.getProxy().broadcast(new ComponentBuilder(getLine(30)).create());
				plugin.getProxy().broadcast(new ComponentBuilder("§5§l" + announce.getHostName() +  " a lancé un event " + announce.getName()).create());
			}
			
			plugin.getProxy().broadcast(new ComponentBuilder(getCentered("§f §5§l" + announce.getName() + " ", 30)).create());
			plugin.getProxy().broadcast(TextComponent.fromLegacyText(announce.getDesc()));
			plugin.getProxy().broadcast(TextComponent.fromLegacyText("§fArene : " + announce.getArena()));
			plugin.getProxy().broadcast(new ComponentBuilder("§d§lCliquez pour rejoindre").event(new ClickEvent(Action.RUN_COMMAND, "/joinevent " + key)).create());
			plugin.getProxy().broadcast(new ComponentBuilder(getLine(30)).create());

		}
	}

	private String getLine(int length) {
		StringBuilder sb = new StringBuilder("§8§m");
		for(int i = 0 ; i < length ; i++) {
			sb.append("-");
		}
		return sb.toString();
	}

	private String getCentered(String string, int lineLength) {
		StringBuilder sb = new StringBuilder("§8§m");
		int line = (lineLength - string.length()) / 2 + 2;
		for(int i = 0 ; i < line ; i++) {
			sb.append("-");
		}
		sb.append(string);
		sb.append("§8§m");
		for(int i = 0 ; i < line ; i++) {
			sb.append("-");
		}
		return sb.toString();
	}

}
