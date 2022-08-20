package fr.iban.bukkitcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.messaging.AbstractMessenger;
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
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                plugin.setServerName(plugin.getConfig().getString("servername"));
                sender.sendMessage("§apapercore reloaded.");
            } else if (args[0].equalsIgnoreCase("debug")) {
                AbstractMessenger messenger = plugin.getMessagingManager().getMessenger();
                messenger.setDebugMode(!messenger.isDebugMode());
                sender.sendMessage("debug mode : " + messenger.isDebugMode());
            }
        }
        return false;
    }

}
