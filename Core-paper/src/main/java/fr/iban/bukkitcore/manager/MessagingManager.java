package fr.iban.bukkitcore.manager;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.event.CoreMessageEvent;
import fr.iban.common.messaging.*;
import org.bukkit.Bukkit;

public class MessagingManager extends AbstractMessagingManager {

    private final CoreBukkitPlugin plugin;

    public MessagingManager(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
        initMessenger();
    }

    @Override
    public void initMessenger() {
        switch (plugin.getConfig().getString("messenger", "redis").toLowerCase()) {
            //case "redis" -> messenger = new RedisMessenger();
            default -> messenger = new SqlMessenger() {
                @Override
                public void startPollTask() {
                    plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::readNewMessages, 1L, 1L);
                }

                @Override
                public void startCleanUpTask() {
                    plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::cleanOldMessages, 1200L, 1200L);
                }
            };
        }
        messenger.setOnMessageListener(message -> {
            if (!message.getServerFrom().equals(plugin.getServerName())) {
                plugin.getServer().getPluginManager().callEvent(new CoreMessageEvent(message));
            }
        });
    }

    @Override
    protected String getServerName() {
        return plugin.getServerName();
    }

    public void sendMessageAsync(String channel, String jsonMsg) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> sendMessage(channel, jsonMsg));
    }

    public <T> void sendMessageAsync(String channel, T message) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> sendMessage(channel, message));
    }
}
