package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import fr.iban.common.data.Account;
import fr.iban.velocitycore.CoreVelocityPlugin;
import fr.iban.velocitycore.manager.AccountManager;
import fr.iban.velocitycore.manager.AutomatedAnnounceManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.velocity.VelocityCommandActor;
import revxrsal.commands.velocity.VelocityCommandHandler;

@Command("announce")
@Description("Commandes pour gérer les annonces.")
public class AnnounceCMD {

    private final CoreVelocityPlugin plugin;

    public AnnounceCMD(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Subcommand("listdisabled")
    @Description("Liste toutes les annonces désactivées par l'utilisateur.")
    public void listDisabledAnnouncements(VelocityCommandActor actor, Player player) {
        AccountManager accountManager = plugin.getAccountManager();
        Account account = accountManager.getAccount(player.getUniqueId());
        for (int idA : account.getBlackListedAnnounces()) {
            player.sendMessage(Component.text("- " + idA));
        }
    }

    @Subcommand("disable")
    @Description("Désactive une annonce spécifique pour l'utilisateur.")
    public void disableAnnouncement(Player player, @Default("0") int id) {
        AutomatedAnnounceManager announceManager = plugin.getAnnounceManager();
        if (announceManager.getAnnounces().containsKey(id)) {
            AccountManager accountManager = plugin.getAccountManager();
            Account account = accountManager.getAccount(player.getUniqueId());
            if (!account.getBlackListedAnnounces().contains(id)) {
                account.getBlackListedAnnounces().add(id);
                player.sendMessage(Component.text("Cette annonce ne vous sera plus affichée à l'avenir.").color(NamedTextColor.GREEN));
                accountManager.saveAccount(account);
            } else {
                player.sendMessage(Component.text("Vous avez déjà bloqué cette annonce.").color(NamedTextColor.RED));
            }
        } else {
            player.sendMessage(Component.text("Cette annonce n'existe pas.").color(NamedTextColor.RED));
        }
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
    }
}
