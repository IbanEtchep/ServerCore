package fr.iban.bukkitcore.listeners;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.menu.CustomEnderChestMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import fr.iban.bukkitcore.menu.Menu;

public class InventoryListener implements Listener {

    private CoreBukkitPlugin plugin;

    public InventoryListener(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }
	
    @EventHandler
    public void onMenuClick(InventoryClickEvent e){

        InventoryHolder holder = e.getInventory().getHolder();
        //If the inventoryholder of the inventory clicked on
        // is an instance of Menu, then gg. The reason that
        // an InventoryHolder can be a Menu is because our Menu
        // class implements InventoryHolder!!
        if (holder instanceof Menu) {
            e.setCancelled(true); //prevent them from fucking with the inventory
            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }
            //Since we know our inventoryholder is a menu, get the Menu Object representing
            // the menu we clicked on
            Menu menu = (Menu) holder;
            //Call the handleMenu object which takes the event and processes it
            menu.handleMenu(e);
        }

    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.ENDER_CHEST) {
            event.setCancelled(true);
            Player player = (Player) event.getPlayer();
            Menu menu = plugin.getEnderChestManager().getMenu(player.getUniqueId());
            if (menu instanceof CustomEnderChestMenu) { // Vérifiez si menu est une instance de CustomEnderChestMenu
                ((CustomEnderChestMenu) menu).open(); // Cast sécurisé et appel de la méthode open
            }
        }
    }
    
    @EventHandler
    public void onMenuClose(InventoryCloseEvent e){

        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof Menu) {
            Menu menu = (Menu) holder;
            menu.handleMenuClose(e);
        }


    }



}
