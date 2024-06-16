package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;

import java.util.ArrayList;
import java.util.List;

public class StaffChatToggle {

    private final CoreVelocityPlugin plugin;

    public StaffChatToggle(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Command({"sctoggle", "staffchattoggle"})
    @CommandPermission("servercore.sctoggle")
    @Description("Permet de basculer la réception des messages du staff.")
    public void toggleStaffChat(Player player) {
        plugin.getChatManager().toggleStaffChat(player);
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
    }
}
