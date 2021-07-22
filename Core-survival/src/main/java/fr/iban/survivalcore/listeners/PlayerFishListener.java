package fr.iban.survivalcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFishListener implements Listener {
	
	@EventHandler
	public void onFish(PlayerFishEvent e) {
//		if(e.getState() == State.CAUGHT_FISH) {
//			Player player = e.getPlayer();
//			PlayerData data = PlayerManager.get(player.getUniqueId());
//		}
	}

}
