package fr.iban.bukkitcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.teleport.SLocation;

public class SLocationUtils {
	
	public static SLocation getSLocation(Location loc) {
		return new SLocation(CoreBukkitPlugin.getInstance().getServerName(), loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
	}

	public static Location getLocation(SLocation sloc) {
		if(sloc == null){
			return null;
		}
		return new Location(Bukkit.getWorld(sloc.getWorld()), sloc.getX(), sloc.getY(), sloc.getZ(), sloc.getYaw(), sloc.getPitch());
	}
}
