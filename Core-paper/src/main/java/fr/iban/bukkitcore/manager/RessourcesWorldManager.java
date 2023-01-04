package fr.iban.bukkitcore.manager;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.common.teleport.PlayerRTP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RessourcesWorldManager {

    private final Map<UUID, Long> lastTeleportTime = new HashMap<>();

    public void sendToRessourceWorld(Player player, String worldname) {
        int secondsLeft = getSecondsLeft(player.getUniqueId());

        if(secondsLeft > 0){
            player.sendMessage("§cVous pourrez vous retéléporter en ressources dans " + secondsLeft + " secondes.");
            return;
        }

        lastTeleportTime.put(player.getUniqueId(), System.currentTimeMillis());
        player.sendMessage("§aTéléportation au monde ressource.");
        CoreBukkitPlugin plugin = CoreBukkitPlugin.getInstance();
        String ressourcesServer = plugin.getConfig().getString("ressources-servername", "ressources");
        plugin.getTeleportManager().randomTeleport(player, ressourcesServer, worldname);
    }

    private int getSecondsLeft(UUID uuid){
        if(lastTeleportTime.containsKey(uuid)){
            long lastTp = lastTeleportTime.get(uuid);
            if(System.currentTimeMillis() - lastTp < 60000){
                return (int) ((60000 - (System.currentTimeMillis() - lastTp))/1000);
            }else{
                lastTeleportTime.remove(uuid);
            }
        }
        return 0;
    }

}
