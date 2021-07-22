package fr.iban.bukkitcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.utils.PluginMessageHelper;

public class AsyncChatListener implements Listener {

	private CoreBukkitPlugin plugin;

	public AsyncChatListener(CoreBukkitPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		if(plugin.getTextInputs().containsKey(player.getUniqueId())) {
			Bukkit.getScheduler().runTask(plugin, () -> plugin.getTextInputs().get(player.getUniqueId()).call(e.getMessage()));
			e.setCancelled(true);
			return;
		}
		if(!e.isCancelled()) {
			PluginMessageHelper.sendGlobalMessage(player, e.getMessage());
			e.setCancelled(true);
		}
	}
}