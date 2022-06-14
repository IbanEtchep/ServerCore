package fr.iban.bukkitcore.manager;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.event.CoreMessageEvent;
import fr.iban.common.messaging.AbstractMessenger;
import fr.iban.common.messaging.Message;
import fr.iban.common.messaging.RedisMessenger;
import fr.iban.common.messaging.SqlMessenger;

public class MessagingManager {

    private AbstractMessenger messenger;
    private final CoreBukkitPlugin plugin;

    public MessagingManager(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
        initMessenger();
    }

    private void initMessenger() {
        switch (plugin.getConfig().getString("messenger", "redis").toLowerCase()) {
            case "redis" -> messenger = new RedisMessenger();
            case "sql" -> messenger = new SqlMessenger() {
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

    public AbstractMessenger getMessenger() {
        return messenger;
    }

    public void sendMessage(String channel, String jsonMsg) {
        getMessenger().sendMessage(new Message(channel, plugin.getServerName(), jsonMsg));
    }
}
