package fr.iban.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Option;
import fr.iban.spartacube.data.Account;
import fr.iban.survivalcore.utils.HexColor;

public class SimpleCommands implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {

			Player player = (Player)sender;

			switch (label) {
			case "site":
				player.sendMessage(HexColor.FLAT_GREEN.getColor() + "≫ " + HexColor.FLAT_LIGHT_GREEN.getColor() + "Site : https://spartacube.fr/");
				break;
			case "discord":
				player.sendMessage(HexColor.FLAT_GREEN.getColor() + "≫ " + HexColor.FLAT_LIGHT_GREEN.getColor() + "Discord : https://discord.gg/jdXnHJ5");
				break;
			case "boutique":
				player.sendMessage(HexColor.FLAT_GREEN.getColor() + "≫ " + HexColor.FLAT_LIGHT_GREEN.getColor() + "Boutique : https://www.spartacube.fr/shop");
				break;
			case "vote":
				player.sendMessage(HexColor.FLAT_GREEN.getColor() + "≫ " + HexColor.FLAT_LIGHT_GREEN.getColor() + "Lien pour voter : https://spartacube.fr/vote");
				break;
			case "map":
				player.sendMessage(HexColor.FLAT_GREEN.getColor() + "≫ " + HexColor.FLAT_LIGHT_GREEN.getColor() + "Map du serveur : http://map.spartacube.fr/");
				break;
			case "grades":
				player.sendMessage(HexColor.FLAT_GREEN.getColor() + "≫ " + HexColor.FLAT_LIGHT_GREEN.getColor() + "Informations sur les grades : https://spartacube.fr/grades");
				break;
			case "tutoriel":
			case "tuto":
				player.chat("/st play Arrivé");
				break;
			case "stoptuto":
				player.chat("/st quit");
				break;
			case "pvp":
				if(args.length == 0) {
					AccountProvider ap = new AccountProvider(player.getUniqueId());
					Account account = ap.getAccount();
					account.toggleOption(Option.PVP);
					if(account.getOption(Option.PVP)) {
						player.sendMessage("§aPVP activé.");
					}else {
						player.sendMessage("§cPVP desactivé.");
					}
					ap.sendAccountToRedis(account);
				}else if(args.length == 1 && sender.hasPermission("spartacube.pvp.others")) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						AccountProvider ap = new AccountProvider(target.getUniqueId());
						Account account = ap.getAccount();
						account.toggleOption(Option.PVP);
						if(account.getOption(Option.PVP)) {
							player.sendMessage("§aPVP de "+ target.getName() +" activé.");
						}else {
							player.sendMessage("§cPVP "+ target.getName() +" desactivé.");
						}
						ap.sendAccountToRedis(account);
					}
				}
				break;
			default:
				break;
			}
		}

		return false;
	}

}
