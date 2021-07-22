package fr.iban.survivalcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.iban.survivalcore.utils.LevelsTop;
import fr.iban.survivalcore.utils.TopJoueur;

public class TopLevelsCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(args.length == 0) {
			
			LevelsTop.getTopLevelsAsync(10).thenAccept(top -> {
				sender.sendMessage(getCentered("§d§l Classement niveaux ", 30));
				for (int i = 0; i < top.size(); i++) {
					TopJoueur joueur = top.get(i);
					sender.sendMessage("§l" + (i+1) + " - §d§l" + joueur.getName() + " §f§lniveau §d§l" + joueur.getLevel());
				}
				sender.sendMessage(getLine(31));;
			});
			
		}else if(args.length == 1) {
			try {
				int nb = Math.abs(Integer.parseInt(args[0]));
				LevelsTop.getTopLevelsAsync(nb > 100 ? 100 : nb).thenAccept(top -> {
					sender.sendMessage(getCentered("§d§l Classement niveaux ", 30));
					for (int i = 0; i < top.size(); i++) {
						TopJoueur joueur = top.get(i);
						sender.sendMessage("§l" + (i+1) + " - §d§l" + joueur.getName() + " §f§lniveau §d§l" + joueur.getLevel());
					}
					sender.sendMessage(getLine(31));;
				});
			}catch (NumberFormatException e) {
				sender.sendMessage("§cIl faut un nombre entier !");
			}
		}
		
		return false;
	}
	
	private String getLine(int length) {
		StringBuilder sb = new StringBuilder("§8§m");
		for(int i = 0 ; i < length ; i++) {
			sb.append("-");
		}
		return sb.toString();
	}

	private String getCentered(String string, int lineLength) {
		StringBuilder sb = new StringBuilder("§8§m");
		int line = (lineLength - string.length()) / 2 + 2;
		for(int i = 0 ; i < line ; i++) {
			sb.append("-");
		}
		sb.append(string);
		sb.append("§8§m");
		for(int i = 0 ; i < line ; i++) {
			sb.append("-");
		}
		return sb.toString();
	}
	

}
