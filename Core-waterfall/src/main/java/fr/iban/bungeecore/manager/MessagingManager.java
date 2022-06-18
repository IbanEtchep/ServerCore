package fr.iban.bungeecore.manager;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.event.CoreMessageEvent;
import fr.iban.common.messaging.AbstractMessagingManager;
import fr.iban.common.messaging.RedisMessenger;
import fr.iban.common.messaging.SqlMessenger;

import java.util.concurrent.TimeUnit;

public class MessagingManager extends AbstractMessagingManager {

    private final CoreBungeePlugin plugin;
    public MessagingManager(CoreBungeePlugin plugin) {
        this.plugin = plugin;
        initMessenger();
    }

    @Override
    public void initMessenger() {
        switch (plugin.getConfiguration().getString("messenger", "sql").toLowerCase()) {
//            case "redis" -> {
//                messenger = new RedisMessenger();
//            }
            default -> messenger = new SqlMessenger() {
                @Override
                public void startPollTask() {
                    plugin.getProxy().getScheduler().schedule(plugin, this::readNewMessages, 50L, 50L, TimeUnit.MILLISECONDS);
                }

                @Override
                public void startCleanUpTask() {
                    plugin.getProxy().getScheduler().schedule(plugin, this::readNewMessages, 1L, 1L, TimeUnit.MINUTES);
                }
            };
        }
        messenger.setOnMessageListener(message -> {
            if(!message.getServerFrom().equals(getServerName())) {
                plugin.getProxy().getPluginManager().callEvent(new CoreMessageEvent(message));
            }
        });
        plugin.getLogger().info("Messenger type : " + plugin.getConfiguration().getString("messenger", "redis"));
    }

    @Override
    protected String getServerName() {
        return "bungee";
    }

    public void sendMessageAsync(String channel, String jsonMsg) {
        plugin.getProxy().getScheduler().runAsync(plugin, () -> sendMessage(channel, jsonMsg));
    }

    public <T> void sendMessageAsync(String channel, T message) {
        plugin.getProxy().getScheduler().runAsync(plugin, () -> sendMessage(channel, message));
    }
}
