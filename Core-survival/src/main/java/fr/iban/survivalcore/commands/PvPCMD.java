package fr.iban.survivalcore.commands;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.manager.AccountManager;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PvPCMD implements CommandExecutor {

    private CoreBukkitPlugin plugin;

    public PvPCMD(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            AccountManager accountManager = plugin.getAccountManager();
            if(args.length == 0) {
                Account account = accountManager.getAccount(player.getUniqueId());
                account.toggleOption(Option.PVP);
                if(account.getOption(Option.PVP)) {
                    player.sendMessage("§aPVP activé.");
                }else {
                    player.sendMessage("§cPVP desactivé.");
                }
                accountManager.saveAccountAsync(account);
            }else if(args.length == 1 && sender.hasPermission("servercore.pvp.others")) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    Account account = accountManager.getAccount(player.getUniqueId());
                    account.toggleOption(Option.PVP);
                    if(account.getOption(Option.PVP)) {
                        player.sendMessage("§aPVP de "+ target.getName() +" activé.");
                    }else {
                        player.sendMessage("§cPVP "+ target.getName() +" desactivé.");
                    }
                    accountManager.saveAccountAsync(account);
                }
            }
        }
        return false;
    }
}
