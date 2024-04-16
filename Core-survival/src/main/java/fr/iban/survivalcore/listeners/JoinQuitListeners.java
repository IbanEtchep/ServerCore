package fr.iban.survivalcore.listeners;

import fr.iban.survivalcore.SurvivalCorePlugin;
import fr.iban.survivalcore.utils.Scheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinQuitListeners implements Listener {

    private final SurvivalCorePlugin plugin;

    public JoinQuitListeners(SurvivalCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setInvulnerable(true);

        Scheduler.runLater(() -> player.setInvulnerable(false), 200);
    }
}
