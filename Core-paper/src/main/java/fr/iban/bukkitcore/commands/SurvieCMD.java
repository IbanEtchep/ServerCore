package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.iban.bukkitcore.utils.PluginMessageHelper;

public class SurvieCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			PluginMessageHelper.sendPlayerToServer(player, CoreBukkitPlugin.getInstance().getConfig().getString("survie-servername", "survie"));
		}
		return false;
	}

}
