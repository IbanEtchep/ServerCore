package fr.iban.bukkitcore.manager;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.event.CoreMessageEvent;
import fr.iban.common.messaging.*;
import org.bukkit.Bukkit;

public class MessagingManager extends AbstractMessagingManager {

    private final CoreBukkitPlugin plugin;

    public MessagingManager(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        messenger = new SqlMessenger();
        messenger.setOnMessageListener(message -> {
            if (!message.getServerFrom().equals(plugin.getServerName())) {
                plugin.getServer().getPluginManager().callEvent(new CoreMessageEvent(message));
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
