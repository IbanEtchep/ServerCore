package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;

public class ChatCMD {

    private final CoreVelocityPlugin plugin;
    private final ProxyServer server;

    public ChatCMD(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    @Command("chat")
    @Description("Affiche les commandes de gestion du chat.")
    public void chat(Player player) {
        player.sendMessage(Component.text("Commandes chat :", NamedTextColor.GREEN));
        player.sendMessage(Component.text("/chat toggle - mute/unmute le chat", NamedTextColor.GREEN));
    }

    @Subcommand("clear")
    @CommandPermission("servercore.chatmanage")
    @Description("Efface le chat pour tous les utilisateurs.")
    public void clearChat(Player player) {
        Component emptyMessage = Component.empty();
        server.getAllPlayers().forEach(p -> {
            for (int i = 0; i < 200; i++) {
                p.sendMessage(emptyMessage);
            }
        });
        server.getAllPlayers().forEach(p -> p.sendMessage(Component.text("Le chat a été clear par " + player.getUsername() + ".", NamedTextColor.RED)));
    }

    @Subcommand("toggle")
    @CommandPermission("servercore.chatmanage")
    @Description("Active ou désactive le chat.")
    public void toggleChat(Player player) {
        plugin.getChatManager().toggleChat(player);
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
    }
}
