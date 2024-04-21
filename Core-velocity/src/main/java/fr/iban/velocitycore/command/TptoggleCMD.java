package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import fr.iban.velocitycore.CoreVelocityPlugin;
import fr.iban.velocitycore.manager.AccountManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.velocity.annotation.CommandPermission;

public class TptoggleCMD {

    private final CoreVelocityPlugin plugin;

    public TptoggleCMD(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Command("tptoggle")
    @Description("Active ou désactive les demandes de téléportation pour le joueur.")
    @CommandPermission("servercore.tptoggle")
    public void execute(Player player) {
        AccountManager accountManager = plugin.getAccountManager();
        Account account = accountManager.getAccount(player.getUniqueId());
        account.toggleOption(Option.TP);
        accountManager.saveAccount(account);

        if (account.getOption(Option.TP)) {
            player.sendMessage(Component.text("Vos demandes de téléportation sont maintenant ouvertes.", NamedTextColor.GREEN));
        } else {
            player.sendMessage(Component.text("Vos demandes de téléportation sont maintenant fermées.", NamedTextColor.RED));
        }
    }
}
