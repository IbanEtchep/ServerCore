package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import fr.iban.common.messaging.AbstractMessenger;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.*;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;

import java.io.IOException;

public class CoreCMD {

    private final CoreVelocityPlugin plugin;

    public CoreCMD(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Command("bcore")
    @Description("Affiche les options de commande pour le serveur.")
    public void core(Player player) {
        Component message = Component.text("Utilisez ", NamedTextColor.GRAY)
                .append(Component.text("/bcore reload", NamedTextColor.GREEN))
                .append(Component.text(" pour recharger la configuration.\n", NamedTextColor.GRAY))
                .append(Component.text("/bcore debug", NamedTextColor.GREEN))
                .append(Component.text(" pour basculer le mode débogage.", NamedTextColor.GRAY));
        player.sendMessage(message);
    }

    @Subcommand("reload")
    @CommandPermission("servercore.reload")
    @Description("Recharge la configuration du serveur.")
    @Usage("/bcore reload")
    public void reloadConfig(Player player) throws IOException {
        plugin.getConfig().reload();
        player.sendMessage(Component.text("La configuration a été rechargée avec succès.", NamedTextColor.GREEN));
    }

    @Subcommand("debug")
    @CommandPermission("servercore.debug")
    @Description("Active ou désactive le mode débogage.")
    @Usage("/bcore debug")
    public void toggleDebug(Player player) {
        AbstractMessenger messenger = plugin.getMessagingManager().getMessenger();
        messenger.setDebugMode(!messenger.isDebugMode());
        player.sendMessage(Component.text("Mode débogage : " + (messenger.isDebugMode() ? "activé" : "désactivé"), NamedTextColor.YELLOW));
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
    }
}
