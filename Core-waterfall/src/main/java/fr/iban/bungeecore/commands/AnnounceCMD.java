package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.manager.AccountManager;
import fr.iban.bungeecore.manager.AnnoncesManager;
import fr.iban.common.data.Account;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AnnounceCMD extends Command {

	private final CoreBungeePlugin plugin;

	public AnnounceCMD(String name, CoreBungeePlugin plugin) {
		super(name);
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		AnnoncesManager am = CoreBungeePlugin.getInstance().getAnnounceManager();
		if(sender instanceof ProxiedPlayer player) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("listdisabled")) {
					Account account = plugin.getAccountManager().getAccount(player.getUniqueId());
					for(int idA : account.getBlackListedAnnounces()) {
						player.sendMessage(new TextComponent("- " + idA));
					}
				}
			}
			if(args.length == 2){
				if(args[0].equalsIgnoreCase("disable")) {
					int id = Integer.parseInt(args[1]);
					if(am.getAnnonces().containsKey(id)) {
						AccountManager accountManager = plugin.getAccountManager();
						Account account = accountManager.getAccount(player.getUniqueId());
						if(!account.getBlackListedAnnounces().contains(id)) {
							account.getBlackListedAnnounces().add(id);
							player.sendMessage(new TextComponent("§aCette annonce ne vous sera plus affichée à l'avenir."));
							accountManager.saveAccount(account);
						}else {
							player.sendMessage(new TextComponent("§cVous avez déjà bloqué cette annonce."));
						}
					}else {
						player.sendMessage(new TextComponent("§cCette annonce n'existe pas."));
					}
				}
			}
		}

	}

}
