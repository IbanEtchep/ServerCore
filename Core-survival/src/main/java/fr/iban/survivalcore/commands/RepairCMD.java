package fr.iban.survivalcore.commands;

import java.util.HashMap;
import java.util.UUID;

import fr.iban.survivalcore.tools.SpecialTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairCMD implements CommandExecutor {

	private HashMap<UUID, Long> repairCooldowns = new HashMap<>();
	private HashMap<UUID, Long> repairAllCooldowns = new HashMap<>();


	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (s instanceof Player) {
			Player player = (Player)s;
			String norepair = ChatColor.RED + "Erreur: " + ChatColor.DARK_RED + "Cet item n'est pas réparable";
			if (args.length == 0 || (args.length >= 1 && args[0].equalsIgnoreCase("hand"))) {
				if (player.hasPermission("servercore.repair")) {
					ItemStack item = player.getItemInHand();
					if (isRepairable(item)) {
						if (canRepair(player)) {
							player.getItemInHand().setDurability((short) 0);
							player.sendMessage(ChatColor.GOLD + "§aRéparation effectuée.");
							repairCooldowns.put(player.getUniqueId(), System.currentTimeMillis());
						}
					}else{
						player.sendMessage(norepair);
					}
				} else {
					player.sendMessage(ChatColor.DARK_RED + "Vous n'êtes pas autorisé à utiliser cette commande");
				}
			}else if(args.length >= 1 && args[0].equalsIgnoreCase("all")){
				if (player.hasPermission("servercore.repairall")) {
					if(canRepair(player)){
						for(ItemStack item : player.getInventory().getContents()){
							if (isRepairable(item)) {
								item.setDurability((short) 0);
							}
						}
						for(ItemStack item : player.getInventory().getArmorContents()){
							if (isRepairable(item)) {
								item.setDurability((short) 0);
							}
						}
						repairAllCooldowns.put(player.getUniqueId(), System.currentTimeMillis());
						player.sendMessage(ChatColor.GOLD + "§aRéparations effectuées.");
					}
				}else{
					player.sendMessage(ChatColor.DARK_RED + "Vous n'êtes pas autorisé à utiliser cette commande");
				}
			}
		}
		return true;
	}

	private boolean isRepairable(ItemStack item){
		if (item == null || item.getType().isBlock() || item.getType().getMaxDurability() < 1 || item.getDurability() == 0 ||
				SpecialTools.is3x3Pickaxe(item) || SpecialTools.is3x3Pickaxe(item)) {
			return false;
		}
		return true;
	}


	private boolean canRepair(Player player) {
		if(repairCooldowns.containsKey(player.getUniqueId())) {
			long lastRep = repairCooldowns.get(player.getUniqueId());
			long troisheures = 3*3600*1000;
			if(System.currentTimeMillis() - lastRep > troisheures) {
				repairCooldowns.remove(player.getUniqueId());
				return true;
			}else {
				long remain = troisheures - (System.currentTimeMillis() - lastRep);
				player.sendMessage("§cVous pourrez à nouveau réparer un item dans " + remain/1000 + " secondes.");
				return false;
			}
		}
		return true;
	}

	private boolean canRepairAll(Player player) {
		if(repairAllCooldowns.containsKey(player.getUniqueId())) {
			long lastRep = repairAllCooldowns.get(player.getUniqueId());
			long troisheures = 3*3600*1000;
			if(System.currentTimeMillis() - lastRep > troisheures) {
				repairAllCooldowns.remove(player.getUniqueId());
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
