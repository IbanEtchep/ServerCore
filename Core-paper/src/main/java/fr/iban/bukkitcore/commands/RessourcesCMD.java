package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.manager.RessourcesWorldManager;
import fr.iban.bukkitcore.menu.RessourceMenu;
import fr.iban.bukkitcore.menu.ServeurMenu;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RessourcesCMD implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			if(args.length == 0){
				new RessourceMenu(player).open();
			}else if(args.length == 1){
				final RessourcesWorldManager ressourcesWorldManager = CoreBukkitPlugin.getInstance().getRessourcesWorldManager();
				switch (args[0]){
					case "world":
						ressourcesWorldManager.sendToRessourceWorld(player, "resource_world");
						break;
					case "nether":
						ressourcesWorldManager.sendToRessourceWorld(player, "resource_nether");
						break;
					case "end":
						ressourcesWorldManager.sendToRessourceWorld(player, "resource_end");
						break;
					default:
						player.sendMessage("§cCe type de monde n'existe pas.");
						break;
				}
			}
		}
		return false;
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> list = new ArrayList<>();
		if(sender instanceof Player && args.length == 1) {
			list.add("world");
			list.add("nether");
			list.add("end");
			return list.stream().filter(string -> string.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
		}
		return list;
	}
}
