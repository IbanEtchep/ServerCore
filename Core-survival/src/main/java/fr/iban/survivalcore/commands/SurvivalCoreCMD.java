package fr.iban.survivalcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SurvivalCoreCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("survivalcore.admin")){
            if(args.length == 1 && args[0].equalsIgnoreCase("proxyplayers")){
                sender.sendMessage(CoreBukkitPlugin.getInstance().getProxyPlayers().size() + " joueurs");
                CoreBukkitPlugin.getInstance().getProxyPlayers().forEach((name, uuid) -> {
                    sender.sendMessage("- " + name + " - " + uuid);
                });
            }
        }
        return false;
    }
}
