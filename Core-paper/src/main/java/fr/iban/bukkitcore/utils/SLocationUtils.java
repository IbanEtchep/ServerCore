package fr.iban.bukkitcore.utils;

import org.bukkit.Location;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.teleport.SLocation;

public class SLocationUtils {
	
	public static SLocation getSLocation(Location loc) {
		return new SLocation(CoreBukkitPlugin.getInstance().getServerName(), loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
	}

}
