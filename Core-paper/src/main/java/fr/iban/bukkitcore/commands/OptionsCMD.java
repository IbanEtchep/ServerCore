package fr.iban.bukkitcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.iban.bukkitcore.menu.OptionsMenu;


public class OptionsCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player player) {
			new OptionsMenu(player).open();
		}
		return false;
	}

}
