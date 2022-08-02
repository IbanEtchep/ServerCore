package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.teleport.SLocation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LastRtpCMD extends Command {


	private final CoreBungeePlugin plugin;

	public LastRtpCMD(CoreBungeePlugin plugin, String name) {
        super(name);
		this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            SLocation loc = plugin.getTeleportManager().getLastRTPLocations().get(player.getUniqueId());
            if (loc != null) {
                plugin.getTeleportManager().delayedTeleport(player, loc, 2);
            } else {
                player.sendMessage("§cLa position n'a pas été trouvée.");
            }
        }
    }
}
