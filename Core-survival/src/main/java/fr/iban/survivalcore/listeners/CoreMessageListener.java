package fr.iban.survivalcore.listeners;

import fr.iban.bukkitcore.event.CoreMessageEvent;
import fr.iban.survivalcore.SurvivalCorePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class CoreMessageListener implements Listener {

    private SurvivalCorePlugin plugin;

    public CoreMessageListener(SurvivalCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCoreMessage(CoreMessageEvent e) {
        String channel = e.getMessage().getChannel();
        String message = e.getMessage().getMessage();

        if (channel.equals("SYNC_ANNOUNCE_COOLDOWN_CHANNEL")) {
            UUID playerUUID = UUID.fromString(message);
            plugin.getAnnounceManager().getCooldowns().put(playerUUID, System.currentTimeMillis());
        }
    }
}
