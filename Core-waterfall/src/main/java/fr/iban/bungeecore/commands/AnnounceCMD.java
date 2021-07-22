package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.utils.AnnoncesManager;
import fr.iban.common.data.AccountProvider;
import fr.iban.spartacube.data.Account;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AnnounceCMD extends Command {

	public AnnounceCMD(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		AnnoncesManager am = CoreBungeePlugin.getInstance().getAnnounceManager();
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer)sender;
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("listdisabled")) {
					AccountProvider ap = new AccountProvider(player.getUniqueId());
					Account account = ap.getAccount();
					for(int idA : account.getBlackListedAnnounces()) {
						player.sendMessage(new TextComponent("- " + idA));
					}
				}
			}
			if(args.length == 2){
				if(args[0].equalsIgnoreCase("disable")) {
					int id = Integer.parseInt(args[1]);
					if(am.getAnnonces().containsKey(id)) {
						AccountProvider ap = new AccountProvider(player.getUniqueId());
						Account account = ap.getAccount();
						if(!account.getBlackListedAnnounces().contains(id)) {
							account.getBlackListedAnnounces().add(id);
							player.sendMessage(new TextComponent("§aCette annonce ne vous sera plus affichée à l'avenir."));
							ap.sendAccountToRedis(account);
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
