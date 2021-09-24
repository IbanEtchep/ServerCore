package fr.iban.bukkitcore.commands.teleport;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TphereCMD implements CommandExecutor {

    private CoreBukkitPlugin plugin;

    public TphereCMD(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(!player.hasPermission("core.tphere")){
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
                        plugin.getTeleportManager().teleport(target, player.getUniqueId());
                    }else {
                        player.sendMessage("§cCe joueur n'est pas en ligne.");
                    }
                });
            }
        }
        return false;
    }
}