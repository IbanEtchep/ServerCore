package fr.iban.survivalcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import fr.iban.survivalcore.utils.XPProvider;

public class PlayerExpChangeListener implements Listener {

	@EventHandler
	public void onExpChange(PlayerExpChangeEvent e) {
		Player player = e.getPlayer();
		XPProvider.addXP(player, e.getAmount(), true);
	}

}
