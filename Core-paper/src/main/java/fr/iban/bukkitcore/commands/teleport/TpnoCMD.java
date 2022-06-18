package fr.iban.bukkitcore.commands.teleport;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.manager.TeleportManager;
import fr.iban.common.teleport.TpRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpnoCMD implements CommandExecutor {

    private final CoreBukkitPlugin plugin;
    private TeleportManager teleportManager;

    public TpnoCMD(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
        this.teleportManager = plugin.getTeleportManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(!player.hasPermission("core.tpno")){
                player.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande.");
                return false;
            }

            if(args.length == 1){
                plugin.getPlayerManager().getProxiedPlayerUUID(args[0]).thenAcceptAsync(target -> {
                    if(target != null){
                        TpRequest request = teleportManager.getTpRequestFrom(player, target);
                        if(request != null){
                            player.sendMessage("§cDemande de téléportation rejetée.");
                            teleportManager.removeTpRequest(player.getUniqueId(), request);
                        }else{
                            player.sendMessage("§cVous n'avez pas de requête de téléportation de ce joueur.");
                        }
                    }else{
                        player.sendMessage("§cCe joueur n'est pas en ligne.");
                    }
                });
            }else if(args.length == 0 && !teleportManager.getTpRequests(player).isEmpty()) {
                TpRequest request = (TpRequest)teleportManager.getTpRequests(player).get(teleportManager.getTpRequests(player).size() - 1);
                player.sendMessage("§cDemande de téléportation rejetée.");
                teleportManager.removeTpRequest(player.getUniqueId(), request);
            }else {
                player.sendMessage("§cVous n'avez pas de requête de téléportation.");
            }

        }
        return false;
    }


}

