package fr.iban.bukkitcore.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.iban.bukkitcore.utils.ItemBuilder;
import fr.iban.bukkitcore.utils.PluginMessageHelper;

public class ServeurMenu extends Menu {

	public ServeurMenu(Player player) {
		super(player);
	}

	@Override
	public String getMenuName() {
		return "§dSélectionnez un serveur :";
	}

	@Override
	public int getRows() {
		return 1;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if(e.getClickedInventory() == e.getView().getTopInventory()) {
			if(e.getCurrentItem().getType() == Material.GRASS) {
				PluginMessageHelper.sendPlayerToServer(player, "Survie");
			}else if (e.getCurrentItem().getType() == Material.IRON_PICKAXE) {
				new RessourceMenu(player).open();
			}
		}
	}

	@Override
	public void setMenuItems() {
		inventory.setItem(2, new ItemBuilder(Material.GRASS).setName("§2§lSurvie").setLore("§aCliquez pour rejoindre le serveur survie.").build());
		inventory.setItem(6, new ItemBuilder(Material.IRON_PICKAXE).setName("§2§lRessources").setLore("§aCliquez pour rejoindre le serveur ressources.").build());
		for(int i = 0 ; i < inventory.getSize() ; i++) {
			if(inventory.getItem(i) == null)
				inventory.setItem(i, FILLER_GLASS);
		}
	}
}