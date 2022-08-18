package fr.iban.bungeecore.utils;

import fr.iban.bungeecore.CoreBungeePlugin;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.TablistFormatManager;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;
import me.neznamy.tab.api.team.TeamManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TabHook {

    private final CoreBungeePlugin plugin;
    private PlayerLoadEvent playerLoadEvent;

    public TabHook(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        TabAPI.getInstance().getEventBus().register(PlayerLoadEvent.class, event -> {
            this.playerLoadEvent = event;
            TablistFormatManager tablistFormatManager = TabAPI.getInstance().getTablistFormatManager();
            TabPlayer tabPlayer = event.getPlayer();
            ProxiedPlayer player = plugin.getProxy().getPlayer(tabPlayer.getUniqueId());

            if (player.hasPermission("premium") && !player.hasPermission("group.support")) {
                if (!tablistFormatManager.getCustomSuffix(tabPlayer).equals("#feca57 ✮")) {
                    tablistFormatManager.setSuffix(tabPlayer, "#feca57 ✮");
                }
            } else {
                if (tablistFormatManager.getCustomSuffix(tabPlayer).equals("#feca57 ✮")) {
                    tablistFormatManager.resetSuffix(tabPlayer);
                }
            }
            tablistFormatManager.setSuffix(tabPlayer, "#feca57 ✮");
        });
    }

    public void disable() {
        TabAPI.getInstance().getEventBus().unregister(playerLoadEvent);
    }
}
