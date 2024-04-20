package fr.iban.velocitycore.command;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.velocity.VelocityCommandHandler;
import revxrsal.commands.velocity.annotation.CommandPermission;

import java.util.ArrayList;
import java.util.List;

public class StaffChatToggle {

    public static List<Player> sc = new ArrayList<>();

    @Command({"sctoggle", "staffchattoggle"})
    @CommandPermission("servercore.sctoggle")
    @Description("Permet de basculer la réception des messages du staff.")
    public void toggleStaffChat(Player player) {
        if (sc.contains(player)) {
            sc.remove(player);
            player.sendMessage(Component.text("Vous pouvez à nouveau recevoir des messages du staff", NamedTextColor.GREEN));
        } else {
            sc.add(player);
            player.sendMessage(Component.text("Vous ne pouvez plus recevoir les messages du staff", NamedTextColor.RED));
        }
    }

    public void setupCommands(VelocityCommandHandler handler) {
        handler.register(this);
    }
}
