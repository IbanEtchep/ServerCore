package fr.iban.bungeecore.manager;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.event.CoreMessageEvent;
import fr.iban.common.messaging.AbstractMessenger;
import fr.iban.common.messaging.Message;
import fr.iban.common.messaging.RedisMessenger;
import fr.iban.common.messaging.SqlMessenger;

import java.util.concurrent.TimeUnit;

public class MessagingManager {

    private AbstractMessenger messenger;
    private final CoreBungeePlugin plugin;

    public MessagingManager(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }

    private void initMessenger() {
        switch (plugin.getConfiguration().getString("messenger", "redis").toLowerCase()) {
            case "redis" -> {
                messenger = new RedisMessenger();
                messenger.setOnMessageListener(message -> {
                    if(!message.getServerFrom().equals("bungee")) {
                        plugin.getProxy().getPluginManager().callEvent(new CoreMessageEvent(message));
                    }
                });
            }
            case "sql" -> {
                messenger = new SqlMessenger() {
                    @Override
                    public void startPollTask() {
                        plugin.getProxy().getScheduler().schedule(plugin, this::readNewMessages, 50L, 50L, TimeUnit.MILLISECONDS);
                    }

                    @Override
                    public void startCleanUpTask() {
                        plugin.getProxy().getScheduler().schedule(plugin, this::readNewMessages, 1L, 1L, TimeUnit.MINUTES);
                    }
                };
                messenger.setOnMessageListener(message -> {
                    if(!message.getServerFrom().equals("bungee")) {
                        plugin.getProxy().getPluginManager().callEvent(new CoreMessageEvent(message));
                    }
                });
            }
        }
    }

    public AbstractMessenger getMessenger() {
        return messenger;
    }

    public void sendMessage(String channel, String jsonMsg) {
        getMessenger().sendMessage(new Message(channel, "bungee", jsonMsg));
    }
}
