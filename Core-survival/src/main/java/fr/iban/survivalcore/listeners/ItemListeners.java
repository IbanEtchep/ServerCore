package fr.iban.survivalcore.listeners;

import fr.iban.survivalcore.SurvivalCorePlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

import static org.bukkit.Material.BOW;
import static org.bukkit.Material.GOLDEN_SWORD;

public class ItemListeners implements Listener {

    @EventHandler
    public void onDespawn(ItemDespawnEvent e) {
        if(e.getEntity().canMobPickup()) {
            Material type = e.getEntity().getItemStack().getType();
            if(type == BOW || type == GOLDEN_SWORD) return;
            SurvivalCorePlugin.getInstance().getLogger().info("despawn d'item : " + e.getEntity().getItemStack().getType());
        }
    }

}
