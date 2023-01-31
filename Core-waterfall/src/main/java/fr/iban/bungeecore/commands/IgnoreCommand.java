package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.manager.AccountManager;
import fr.iban.common.data.Account;
import fr.iban.common.data.AccountDAO;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import revxrsal.commands.annotation.Command;

import java.util.UUID;

public class IgnoreCommand {


    private final CoreBungeePlugin plugin;

    public IgnoreCommand(CoreBungeePlugin coreBungeePlugin) {
        this.plugin = coreBungeePlugin;
    }

    @Command("ignore")
    public void ignore(ProxiedPlayer sender, ProxiedPlayer target) {
        if (sender.getUniqueId() != target.getUniqueId()) {
            AccountManager accountManager = plugin.getAccountManager();
            Account account = accountManager.getAccount(sender.getUniqueId());
            if (!account.getIgnoredPlayers().contains(target.getUniqueId())) {
                if (!target.hasPermission("spartacube.staff")) {
                    account.getIgnoredPlayers().add(target.getUniqueId());
                    sender.sendMessage(new TextComponent("§aVous ignorez maintenant §7" + target.getName() + "§a."));
                    accountManager.saveAccount(account);
                } else {
                    sender.sendMessage(new TextComponent("§cVous ne pouvez pas ignorer un membre du staff !"));
                }
            } else {
                account.getIgnoredPlayers().remove(target.getUniqueId());
                sender.sendMessage(new TextComponent("§aVous n'ignorez plus §7" + target.getName() + "§a."));
                ProxyServer.getInstance().getScheduler().runAsync(plugin, () -> new AccountDAO().removeIgnoredPlayerFromDB(sender.getUniqueId(), target.getUniqueId()));
            }
        } else {
            sender.sendMessage(new TextComponent("§cVous ne pouvez pas vous ignorer vous même."));
        }
    }
}
