package fr.iban.survivalcore.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;

public class RaidTriggerListener implements Listener {

	private Map<UUID, Long> cooldown = new HashMap<>();

	@EventHandler
	public void onRaidTrigger(RaidTriggerEvent e) {
		final Player player = e.getPlayer();
		if (player.hasPermission("antiraidfarm.bypass")) {
			return;
		}
		if (isCooldown(player)) {
			e.setCancelled(true);
		} else {
			cooldown.put(player.getUniqueId(), System.currentTimeMillis());
		}
	}

	private boolean isCooldown(Player player) {
		if(cooldown.containsKey(player.getUniqueId())) {
			if(System.currentTimeMillis() - cooldown.get(player.getUniqueId()) < 10000) {
				return true;
			}else {
				cooldown.remove(player.getUniqueId());
			}
		}
		return false;
	}

}
