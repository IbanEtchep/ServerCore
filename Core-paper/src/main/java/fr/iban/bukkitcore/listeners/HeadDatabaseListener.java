package fr.iban.bukkitcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.iban.bukkitcore.utils.Head;
import me.arcaniax.hdb.api.DatabaseLoadEvent;

public class HeadDatabaseListener implements Listener {
	
    @EventHandler
    public void onDatabaseLoad(DatabaseLoadEvent e) {
    	Head.loadAPI();
    }

}
