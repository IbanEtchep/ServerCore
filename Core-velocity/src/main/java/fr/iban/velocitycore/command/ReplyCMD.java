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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplyCMD {

    private static final Map<Player, Player> replies = new HashMap<>();
    private final CoreVelocityPlugin plugin;

    public ReplyCMD(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Command({"reply", "r"})
    @Description("Répondre au dernier joueur qui vous a contacté.")
    @Usage("/reply <message> - Répond au dernier message reçu.")
    public void reply(Player player, @Default("") @Named("message") String message) {
        if (message.isEmpty()) {
            player.sendMessage(Component.text("Utilisation: /r <message>").color(NamedTextColor.YELLOW));
            return;
        }

        if (replies.containsKey(player)) {
            Player target = replies.get(player);
            if (target == null || !target.isActive()) {
                player.sendMessage(Component.text("Le joueur est hors-ligne!", NamedTextColor.RED));
                return;
            }

            plugin.getChatManager().sendMessage(player, target, message.trim());
        } else {
            player.sendMessage(Component.text("Tu ne peux pas répondre, car personne ne t'a écrit.", NamedTextColor.RED));
        }
    }

    public static Map<Player, Player> getReplies() {
        return replies;
    }
}