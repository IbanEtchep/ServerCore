package fr.iban.survivalcore.commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RepairCMD implements CommandExecutor {

	private HashMap<UUID, Long> delaymap = new HashMap<>();

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (s instanceof Player) {
			Player player = (Player)s;
			if (player.hasPermission("spartacube.repair")) {
				String norepair = ChatColor.RED + "Erreur: " + ChatColor.DARK_RED + "Cet item n'est pas réparable";
				if (args.length == 0 || (args.length >= 1 && args[0].equalsIgnoreCase("hand"))) {
					Material material = player.getItemInHand().getType();
					if (material.isBlock() || material.getMaxDurability() < 1 || player.getItemInHand().getDurability() == 0) {
						player.sendMessage(norepair);
					} else if(canRepair(player)){
						player.getItemInHand().setDurability((short)0);
						player.sendMessage(ChatColor.GOLD + "§aRéparation effectuée.");
						delaymap.put(player.getUniqueId(), System.currentTimeMillis());
					} 
				} else {
					player.sendMessage(ChatColor.DARK_RED + "Vous n'êtes pas autorisé à utiliser cette commande");
				} 
			}  
		}
		return true;
	}
	
	
	private boolean canRepair(Player player) {
		if(delaymap.containsKey(player.getUniqueId())) {
			long lastRep = delaymap.get(player.getUniqueId());
			long troisheures = 3*3600*1000;
			if(System.currentTimeMillis() - lastRep > troisheures) {
				delaymap.remove(player.getUniqueId());
				return true;
			}else {
				long remain = troisheures - (System.currentTimeMillis() - lastRep);
				player.sendMessage("§cVous pourrez à nouveau réparer un item dans " + remain/1000 + " secondes.");
				return false;
			}
		}
		return true;
	}
}
