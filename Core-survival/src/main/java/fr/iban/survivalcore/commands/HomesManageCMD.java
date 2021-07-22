package fr.iban.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.iban.survivalcore.utils.LuckPermsUtils;

public class HomesManageCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("spartacube.addhomes")) {
			if(args.length == 2) {
				Player target = Bukkit.getPlayer(args[0]);
				int amount = Integer.parseInt(args[1]);
				if(target != null) {
					LuckPermsUtils.addHomes(target, amount);
					sender.sendMessage(amount +" résidences ont été ajoutées à "+ target.getName());
				}
			}
		}
		return false;
	}

}
