package fr.iban.bukkitcore.manager;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.event.CoreMessageEvent;
import fr.iban.common.messaging.Message;
import fr.iban.common.messaging.MessagingManager;
import org.bukkit.Bukkit;

public class BukkitMessagingManager extends MessagingManager {

    private final CoreBukkitPlugin plugin;

    public BukkitMessagingManager(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void startPollTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Message message : sqlMessenger.getNewMessages()) {
                if(!plugin.getServerName().equalsIgnoreCase(message.getServerFrom())) {
                    Bukkit.getPluginManager().callEvent(new CoreMessageEvent(message));
                }
            }
        }, 20L, 20L);
    }

    @Override
    public void startCleanUpTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, sqlMessenger::cleanOldMessages, 1200L, 1200L);
    }
}
