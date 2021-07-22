package fr.iban.bukkitcore.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.utils.ItemBuilder;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.teleport.PlayerRTP;

public class RessourceMenu extends Menu {

	public RessourceMenu(Player player) {
		super(player);
	}

	@Override
	public String getMenuName() {
		return "§dSélectionnez un monde :";
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if(e.getClickedInventory() == e.getView().getTopInventory()) {
			if(e.getCurrentItem().getType() == Material.GRASS_BLOCK) {
				sendToWorld("resource_world");
			}else if (e.getCurrentItem().getType() == Material.NETHERRACK) {
				sendToWorld("resource_nether");
			}else if (e.getCurrentItem().getType() == Material.END_STONE) {
				sendToWorld("resource_end");
			}
		}
	}

	@Override
	public void setMenuItems() {
		inventory.setItem(2, new ItemBuilder(Material.GRASS_BLOCK).setName("§2§lNormal").setLore("§aCliquez pour rejoindre le monde ressource normal.").build());
		inventory.setItem(4, new ItemBuilder(Material.NETHERRACK).setName("§4§lNether").setLore("§aCliquez pour rejoindre le monde ressource nether.").build());
		inventory.setItem(6, new ItemBuilder(Material.END_STONE).setName("§5§lEnder").setLore("§aCliquez pour rejoindre le monde ressource ender.").build());

		for(int i = 0 ; i < inventory.getSize() ; i++) {
			if(inventory.getItem(i) == null)
				inventory.setItem(i, FILLER_GLASS);
		}
	}
	
	private void sendToWorld(String worldname) {
		CoreBukkitPlugin plugin = CoreBukkitPlugin.getInstance();
		if(plugin.getServerName().equalsIgnoreCase("ressources")) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rtp player " + player.getName() + " " + worldname);
		}else {
			RedisAccess.getInstance().getRedissonClient().getTopic("PlayerRTP").publish(new PlayerRTP(player.getUniqueId(), worldname));
			PluginMessageHelper.sendPlayerToServer(player, "Ressources");
		}
	}

	@Override
	public int getRows() {
		return 1;
	}
}