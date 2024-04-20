package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.*;
import revxrsal.commands.velocity.VelocityCommandActor;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;

public class JoinEventCMD {

    private final CoreVelocityPlugin plugin;

    public JoinEventCMD(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Command("joinevent")
    @Description("Rejoignez un événement en cours ou spécifique si un nom est fourni.")
    @CommandPermission("servercore.joinevent")
    @Usage("/joinevent <event>")
    public void joinEvent(VelocityCommandActor actor, @Optional @Named("event") String eventName) {
        if (!(actor instanceof Player player)) {
            return;
        }

        if (eventName == null) {
            handleJoinEvent(player, plugin.getCurrentEvents().lastKey());
        } else {
            handleJoinEvent(player, eventName);
        }
    }

    private void handleJoinEvent(Player player, String event) {
        if (plugin.getCurrentEvents().containsKey(event)) {
            plugin.getTeleportManager().delayedTeleport(player, plugin.getCurrentEvents().get(event), 3);
        } else {
            player.sendMessage(Component.text("Il n'y a pas d'event à ce nom.", NamedTextColor.RED));
        }
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
    }
}
