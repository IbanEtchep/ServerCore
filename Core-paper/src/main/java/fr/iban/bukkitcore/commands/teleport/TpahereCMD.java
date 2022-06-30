package fr.iban.bukkitcore.commands.teleport;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.manager.TeleportManager;
import fr.iban.common.teleport.RequestType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpahereCMD implements CommandExecutor {

    private final CoreBukkitPlugin plugin;
    private TeleportManager teleportManager;

    public TpahereCMD(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
        this.teleportManager = plugin.getTeleportManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("core.tpahere")) {
                player.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande.");
                return false;
            }

            if (args.length == 1) {
                UUID target = plugin.getPlayerManager().getOnlinePlayerUUID(args[0]);

                if (target != null) {

                    if (target.equals(player.getUniqueId())) {
                        player.sendMessage("§cVous ne pouvez pas vous téléporter à vous même.");
                        return false;
                    }

                    teleportManager.sendTeleportRequest(player.getUniqueId(), target, RequestType.TPHERE);

                } else {
                    player.sendMessage("§cCe joueur n'est pas en ligne.");
                }
            }
        }
        return false;
    }


}