package fr.iban.survivalcore.listeners;

import fr.iban.survivalcore.utils.Scheduler;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class PortalListeners implements Listener {


    @EventHandler
    public void onPortalCreate(PortalCreateEvent e) {
        if (e.getReason() == CreateReason.FIRE && e.getWorld().getEnvironment() == Environment.NETHER) {
            if (!e.getBlocks().isEmpty() && e.getBlocks().get(0).getLocation().getY() >= 128) {
                Scheduler.runLater(() -> {
                    for (BlockState block : e.getBlocks()) {
                        if (block.getType() == Material.NETHER_PORTAL) {
                            block.getBlock().breakNaturally();
                            break;
                        }
                    }
                }, 6000);
            }
        }
    }
}
