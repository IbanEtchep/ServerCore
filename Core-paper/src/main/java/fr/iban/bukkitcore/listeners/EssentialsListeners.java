package fr.iban.bukkitcore.listeners;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import net.ess3.api.IUser;
import net.ess3.api.events.VanishStatusChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EssentialsListeners implements Listener {

    private final CoreBukkitPlugin plugin;

    public EssentialsListeners(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVanish(VanishStatusChangeEvent event) {
        plugin.getPlayerManager().setVanishedAndSync(event.getAffected().getBase().getUniqueId(), event.getValue());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        IUser user = plugin.getEssentials().getUser(player);
        if(plugin.getPlayerManager().isVanished(player.getUniqueId()) && !user.isVanished()) {
            user.setVanished(true);
        }
    }

}
