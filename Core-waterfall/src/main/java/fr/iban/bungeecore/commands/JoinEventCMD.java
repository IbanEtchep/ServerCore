package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class JoinEventCMD extends Command {

	private CoreBungeePlugin plugin;

	public JoinEventCMD(String name, CoreBungeePlugin coreBungeePlugin) {
		super(name);
		this.plugin = coreBungeePlugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer)sender;
			if(plugin.getCurrentEvents().isEmpty()) {
				player.sendMessage(TextComponent.fromLegacyText("§cIl n'y a pas d'event en cours :c"));
				return;
			}
			if(args.length == 0) {
				if(plugin.getCurrentEvents().size() == 1) {
					joinEvent(plugin.getCurrentEvents().keySet().iterator().next(), player);
				}else {
					player.sendMessage(TextComponent.fromLegacyText("§cIl y a actuellement plus d'un event en cours, veuillez indiquer le quel vous voulez rejoindre."));
				}
			}else if(args.length == 1) {
				joinEvent(args[0], player);
			}
		}
	}
	
	private void joinEvent(String event, ProxiedPlayer player) {
		if(plugin.getCurrentEvents().containsKey(event)) {
			plugin.getTeleportManager().delayedTeleport(player, plugin.getCurrentEvents().get(event), 3);
		}else {
			player.sendMessage(TextComponent.fromLegacyText("§cIl n'y a pas d'event à ce nom."));
		}
	}

}
