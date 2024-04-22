package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.iban.common.data.Account;
import fr.iban.velocitycore.CoreVelocityPlugin;
import fr.iban.velocitycore.manager.AccountManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.*;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;

@Command("ignore")
public class IgnoreCommand {

    private final CoreVelocityPlugin plugin;
    private final ProxyServer server;

    public IgnoreCommand(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    @Subcommand("help")
    @DefaultFor("ignore")
    @Description("Affiche les options de la commande ignore.")
    public void ignore(Player player) {
        Component message = Component.text("Utilisez ", NamedTextColor.GRAY)
                .append(Component.text("/ignore add <joueur>", NamedTextColor.GREEN))
                .append(Component.text(" pour ignorer un joueur.\n", NamedTextColor.GRAY))
                .append(Component.text("/ignore remove <joueur>", NamedTextColor.GREEN))
                .append(Component.text(" pour ne plus ignorer un joueur.", NamedTextColor.GRAY));
        player.sendMessage(message);
    }

    @Subcommand("add")
    @Description("Ajoute un joueur à la liste des ignorés.")
    @Usage("/ignore add <joueur>")
    public void addIgnore(Player player, @Named("joueur") Player target) {
        if (!player.getUniqueId().equals(target.getUniqueId())) {
            AccountManager accountManager = plugin.getAccountManager();
            Account account = accountManager.getAccount(player.getUniqueId());
            if (!account.getIgnoredPlayers().contains(target.getUniqueId())) {
                if (!target.hasPermission("servercore.staff")) {
                    account.getIgnoredPlayers().add(target.getUniqueId());
                    player.sendMessage(Component.text("Vous ignorez maintenant " + target.getUsername() + ".", NamedTextColor.GREEN));
                    accountManager.saveAccount(account);
                } else {
                    player.sendMessage(Component.text("Vous ne pouvez pas ignorer un membre du staff !", NamedTextColor.RED));
                }
            } else {
                player.sendMessage(Component.text("Ce joueur est déjà ignoré.", NamedTextColor.RED));
            }
        } else {
            player.sendMessage(Component.text("Vous ne pouvez pas vous ignorer vous-même.", NamedTextColor.RED));
        }
    }

    @Subcommand("remove")
    @Description("Retire un joueur de la liste des ignorés.")
    @Usage("/ignore remove <joueur>")
    public void removeIgnore(Player player, @Named("joueur") Player target) {
        AccountManager accountManager = plugin.getAccountManager();
        Account account = accountManager.getAccount(player.getUniqueId());
        if (account.getIgnoredPlayers().contains(target.getUniqueId())) {
            account.getIgnoredPlayers().remove(target.getUniqueId());
            player.sendMessage(Component.text("Vous n'ignorez plus " + target.getUsername() + ".", NamedTextColor.GREEN));
        } else {
            player.sendMessage(Component.text("Ce joueur n'est pas ignoré.", NamedTextColor.RED));
        }
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
    }
}
