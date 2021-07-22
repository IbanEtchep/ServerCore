package fr.iban.survivalcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
			default:
				break;
			}
		}

		return false;
	}

}
