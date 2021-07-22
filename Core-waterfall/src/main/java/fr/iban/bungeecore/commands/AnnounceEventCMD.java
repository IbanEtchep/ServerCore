package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class AnnounceEventCMD extends Command {

	private CoreBungeePlugin plugin;

	public AnnounceEventCMD(String name, CoreBungeePlugin plugin) {
		super(name);
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender.hasPermission("spartacube.announceevent") && args.length >= 1) {
			StringBuilder bc = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				bc.append(args[i] + " ");
			}
			plugin.getProxy().broadcast(TextComponent.fromLegacyText("§cE§6V§eE§aN§9T§5 : §6" + bc.toString()));
		}else {
			sender.sendMessage(TextComponent.fromLegacyText("§cVous n'avez pas la permission de faire ça."));
		}
	}

}
