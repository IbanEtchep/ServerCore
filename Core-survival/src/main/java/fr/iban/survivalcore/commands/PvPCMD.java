package fr.iban.survivalcore.commands;

import fr.iban.common.data.Account;
import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Option;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PvPCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0) {
                AccountProvider ap = new AccountProvider(player.getUniqueId());
                Account account = ap.getAccount();
                account.toggleOption(Option.PVP);
                if(account.getOption(Option.PVP)) {
                    player.sendMessage("§aPVP activé.");
                }else {
                    player.sendMessage("§cPVP desactivé.");
                }
                ap.sendAccountToRedis(account);
            }else if(args.length == 1 && sender.hasPermission("servercore.pvp.others")) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    AccountProvider ap = new AccountProvider(target.getUniqueId());
                    Account account = ap.getAccount();
                    account.toggleOption(Option.PVP);
                    if(account.getOption(Option.PVP)) {
                        player.sendMessage("§aPVP de "+ target.getName() +" activé.");
                    }else {
                        player.sendMessage("§cPVP "+ target.getName() +" desactivé.");
                    }
                    ap.sendAccountToRedis(account);
                }
            }
        }
        return false;
    }
}
