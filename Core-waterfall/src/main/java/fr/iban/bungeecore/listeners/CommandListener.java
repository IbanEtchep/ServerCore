package fr.iban.bungeecore.listeners;

import fr.iban.bungeecore.CoreBungeePlugin;
import io.github.waterfallmc.waterfall.event.ProxyDefineCommandsEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class CommandListener implements Listener {

    private final CoreBungeePlugin plugin;

    public CommandListener(CoreBungeePlugin coreBungeePlugin) {
        this.plugin = coreBungeePlugin;
    }

    @EventHandler
    public void onCommandDefine(ProxyDefineCommandsEvent e) {
        ProxiedPlayer player = (ProxiedPlayer) e.getReceiver();

        if (player.hasPermission("servercore.admin")) {
            return;
        }

        List<String> allowed = plugin.getConfiguration().getStringList("tabcomplete.global");

        if (player.hasPermission("servercore.moderation")) {
            allowed.addAll(plugin.getConfiguration().getStringList("tabcomplete.moderation"));
        }

        e.getCommands().entrySet().removeIf(cmd -> !allowed.contains(cmd.getKey()));
    }

}
