package fr.iban.bungeecore.manager;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.event.CoreMessageEvent;
import fr.iban.common.data.redis.RedisCredentials;
import fr.iban.common.messaging.AbstractMessagingManager;
import fr.iban.common.messaging.AbstractMessenger;
import fr.iban.common.messaging.RedisMessenger;
import fr.iban.common.messaging.SqlMessenger;
import net.md_5.bungee.config.Configuration;

public class MessagingManager extends AbstractMessagingManager {

    private final CoreBungeePlugin plugin;
    public MessagingManager(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        Configuration config = plugin.getConfiguration();
        if (config.getString("messenger", "sql").equals("redis")) {
            RedisCredentials credentials = new RedisCredentials(
                    config.getString("redis.host"),
                    config.getString("redis.password"),
                    config.getInt("redis.port"),
                    config.getString("redis.clientName")
            );
            messenger = new RedisMessenger(credentials);
        } else {
            messenger = new SqlMessenger();
        }

        messenger.setOnMessageListener(message -> {
            if(!message.getServerFrom().equals(getServerName())) {
                plugin.getProxy().getPluginManager().callEvent(new CoreMessageEvent(message));
            }
        });

        messenger.init();
    }

    @Override
    public void close() {
        messenger.close();
    }

    @Override
    protected String getServerName() {
        return "bungee";
    }
}
