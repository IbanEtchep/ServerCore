package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;

public class MsgToggleCMD {

    private final CoreVelocityPlugin plugin;

    public MsgToggleCMD(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Command("msgtoggle")
    @CommandPermission("servercore.msgtoggle")
    @Description("Permet d'activer ou désactiver la réception de messages de la part des autres joueurs.")
    public void execute(Player player) {
        Account account = plugin.getAccountManager().getAccount(player.getUniqueId());

        if (account.getOption(Option.MSG)) {
            account.setOption(Option.MSG, false);
            player.sendMessage(Component.text("Vous ne pouvez plus recevoir les messages des joueurs", NamedTextColor.RED));
        } else {
            account.setOption(Option.MSG, true);
            player.sendMessage(Component.text("Vous pouvez à nouveau recevoir les messages des joueurs", NamedTextColor.GREEN));
        }
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
    }
}
