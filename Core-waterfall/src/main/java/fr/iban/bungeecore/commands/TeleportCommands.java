package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.manager.TeleportManager;
import fr.iban.common.teleport.SLocation;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bungee.BungeeCommandActor;
import revxrsal.commands.bungee.annotation.CommandPermission;

public class TeleportCommands {

    private CoreBungeePlugin plugin;
    private TeleportManager teleportManager;

    public TeleportCommands(CoreBungeePlugin plugin) {
        this.plugin = plugin;
        this.teleportManager = plugin.getTeleportManager();
    }

    @Command("back")
    @CommandPermission("servercore.back.death")
    public void back(ProxiedPlayer sender) {
        SLocation location = teleportManager.getDeathLocations().get(sender.getUniqueId());
        if(location != null) {
            teleportManager.delayedTeleport(sender, location, 3);
        }else {
            sender.sendMessage(TextComponent.fromLegacyText("§cL'endroit de votre décès n'a pas pu être trouvé."));
        }
    }

    @Command("lastrtp")
    public void lastRtp(ProxiedPlayer sender) {
        SLocation loc = plugin.getTeleportManager().getLastRTPLocations().get(sender.getUniqueId());
        if (loc != null) {
            plugin.getTeleportManager().delayedTeleport(sender, loc, 2);
        } else {
            sender.sendMessage("§cLa position n'a pas été trouvée.");
        }
    }
}
