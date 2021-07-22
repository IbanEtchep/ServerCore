package fr.iban.bungeecore.commands;

import java.util.ArrayList;
import java.util.List;

import fr.iban.bungeecore.teleport.RequestType;
import fr.iban.bungeecore.teleport.TeleportManager;
import fr.iban.bungeecore.teleport.TpRequest;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class TpyesCMD extends Command implements TabExecutor{

	private TeleportManager tpm;


	public TpyesCMD(String name, String permission, String alias, TeleportManager tpm) {
		super(name, permission, alias);
		this.tpm = tpm;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer)sender;
			if(args.length == 1) {
				if(!tpm.getTpRequests(player).isEmpty()) {
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
					if(target != null) {
						TpRequest req = tpm.getTpRequestFrom(player, target);
						if(req != null) {
							if(req.getRequestType() == RequestType.TP) {
								tpm.delayedTeleport(target, player, 3);
							}else if(req.getRequestType() == RequestType.TPHERE) {
								tpm.delayedTeleport(player, target, 3);
							}
							player.sendMessage(TextComponent.fromLegacyText("§aDemande de téléportation acceptée."));
							tpm.getTpRequests(player).remove(req);
						}else {
							player.sendMessage(TextComponent.fromLegacyText("§cVous n'avez pas de requête de téléportation de ce joueur."));
						}
					}else {
						player.sendMessage(TextComponent.fromLegacyText("§cLe joueur n'est plus en ligne."));
					}
				}else {
					player.sendMessage(TextComponent.fromLegacyText("§cVous n'avez pas de requête de téléportation."));
				}
			}else {
				if(!tpm.getTpRequests(player).isEmpty()) {
					TpRequest request = (TpRequest)tpm.getTpRequests(player).get(tpm.getTpRequests(player).size() - 1);
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(request.getPlayerID());
					if(target != null) {
						if(request.getRequestType() == RequestType.TP) {
							tpm.delayedTeleport(target, player, 3);
						}else if(request.getRequestType() == RequestType.TPHERE) {
							tpm.delayedTeleport(player, target, 3);
						}
						player.sendMessage(TextComponent.fromLegacyText("§aDemande de téléportation acceptée."));
						tpm.getTpRequests(player).remove(request);
					}else {
						player.sendMessage(TextComponent.fromLegacyText("§aLe joueur n'est plus en ligne."));
					}
				}else {
					player.sendMessage(TextComponent.fromLegacyText("§cVous n'avez pas de requête de téléportation."));
				}
			}
		}
	}

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
		List<String> playernames = new ArrayList<>();
		if(sender instanceof ProxiedPlayer && args.length == 1) {
			ProxiedPlayer player = (ProxiedPlayer)sender;
			for (Object object : tpm.getTpRequests(player)) {
				TpRequest request = (TpRequest)object;
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(request.getPlayerID());
				if(target != null && target.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					playernames.add(target.getName());
				}
			}
		}
		return playernames;
	}

}