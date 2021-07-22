package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ChatCMD extends Command {

	public ChatCMD(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender.hasPermission("spartacube.chatmanage")) {
			if(args.length == 0) {
				sender.sendMessage(TextComponent.fromLegacyText("§2Commandes chat :"));
				sender.sendMessage(TextComponent.fromLegacyText("§a/chat toggle - mute/unmute le chat"));
				sender.sendMessage(TextComponent.fromLegacyText("§a/chat clear - clear le chat"));
			}else if(args.length == 1) {
				switch (args[0]) {
				case "clear":
					for(int i=0 ; i < 200 ; i++) {
						ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(""));
					}
					ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText("§cLe chat a été clear par " + sender.getName() + "."));
					break;
				case "toggle":
					CoreBungeePlugin.getInstance().getChatManager().toggleChat(sender);
					break;
				default:
					break;
				}
			}
		}
	}
	

}
