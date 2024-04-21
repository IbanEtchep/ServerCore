package fr.iban.bukkitcore.listeners;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.bukkitcore.utils.Scheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {

    private CoreBukkitPlugin plugin;

    public AsyncChatListener(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (plugin.getTextInputs().containsKey(player.getUniqueId())) {
            Scheduler.run(() -> plugin.getTextInputs().get(player.getUniqueId()).call(e.getMessage()));
            e.setCancelled(true);
            return;
        }
        if (!e.isCancelled()) {
            PluginMessageHelper.sendGlobalMessage(player, e.getMessage());
            e.setCancelled(true);
        }
    }
}