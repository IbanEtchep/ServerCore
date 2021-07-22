package fr.iban.survivalcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.iban.survivalcore.tools.SpecialTools;

public class GiveSpecialToolsCMD implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player && sender.hasPermission("spartacube.givetools")) {
			Player player = (Player)sender;
			
			if(args.length == 0) {
				player.sendMessage("/givetools (pioche/pelle/hache) (bucheron/hades/3x3)");
			}else if(args.length <= 2) {
				switch (args[0]) {
				case "pioche":
					switch (args[1]) {
					case "hades":
						player.getInventory().addItem(SpecialTools.getCutCleanPickaxe());
						break;
					case "3x3":
						player.getInventory().addItem(SpecialTools.get3x3Pickaxe());
						break;
					default:
						player.sendMessage("/give pioche (hades/3x3)");
						break;
					}
				 break;
				case "pelle":
					switch (args[1]) {
					case "3x3":
						player.getInventory().addItem(SpecialTools.get3x3Shovel());
						break;
					default:
						player.sendMessage("/give pelle (3x3)");
						break;
					}
				 break;
				case "hache":
					switch (args[1]) {
					case "bucheron":
						player.getInventory().addItem(SpecialTools.getLumberjackAxe());
						break;
					default:
						player.sendMessage("/give hache (bucheron)");
						break;
					}
				 break;
				case "houe":
					switch (args[1]) {
					case "déméter":
						player.getInventory().addItem(SpecialTools.getFarmerHoe());
						break;
					default:
						player.sendMessage("/give houe (déméter)");
						break;
					}
				 break;
				case "epee":
					switch (args[1]) {
					case "hephaistos":
						player.getInventory().addItem(SpecialTools.getXpSword());
						break;
					default:
						player.sendMessage("/give epee (hephaistos)");
						break;
					}
				 break;
				default:
					player.sendMessage("/givetools (pioche/pelle/hache/houe/epee) (bucheron/hades/3x3/déméter/hephaistos)");
					break;
				}
			}
		}
		
		return false;

	}

	
}