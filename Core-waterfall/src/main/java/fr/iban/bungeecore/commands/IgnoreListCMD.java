package fr.iban.bungeecore.commands;

import java.util.UUID;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.data.AccountProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class IgnoreListCMD extends Command {

	public IgnoreListCMD(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer)sender;
			if(args.length == 0) {
				new AccountProvider(player.getUniqueId()).getAccountAsync().thenAccept(account -> {
					if(!account.getIgnoredPlayers().isEmpty()) {
						player.sendMessage(new TextComponent("|| §7§Joueurs Ignorés ||"));
						for(UUID ignoredPlayer : account.getIgnoredPlayers()) {
							player.sendMessage(new TextComponent("§8- §a" + CoreBungeePlugin.getInstance().getProxy().getPlayer(ignoredPlayer).getName()));
						}
					} else {
						player.sendMessage(new TextComponent("§cVous n'ignorez personne."));
					}
				});
			} else {
				player.sendMessage(new TextComponent("§7Utilisation: §c/ignorelist"));
			}
		} 
	}
}
