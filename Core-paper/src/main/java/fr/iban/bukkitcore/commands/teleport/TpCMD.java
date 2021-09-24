package fr.iban.bukkitcore.commands.teleport;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TpCMD implements CommandExecutor {

    private CoreBukkitPlugin plugin;

    public TpCMD(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(!player.hasPermission("core.tp")){
                player.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande.");
                return false;
            }

            if(args.length == 1) {
                plugin.getProxiedPlayerUUID(args[0]).thenAcceptAsync(target -> {

                    if(target != null) {
                        if(target.toString().equals(player.getUniqueId().toString())) {
                            player.sendMessage("§cVous ne pouvez pas vous téléporter à vous même.");
                            return;
                        }
                        plugin.getTeleportManager().teleport(player.getUniqueId(), target);
                    }else {
                        player.sendMessage("§cCe joueur n'est pas en ligne.");
                    }
                });
            }
        }
        return false;
    }

}
