package fr.iban.survivalcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FishingListener implements Listener {

    private final Map<UUID, PlayerFishEvent.State> lastStates = new HashMap<>();

    @EventHandler
    public void onFish(PlayerFishEvent e) {
        Player player = e.getPlayer();

        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH
                && lastStates.getOrDefault(player.getUniqueId(), PlayerFishEvent.State.FISHING) == PlayerFishEvent.State.CAUGHT_FISH) {
            e.setCancelled(true);
        }

        lastStates.put(player.getUniqueId(), e.getState());
    }

}
