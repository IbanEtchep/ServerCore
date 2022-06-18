package fr.iban.bukkitcore.manager;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.teleport.PlayerRTP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RessourcesWorldManager {

    private Map<UUID, Long> lastTeleportTime = new HashMap<>();

    public void sendToRessourceWorld(Player player, String worldname) {
        int secondsLeft = getSecondsLeft(player.getUniqueId());

        if(secondsLeft > 0){
            player.sendMessage("§cVous pourrez vous retéléporter en ressources dans " + secondsLeft + " secondes.");
            return;
        }

        lastTeleportTime.put(player.getUniqueId(), System.currentTimeMillis());
        player.sendMessage("§aTéléportation au monde ressource.");
        CoreBukkitPlugin plugin = CoreBukkitPlugin.getInstance();
        if(plugin.getServerName().equalsIgnoreCase(plugin.getConfig().getString("ressources-servername"))) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rtp player " + player.getName() + " " + worldname);
        }else {
            plugin.getMessagingManager().sendMessageAsync("PlayerRTP", new PlayerRTP(player.getUniqueId(), worldname));
            PluginMessageHelper.sendPlayerToServer(player, plugin.getConfig().getString("ressources-servername"));
        }
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
