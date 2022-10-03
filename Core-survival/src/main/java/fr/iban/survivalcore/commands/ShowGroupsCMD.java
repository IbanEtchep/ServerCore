package fr.iban.survivalcore.commands;

import fr.iban.survivalcore.utils.LuckPermsUtils;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class ShowGroupsCMD {

    @Command("showgroups")
    @CommandPermission("core.showgroups")
    public void showGroups(Player sender) {
        LuckPermsUtils.showGroups(sender);
    }
}
