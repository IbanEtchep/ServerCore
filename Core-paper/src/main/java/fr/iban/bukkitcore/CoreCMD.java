package fr.iban.bukkitcore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CoreCMD implements CommandExecutor {

    private CoreBukkitPlugin plugin;

    public CoreCMD(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage("§apapercore reloaded.");
        return false;
    }

}
