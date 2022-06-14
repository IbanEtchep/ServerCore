package fr.iban.bungeecore.messaging;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.messaging.Message;
import fr.iban.common.messaging.MessagingManager;

import java.util.concurrent.TimeUnit;

public class BungeeMessagingManager extends MessagingManager {

    private CoreBungeePlugin plugin;

    public BungeeMessagingManager(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void startPollTask() {
        plugin.getProxy().getScheduler().schedule(plugin, () -> {
            for (Message message : sqlMessenger.getNewMessages()) {
                if(!message.getServerFrom().equalsIgnoreCase("bungee")) {
                    plugin.getProxy().getPluginManager().callEvent(new CoreMessageEvent(message));
                }
            }
        }, 1L, 1L, TimeUnit.SECONDS);
    }

    @Override
    public void startCleanUpTask() {
        plugin.getProxy().getScheduler().schedule(plugin, sqlMessenger::cleanOldMessages, 1L, 1L, TimeUnit.MINUTES);
    }
}
