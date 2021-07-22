package fr.iban.bukkitcore.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/*

A class extending the functionality of the regular Menu, but making it Paginated

This pagination system was made from Jer's code sample. <3

 */

public abstract class PaginatedMenu extends Menu {

	//Keep track of what page the menu is on
	protected int page = 0;
	//28 is max items because with the border set below,
	//28 empty slots are remaining.
	protected int maxItemsPerPage = getSlots() - 14 - (getRows()*2);
	//the index represents the index of the slot
	//that the loop is on
	protected int index = 0;

	
	protected PaginatedMenu(Player player) {
		super(player);
	}
	
	public int getElementAmount() {
		return -1;
	}


	//Set the border and menu buttons for the menu
	@Override
	public void addMenuBorder(){
		
		index = page*getMaxItemsPerPage();
		
		int lastRowFirst = (getRows()-1)*9;

		if(getElementAmount() != -1 && getElementAmount() > maxItemsPerPage && (index+getMaxItemsPerPage() + 1) <= getElementAmount()) {
			inventory.setItem(lastRowFirst+5, makeItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Suivant"));
		}

		if(page > 0) {
			inventory.setItem(lastRowFirst+3, makeItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Précédent"));
		}

		inventory.setItem(lastRowFirst+4, makeItem(Material.RED_STAINED_GLASS_PANE, ChatColor.DARK_RED + "Fermer"));

		super.addMenuBorder();
	}

	protected void checkBottonsClick(ItemStack item, Player player) {
		checkNextBottonClick(item);
		checkPreviousBottonClick(item);
		checkCloseBottonClick(item, player);
	}

	protected void checkNextBottonClick(ItemStack item) {
		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN  + "suivant")){
			page += 1;
			super.open();
		}
	}

	protected void checkPreviousBottonClick(ItemStack item) {
		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN  + "précédent")){
			page -= 1;
			if(page == 0) {
				index = 0;
			}
			super.open();
		}
	}

	protected void checkCloseBottonClick(ItemStack item, Player player) {
		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED  + "fermer")){
			player.closeInventory();
		}
	}
	
	public int getMaxItemsPerPage() {
		return maxItemsPerPage;
	}
}

