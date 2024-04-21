package fr.iban.bukkitcore.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CustomEnderChestMenu extends Menu {

    private final ItemStack[] contents;

    public CustomEnderChestMenu(Player player, ItemStack[] contents) {
        super(player);
        this.contents = contents;
    }

    @Override
    public String getMenuName() {
        return "§6Ender Chest";
    }

    @Override
    public int getRows() {
        for (int i = 6; i >= 1; i--) {
            if (player.hasPermission("servercore.enderchest." + i)) {
                return i;
            }
        }
        return 3;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        e.setCancelled(false);
    }

    @Override
    public void setMenuItems() {
        int index = 0;
        for (ItemStack item : contents) {
            if (item != null && index < inventory.getSize()) {
                inventory.setItem(index, item);
            }
            index++;
        }
        setFillerGlass();
    }
}
