package fr.iban.bungeecore.manager;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.event.CoreMessageEvent;
import fr.iban.common.messaging.AbstractMessagingManager;
import fr.iban.common.messaging.SqlMessenger;

public class MessagingManager extends AbstractMessagingManager {

    private final CoreBungeePlugin plugin;
    public MessagingManager(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        messenger = new SqlMessenger();
        messenger.setOnMessageListener(message -> {
            if(!message.getServerFrom().equals(getServerName())) {
                plugin.getProxy().getPluginManager().callEvent(new CoreMessageEvent(message));
            }
        });
        messenger.init();
        plugin.getLogger().info("Messenger type : " + plugin.getConfiguration().getString("messenger", "redis"));
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
