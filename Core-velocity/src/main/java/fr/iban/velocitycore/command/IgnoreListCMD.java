package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.iban.common.data.Account;
import fr.iban.velocitycore.CoreVelocityPlugin;
import fr.iban.velocitycore.manager.AccountManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;
import revxrsal.commands.velocity.exception.SenderNotPlayerException;

import java.util.UUID;

public class IgnoreListCMD {

    private final CoreVelocityPlugin plugin;
    private final ProxyServer server;

    public IgnoreListCMD(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    @Command("ignorelist")
    @Description("Affiche la liste des joueurs ignorés par le joueur.")
    public void execute(Player player) throws SenderNotPlayerException {
        AccountManager accountManager = plugin.getAccountManager();
        Account account = accountManager.getAccount(player.getUniqueId());
        if (!account.getIgnoredPlayers().isEmpty()) {
            player.sendMessage(Component.text("|| Joueurs Ignorés ||").color(NamedTextColor.GRAY));
            for (UUID ignoredPlayer : account.getIgnoredPlayers()) {
                String playerName = server.getPlayer(ignoredPlayer).map(Player::getUsername).orElse("Joueur Inconnu");
                player.sendMessage(Component.text("- " + playerName, NamedTextColor.GREEN));
            }
        } else {
            player.sendMessage(Component.text("Vous n'ignorez personne.", NamedTextColor.RED));
        }
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
    }
}
