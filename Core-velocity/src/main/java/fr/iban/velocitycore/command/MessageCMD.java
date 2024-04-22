package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.*;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;

import java.util.ArrayList;
import java.util.List;

public class MessageCMD {

    private final CoreVelocityPlugin plugin;
    private final ProxyServer server;

    public MessageCMD(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    @Command({"msg", "message", "m", "w", "tell", "t"})
    @Description("Envoyer un message privé à un joueur.")
    @Usage("/msg <joueur> <message>")
    public void msg(Player player, Player target, String message) {
        plugin.getChatManager().sendMessage(player, target, message);
    }
}
