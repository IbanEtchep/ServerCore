package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.utils.HexColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleCommands implements CommandExecutor {

	private CoreBukkitPlugin plugin;

	public SimpleCommands(CoreBukkitPlugin plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {

			Player player = (Player)sender;

			switch (label) {
			case "site":
				player.sendMessage(HexColor.translateColorCodes(plugin.getConfig().getString("messages.site")));
				break;
			case "discord":
				player.sendMessage(HexColor.translateColorCodes(plugin.getConfig().getString("messages.discord")));
				break;
			case "vote":
				player.sendMessage(HexColor.translateColorCodes(plugin.getConfig().getString("messages.vote")));
				break;
			case "grades":
				player.sendMessage(HexColor.translateColorCodes(plugin.getConfig().getString("messages.grades")));
				break;
			default:
				break;
			}
		}

		return false;
	}

}
