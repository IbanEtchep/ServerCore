package fr.iban.bukkitcore.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.iban.bukkitcore.utils.ItemBuilder;
import fr.iban.bukkitcore.utils.Options;
import fr.iban.common.data.Account;
import fr.iban.common.data.AccountProvider;

public class OptionsMenu extends PaginatedMenu{

	private OptionsMenu previousMenu;

	public OptionsMenu(Player player) {
		super(player);
	}


	@Override
	public String getMenuName() {
		return "§2Vos paramètres";
	}

	@Override
	public int getRows() {
		return 3;
	}

	@Override
	public int getElementAmount() {
		return Options.values().length;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		ItemStack item = e.getCurrentItem();
		AccountProvider ap = new AccountProvider(e.getWhoClicked().getUniqueId());
		Account account = ap.getAccount();

		checkBottonsClick(item, (Player) e.getWhoClicked());
		if(previousMenu != null && displayNameEquals(item, "§4Retour")) {
			previousMenu.open();
			return;
		}
		
		if(Options.getByDisplayName(item.getItemMeta().getDisplayName()) != null) {
			if(item.getItemMeta().getDisplayName().startsWith("§4")) {
				account.setOption(Options.getByDisplayName(item.getItemMeta().getDisplayName()).getOption(), true);
				ap.sendAccountToRedis(account);
			}else {
				account.setOption(Options.getByDisplayName(item.getItemMeta().getDisplayName()).getOption(), false);
				ap.sendAccountToRedis(account);
			}
			super.open();
		}
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		for(int i = 0; i < getMaxItemsPerPage(); i++) {
			index = getMaxItemsPerPage() * page + i;
			if(index >= Options.values().length) break;
			Options option = Options.values()[index];
			AccountProvider ap = new AccountProvider(player.getUniqueId());
			Account account = ap.getAccount();
			if (option != null){
				if(account.getOption(option.getOption())) {
					inventory.addItem(new ItemBuilder(option.getItem()).setName("§2" + option.getDisplayName()).build());
				}else {
					inventory.addItem(new ItemBuilder(option.getItem()).setName("§4" + option.getDisplayName()).build());
				}
			}
		}

		if(previousMenu != null) {
			inventory.setItem(31, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§4Retour")
					.addLore("§cRetourner au menu précédent")
					.build());
		}
	}

}