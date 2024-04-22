package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import fr.iban.common.data.Account;
import fr.iban.velocitycore.CoreVelocityPlugin;
import fr.iban.velocitycore.manager.AccountManager;
import fr.iban.velocitycore.manager.AnnoncesManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.*;
import revxrsal.commands.velocity.VelocityCommandActor;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;

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
    public void disableAnnouncement(VelocityCommandActor actor, Player player, @Default("0") int id) {
        AnnoncesManager announceManager = plugin.getAnnounceManager();
        if (announceManager.getAnnonces().containsKey(id)) {
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
