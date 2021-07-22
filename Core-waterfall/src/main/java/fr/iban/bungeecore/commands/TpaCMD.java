package fr.iban.bungeecore.commands;

import java.util.ArrayList;
import java.util.List;

import fr.iban.bungeecore.teleport.TeleportManager;
import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Option;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class TpaCMD extends Command implements TabExecutor{

	private TeleportManager tpm;

	public TpaCMD(String name, String permission, TeleportManager tpm) {
		super(name, permission);
		this.tpm = tpm;
	}


	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer)sender;
			if(args.length == 1) {
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if(target != null) {

					if(target.equals(player)) {
						player.sendMessage(TextComponent.fromLegacyText("§cVous ne pouvez pas vous téléporter à vous même."));
						return;
					}

					new AccountProvider(target.getUniqueId()).getAccountAsync().thenAccept(account -> {
						
						if(account.getOption(Option.TP)) {
							if(!account.getIgnoredPlayers().contains(player.getUniqueId())) {
								tpm.sendTeleportRequest(player, target);
							}
						} else {
							player.sendMessage(TextComponent.fromLegacyText("§cCe joueur a désactivé ses demandes de téléportation."));
						}
						
					});

				}else {
					player.sendMessage(TextComponent.fromLegacyText("§cCe joueur n'est pas en ligne."));
				}
			}
		}
	}

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
		List<String> playernames = new ArrayList<>();
		if(args.length == 1) {
			for(ProxiedPlayer p: ProxyServer.getInstance().getPlayers()){
				if(!sender.getName().equals(p.getName()) && p.getName().toLowerCase().startsWith(args[0].toLowerCase()))
					playernames.add(p.getName());
			}
		}
		return playernames;
	}

}