package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.teleport.TeleportManager;
import fr.iban.common.teleport.SLocation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BackCMD extends Command {
	
	private TeleportManager tpManager;

	public BackCMD(String name, String permission, TeleportManager tpManager) {
		super(name, permission);
		this.tpManager = tpManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer && sender.hasPermission(getPermission())){
			ProxiedPlayer player = (ProxiedPlayer)sender;
			SLocation location = tpManager.getDeathLocations().get(player.getUniqueId());
			if(location != null) {
				tpManager.delayedTeleport(player, location, 3);
			}else {
				sender.sendMessage(TextComponent.fromLegacyText("§cL'endroit de votre décès n'a pas pu être trouvé."));
			}
		}else {
			sender.sendMessage(TextComponent.fromLegacyText("§cVous n'avez pas la permission d'éxecuter cette commande."));
		}
	}

}
