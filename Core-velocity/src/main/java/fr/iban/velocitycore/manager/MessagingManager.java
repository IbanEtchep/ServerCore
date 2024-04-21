package fr.iban.velocitycore.manager;

import dev.dejvokep.boostedyaml.YamlDocument;
import fr.iban.common.data.redis.RedisCredentials;
import fr.iban.common.messaging.AbstractMessagingManager;
import fr.iban.common.messaging.RedisMessenger;
import fr.iban.common.messaging.SqlMessenger;
import fr.iban.velocitycore.CoreVelocityPlugin;
import fr.iban.velocitycore.event.CoreMessageEvent;

public class MessagingManager extends AbstractMessagingManager {

    private final CoreVelocityPlugin plugin;
    public MessagingManager(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        YamlDocument config = plugin.getConfig();
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
                plugin.getServer().getEventManager().fire(new CoreMessageEvent(message));
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
        return plugin.getServerName();
    }
}
