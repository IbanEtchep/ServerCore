package fr.iban.bukkitcore.commands.teleport;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.manager.TeleportManager;
import fr.iban.common.teleport.RequestType;
import fr.iban.common.teleport.TpRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpyesCMD implements CommandExecutor {


    private final CoreBukkitPlugin plugin;
    private final TeleportManager teleportManager;

    public TpyesCMD(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
        this.teleportManager = plugin.getTeleportManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("core.tpyes")) {
                player.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande.");
                return false;
            }

            if (args.length == 1) {
                UUID target = plugin.getPlayerManager().getOnlinePlayerUUID(args[0]);
                if (target != null) {
                    TpRequest request = teleportManager.getTpRequestFrom(player, target);
                    if (request != null) {
                        if (request.getRequestType() == RequestType.TP) {
                            teleportManager.teleport(target, player.getUniqueId(), 3);
                        } else if (request.getRequestType() == RequestType.TPHERE) {
                            teleportManager.teleport(player.getUniqueId(), target, 3);
                        }
                        player.sendMessage("§aDemande de téléportation acceptée.");
                        teleportManager.removeTpRequest(player.getUniqueId(), request);
                    } else {
                        player.sendMessage("§cVous n'avez pas de requête de téléportation de ce joueur.");
                    }
                } else {
                    player.sendMessage("§cCe joueur n'est pas en ligne.");
                }
            } else if (args.length == 0 && !teleportManager.getTpRequests(player).isEmpty()) {
                TpRequest request = teleportManager.getTpRequests(player).get(teleportManager.getTpRequests(player).size() - 1);
                if (request.getRequestType() == RequestType.TP) {
                    teleportManager.teleport(request.getPlayerFrom(), request.getPlayerTo(), 3);
                } else if (request.getRequestType() == RequestType.TPHERE) {
                    teleportManager.teleport(request.getPlayerTo(), request.getPlayerFrom(), 3);
                }
                player.sendMessage("§aDemande de téléportation acceptée.");
                teleportManager.removeTpRequest(player.getUniqueId(), request);
            } else {
                player.sendMessage("§cVous n'avez pas de requête de téléportation.");
            }

        }
        return false;
    }


}
