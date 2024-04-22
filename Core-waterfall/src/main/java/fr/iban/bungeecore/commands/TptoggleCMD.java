package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.manager.AccountManager;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TptoggleCMD extends Command {

    private CoreBungeePlugin plugin;

    public TptoggleCMD(String name, CoreBungeePlugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer player) {
            AccountManager accountManager = plugin.getAccountManager();
            Account account = accountManager.getAccount(player.getUniqueId());
            account.toggleOption(Option.TP);
            accountManager.saveAccount(account);
            if (account.getOption(Option.TP)) {
                player.sendMessage(new TextComponent("§aVos demande de téléportation sont maintenant ouvertes"));
            } else {
                player.sendMessage(new TextComponent("§cVos demande de téléportation sont maintenant fermées"));
            }
        }
    }
}