package fr.iban.bukkitcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.rewards.RewardsDAO;
import fr.iban.bukkitcore.utils.PluginMessageHelper;

public class JoinQuitListeners implements Listener {

	private final CoreBukkitPlugin plugin;


	public JoinQuitListeners(CoreBukkitPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		e.setJoinMessage(null);
		if(plugin.getServerName().equals("null")) {
			Bukkit.getScheduler().runTaskLater(plugin, () -> PluginMessageHelper.askServerName(player), 10L);
		}
		RewardsDAO.getRewardsAsync(player.getUniqueId()).thenAccept(list -> {
			if(!list.isEmpty()) {
				player.sendMessage("§aVous avez une ou plusieurs récompenses en attente ! (recompenses)");
			}
		});
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		e.setQuitMessage(null);
		plugin.getTextInputs().remove(player.getUniqueId());
	}

}
