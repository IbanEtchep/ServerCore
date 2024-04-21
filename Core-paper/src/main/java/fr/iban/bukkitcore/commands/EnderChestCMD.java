package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.menu.CustomEnderChestMenu;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.bukkit.BukkitCommandActor;

public class EnderChestCMD {

    private CoreBukkitPlugin plugin;

    public EnderChestCMD(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Command({"ec", "enderchest"})
    @CommandPermission("enderchest.use")
    @Description("Ouvre l'ender chest du joueur.")
    public void openEnderChest(BukkitCommandActor actor) {
        Player player = actor.requirePlayer();
        CustomEnderChestMenu menu = (CustomEnderChestMenu) plugin.getEnderChestManager().getMenu(player.getUniqueId());
        if (menu == null) {
            actor.reply("§cVotre ender chest n'est pas chargé.");
            return;
        }
        menu.open();
    }
}
