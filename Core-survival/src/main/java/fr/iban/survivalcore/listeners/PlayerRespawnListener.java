package fr.iban.survivalcore.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.warps.WarpsPlugin;
import fr.iban.warps.objects.Warp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRespawnListener implements Listener {


    @EventHandler
    public void onPostRespawn(PlayerPostRespawnEvent e) {
        Player player = e.getPlayer();
        Location bedLocation = player.getRespawnLocation();

        if (bedLocation != null && e.getRespawnedLocation().equals(bedLocation)) {
            return;
        }

        try {
            Warp spawn = WarpsPlugin.getInstance().getWarpManager().getWarp("spawn");
            if(spawn != null) {
                CoreBukkitPlugin.getInstance().getTeleportManager().teleport(player, spawn.getLocation());
            }
        } catch (NoClassDefFoundError ignored) {
        }
    }

}