package fr.iban.bungeecore.listeners;

import java.util.UUID;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import fr.iban.bungeecore.CoreBungeePlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {
	
	
	@EventHandler
	public void onPluginMessage(PluginMessageEvent e) {
		ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
		if(e.getTag().equals("proxy:chat")) {
			String sub = in.readUTF();
			if(sub.equals("Global")) {
				if(e.getReceiver() instanceof ProxiedPlayer) {
					UUID uuid = UUID.fromString(in.readUTF());
					String message = in.readUTF();
					CoreBungeePlugin.getInstance().getChatManager().sendGlobalMessage(uuid, message);
				}
			}
		}else if(e.getTag().equals("proxy:annonce")) {
			String sub = in.readUTF();
			if(sub.equals("Annonce")) {
				if(e.getReceiver() instanceof ProxiedPlayer) {
					UUID uuid = UUID.fromString(in.readUTF());
					String message = in.readUTF();
					CoreBungeePlugin.getInstance().getChatManager().sendAnnonce(uuid, message);
				}
			}
		}
	}

}