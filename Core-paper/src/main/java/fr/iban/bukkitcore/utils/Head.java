package fr.iban.bukkitcore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.arcaniax.hdb.api.HeadDatabaseAPI;

public enum Head {
	
	CHEST("227"),
	CHEST_DIRT("775"),
	HAL("41548"),
	NO_ENTRY("19778"),
	GRASS("24064"),
	OAK_RIGHT("7826"),
	OAK_LEFT("7827"),
	OAK_PLUS("2336"),
	OAK_MINUS("1786"),
	GLOBE("2981"),
	FIREBALL("6385"),
	TNTCREEP("16044"),
	OAK_L("175"),
	OAK_INFO("20774"),
	BAG("30016"),
	DEATH("6861"),
	PLUS("10209"),
	TCHAT("28599"),
	MOINS("9351"),
	AROBASE("42824"),
	ENDER_PEARL("116"),
	PVP("27529"),
	ARROW("513"),
	HOUSE("4449"),
	NORMAL_CHEST("2970"),
	FARMER_STEVE("30463")
	;
	
	private static HeadDatabaseAPI api;
	private String id;
	
	private Head(String id) {
		this.id = id;
	}
		
	private boolean isAPILoaded() {
		return api != null;
	}
	
	public ItemStack get() {
		if(isAPILoaded()) {
			return api.getItemHead(id);
		}else {
			return new ItemStack(Material.PLAYER_HEAD);
		}
	}
	
	public static void loadAPI() {
		api = new HeadDatabaseAPI();
	}

}
