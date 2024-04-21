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
    public void execute(VelocityCommandActor sender, Player target, String message) {
        if (message.startsWith("/")) {
            server.getCommandManager().executeAsync(target, message.substring(1));
        } else {
            target.spoofChatInput(message);
        }
    }
}
