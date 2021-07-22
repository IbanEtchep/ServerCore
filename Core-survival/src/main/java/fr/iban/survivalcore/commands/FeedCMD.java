package fr.iban.survivalcore.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCMD implements CommandExecutor {

	private Map<UUID, Long> cooldowns = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			if(player.hasPermission("spartacube.feed")) {

				if(!cooldowns.isEmpty() && cooldowns.containsKey(player.getUniqueId())) {
					int secondsLeft = (int) (120000 - (System.currentTimeMillis() - cooldowns.get(player.getUniqueId()))) / 1000;
					if(secondsLeft <= 0) {
						cooldowns.remove(player.getUniqueId());
					}else if(!player.hasPermission("spartacube.feed.bypasscooldown")){
						player.sendMessage("§cVous devez attendre " + secondsLeft +" secondes avant de réutiliser cette commande !");
						return false;
					}
				}

				player.setFoodLevel(20);
				player.setSaturation(5f);
				player.sendMessage("§aVous avez été rassasié.");

				if(!player.hasPermission("spartacube.feed.bypasscooldown")){
					cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
				}
			}else {
				player.sendMessage("§cVous n'avez pas la permission pour effectuer cette commande.");
			}
		}
		return false;
	}



}
