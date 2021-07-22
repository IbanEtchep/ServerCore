package fr.iban.bungeecore.commands;

import java.util.UUID;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.data.AccountProvider;
import fr.iban.spartacube.data.Account;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class IgnoreCMD extends Command {

	public IgnoreCMD(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer)sender;
			if(args.length == 1) {
				if(CoreBungeePlugin.getInstance().getProxy().getPlayer(args[0]) != null) {
					UUID ignoredPlayer = CoreBungeePlugin.getInstance().getProxy().getPlayer(args[0]).getUniqueId();
					ProxiedPlayer ignored = CoreBungeePlugin.getInstance().getProxy().getPlayer(args[0]);
					if(ignoredPlayer != player.getUniqueId()) {
						AccountProvider ap = new AccountProvider(player.getUniqueId());
						Account account = ap.getAccount();
						if(!account.getIgnoredPlayers().contains(ignoredPlayer)) {
						  if(!ignored.hasPermission("spartacube.staff")) {
							account.getIgnoredPlayers().add(ignoredPlayer);
							player.sendMessage(new TextComponent("§aVous ignoré maintenant §7" + ignored.getName() + "§a."));
							ap.sendAccountToRedis(account);
						  } else {
							  player.sendMessage(new TextComponent("§cVous ne pouvez pas ignoré un membre du staff !")); 
						  }
						}else {
							account.getIgnoredPlayers().remove(ignoredPlayer);
							player.sendMessage(new TextComponent("§aVous n'ignorez plus §7" + ignored.getName() + "§a."));
							ap.sendAccountToRedis(account);
						}
					} else {
						 player.sendMessage(new TextComponent("§cVous ne pouvez pas vous ignorer vous même.")); 
					}
				}else {
					player.sendMessage(new TextComponent("§cCe joueur n'existe pas."));
					}	  
			} else {
				player.sendMessage(new TextComponent("§7Utilisation: §c/ignore (joueur)"));
			}

		}

	}

}