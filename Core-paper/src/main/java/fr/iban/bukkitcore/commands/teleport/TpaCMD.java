package fr.iban.bukkitcore.commands.teleport;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.teleport.TeleportManager;
import fr.iban.common.teleport.RequestType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpaCMD implements CommandExecutor {

    private final CoreBukkitPlugin plugin;
    private TeleportManager teleportManager;

    public TpaCMD(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
        this.teleportManager = plugin.getTeleportManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(!player.hasPermission("core.tpa")){
                player.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande.");
                return false;
            }

            if(args.length == 1){
                plugin.getProxiedPlayerUUID(args[0]).thenAcceptAsync(target -> {

                    if(target != null){

                        if(target.equals(player.getUniqueId())) {
                            player.sendMessage("§cVous ne pouvez pas vous téléporter à vous même.");
                            return;
                        }

                        teleportManager.sendTeleportRequest(player.getUniqueId(), target, RequestType.TP);

                    }else{
                        player.sendMessage("§cCe joueur n'est pas en ligne.");
                    }
                });
            }
        }
        return false;
    }


}
