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
    @AutoComplete("@playerNames")
    @CommandPermission("servercore.sudo")
    @Usage("/sudo <joueur> <message|commande>")
    public void execute(Player player, @Flag("target") Player player2, @Optional String message) {

        if (player2.hasPermission("servcore.sudobypass") && !player.hasPermission("servercore.adminsudo")) {
            player.sendMessage(Component.text("Désolé, ce joueur a la permission de bypass. Vous ne pouvez pas utiliser sudo sur lui.", NamedTextColor.RED));
        } else if (message == null || message.isEmpty()) {
            player.sendMessage(Component.text("Veuillez spécifier un message ou une commande à envoyer.")
                    .hoverEvent(HoverEvent.showText(Component.text("Copiez cette commande et ajoutez un message ou une commande à la suite.")))
                    .clickEvent(ClickEvent.suggestCommand("/sudo " + player2.getUsername() + " "))
                    .color(NamedTextColor.RED));
        } else {
            if (message.startsWith("/")) {
                server.getCommandManager().executeAsync(player2, message.substring(1));
            } else {
                player2.spoofChatInput(message);
            }
            server.getConsoleCommandSource().sendMessage(Component.text(
                    ChatColor.translateAlternateColorCodes('&', "&c" + player.getUsername() + " &6a utilisé sudo pour faire dire ou utiliser " + "&8" + message + " &6par " + "&c" + player2.getUsername())
            ));
        }

//        if (args.length == 0) {
//            player.sendMessage(Component.text("Veuillez utilisez: /sudo message du joueur")
//                    .hoverEvent(HoverEvent.showText(Component.text("Copiez!")))
//                    .clickEvent(ClickEvent.suggestCommand("/sudo ")));
//        } else if (args.length == 1) {
//            player.sendMessage(Component.text("Veuillez utilisez: /sudo " + args[0] + " message")
//                    .hoverEvent(HoverEvent.showText(Component.text("Copiez!")))
//                    .clickEvent(ClickEvent.suggestCommand("/sudo " + args[0] + " ")));
//        } else {
//            Player player2 = server.getPlayer(args[0]).orElse(null);
//            if (player2 != null && player.isActive()) {
//                if (player2.hasPermission("sudo.bypass") && !player.hasPermission("sudo.bypassbypass")) {
//                    player.sendMessage(Component.text("Désolé, ce joueur a la permission de bypass. Vous ne pouvez pas le sudo.", NamedTextColor.RED));
//                } else {
//                    StringBuilder message = new StringBuilder();
//
//                    for (int i = 1; i != args.length; i++) {
//                        if (message.toString().equals("")) {
//                            message.append(args[i]);
//                        } else {
//                            message.append(" ").append(args[i]);
//                        }
//                    }
//
//                    player2.spoofChatInput(message.toString());
//                    server.getConsoleCommandSource().sendMessage(Component.text(
//                            ChatColor.translateAlternateColorCodes('&', "&c" + player.getUsername() + " &6a utilisé le sudo pour faire dire ou utiliser " + "&8" + message + " &6à " + "&c" + player2.getUsername())
//                    ));
//                }
//            } else {
//                player.sendMessage(Component.text("Désolé, ce joueur n'est pas connecté.", NamedTextColor.RED));
//            }
//        }
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
        handler.getAutoCompleter().registerSuggestion("playerNames", (args, sender, command) -> suggestPlayers((VelocityCommandActor) sender));
    }

    private List<String> suggestPlayers(VelocityCommandActor actor) {
        if (actor.getSource() instanceof Player player) {
            return server.getAllPlayers().stream()
                    .filter(p -> p.isActive() && !p.equals(player))
                    .map(Player::getUsername)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
