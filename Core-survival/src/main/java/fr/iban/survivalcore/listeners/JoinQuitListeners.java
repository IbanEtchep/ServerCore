package fr.iban.survivalcore.listeners;

import fr.iban.survivalcore.SurvivalCorePlugin;
import org.bukkit.Bukkit;
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

        Bukkit.getScheduler().runTaskLater(plugin, () -> player.setInvulnerable(false), 200);
    }
}
