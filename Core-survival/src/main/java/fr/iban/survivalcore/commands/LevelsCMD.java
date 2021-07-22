package fr.iban.survivalcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.iban.common.data.AccountProvider;
import fr.iban.spartacube.data.Account;
import fr.iban.survivalcore.utils.HexColor;
import fr.iban.survivalcore.utils.LevelUtils;

public class LevelsCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			if(sender instanceof Player) {
				Player p = (Player)sender;
				Account account = new AccountProvider(p.getUniqueId()).getAccount();
				p.sendMessage(HexColor.FLAT_PURPLE.getColor() + "Vous êtes au niveau " + HexColor.FLAT_BLUE_GREEN.getColor() + "§l" + account.getLevel() + HexColor.FLAT_PURPLE.getColor()+" .");
			}
		}else if(args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if(target != null) {
				Account account = new AccountProvider(target.getUniqueId()).getAccount();
				sender.sendMessage(HexColor.FLAT_PURPLE.getColor() + target.getName() + " est au niveau " + HexColor.FLAT_BLUE_GREEN.getColor() + "§l" + account.getLevel() + HexColor.FLAT_PURPLE.getColor()+" .");
			}else {
				sender.sendMessage("§cCe joueur n'est pas en ligne.");
			}
		}else if(args.length > 1 && sender.hasPermission("levels.admin")){
			//level set <joueur> nombre
			if(args.length == 3 && args[0].equalsIgnoreCase("set")) {
				Player target = Bukkit.getPlayer(args[1]);
				if(target != null) {
					int nombre = Integer.parseInt(args[2]);
					AccountProvider ap = new AccountProvider(target.getUniqueId());
					Account account = ap.getAccount();
					account.setExp(LevelUtils.getLevelExp(nombre));
					ap.sendAccountToRedis(account);
					sender.sendMessage("§a"+target.getName() + " est maintenant niveau " + nombre);
				}else {
					sender.sendMessage("§cCe joueur n'est pas en ligne !");
				}
			}
			if(args.length == 3 && args[0].equalsIgnoreCase("addxp")) {
				Player target = Bukkit.getPlayer(args[1]);
				if(target != null) {
					int nombre = Integer.parseInt(args[2]);
					AccountProvider ap = new AccountProvider(target.getUniqueId());
					Account account = ap.getAccount();
					account.addExp(nombre);
					ap.sendAccountToRedis(account);
					sender.sendMessage("§a"+target.getName() + " a reçu" + nombre + " xp.");
				}else {
					sender.sendMessage("§cCe joueur n'est pas en ligne !");
				}
			}
		}
		return false;
	}


}