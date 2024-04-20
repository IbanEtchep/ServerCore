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
    @AutoComplete("@playerNames")
    @CommandPermission("servercore.msg")
    @Description("Envoyer un message privé à un joueur.")
    @Usage("/msg <joueur> <message>")
    public void msg(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(Component.text("/msg [Joueur] [Message]").color(NamedTextColor.YELLOW));
            return;
        }

        if (args.length < 2) {
            player.sendMessage(Component.text("Veuillez entrer un message.").color(NamedTextColor.RED));
            return;
        }

        Player target = server.getPlayer(args[0]).orElse(null);
        if (target == null) {
            player.sendMessage(Component.text(args[0] + " est hors-ligne !").color(NamedTextColor.RED));
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String message = sb.toString().trim();

        plugin.getChatManager().sendMessage(player, target, message);
    }

    private List<String> suggestPlayers(Player player) {
        List<String> suggestions = new ArrayList<>();
        for (Player p : server.getAllPlayers()) {
            if (!player.equals(p) && p.isActive()) {
                suggestions.add(p.getUsername());
            }
        }
        return suggestions;
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
        handler.getAutoCompleter().registerSuggestion("playerNames", (args, sender, command) -> suggestPlayers((Player) sender));
    }
}
