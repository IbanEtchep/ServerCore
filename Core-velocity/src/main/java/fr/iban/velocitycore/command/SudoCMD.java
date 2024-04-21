package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import revxrsal.commands.annotation.*;
import revxrsal.commands.velocity.VelocityCommandActor;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SudoCMD {

    private final ProxyServer server;

    public SudoCMD(CoreVelocityPlugin plugin) {
        this.server = plugin.getServer();
    }


    @Command("sudo")
    @Description("Permet à un joueur de faire parler un autre joueur ou d'exécuter une commande en son nom.")
    @CommandPermission("servercore.sudo")
    @Usage("/sudo <joueur> <message|commande>")
    public void execute(Player player, Player target, @Optional String message) {
        if (target.hasPermission("servcore.sudobypass") && !player.hasPermission("servercore.adminsudo")) {
            player.sendMessage(Component.text("Désolé, ce joueur a la permission de bypass. Vous ne pouvez pas utiliser sudo sur lui.", NamedTextColor.RED));
        } else if (message == null || message.isEmpty()) {
            player.sendMessage(Component.text("Veuillez spécifier un message ou une commande à envoyer.")
                    .hoverEvent(HoverEvent.showText(Component.text("Copiez cette commande et ajoutez un message ou une commande à la suite.")))
                    .clickEvent(ClickEvent.suggestCommand("/sudo " + target.getUsername() + " "))
                    .color(NamedTextColor.RED));
        } else {
            if (message.startsWith("/")) {
                server.getCommandManager().executeAsync(target, message.substring(1));
            } else {
                target.spoofChatInput(message);
            }
            server.getConsoleCommandSource().sendMessage(Component.text(
                    ChatColor.translateAlternateColorCodes('&', "&c" + player.getUsername() + " &6a utilisé sudo pour faire dire ou utiliser " + "&8" + message + " &6par " + "&c" + target.getUsername())
            ));
        }
    }
}
