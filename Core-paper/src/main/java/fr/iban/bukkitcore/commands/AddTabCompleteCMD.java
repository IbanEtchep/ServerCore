package fr.iban.bukkitcore.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import net.md_5.bungee.api.chat.TextComponent;

public class AddTabCompleteCMD implements CommandExecutor {

	private CoreBukkitPlugin plugin;

	public AddTabCompleteCMD(CoreBukkitPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("spartacube.admin")) {
			if(args.length <= 1) {
				sender.sendMessage(TextComponent.fromLegacyText("/addtabcomplete global/moderation commandeSansSlash"));
			}else if(args.length == 2){
				switch (args[0]) {
				case "global":
				{
					String path = "tabcomplete.global";
					List<String> list = plugin.getConfig().getStringList("tabcomplete.global");
					list.add(args[1]);
					plugin.getConfig().set(path, list);
					sender.sendMessage(TextComponent.fromLegacyText(args[1] + " ajouté au groupe " + args[0]));
					plugin.saveConfig();
					break;
				}
				case "moderation":
				{
					String path = "tabcomplete.moderation";
					List<String> list = plugin.getConfig().getStringList("tabcomplete.global");
					list.add(args[1]);
					plugin.getConfig().set(path, list);
					sender.sendMessage(TextComponent.fromLegacyText(args[1] + " ajouté au groupe " + args[0]));
					plugin.saveConfig();
					break;
				}
				default:
					break;
				}
			}
		}
		return false;
	}

}
