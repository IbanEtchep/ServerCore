package fr.iban.velocitycore.util;

import com.velocitypowered.api.proxy.Player;
import fr.iban.velocitycore.CoreVelocityPlugin;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.event.EventBus;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;
import me.neznamy.tab.api.tablist.TabListFormatManager;

import java.util.Objects;

public class TabHook {

    private final CoreVelocityPlugin plugin;

    public TabHook(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        EventBus eventBus = TabAPI.getInstance().getEventBus();

        if(eventBus == null) {
            plugin.getLogger().info("TabAPI not found, disabling TabHook.");
            return;
        }

         eventBus.register(PlayerLoadEvent.class, event -> {
            TabListFormatManager tablistFormatManager = TabAPI.getInstance().getTabListFormatManager();
            TabPlayer tabPlayer = event.getPlayer();
            Player player = plugin.getServer().getPlayer(tabPlayer.getUniqueId()).orElse(null);

            if (player == null) {
                return;
            }

            if (player.hasPermission("premium") && !player.hasPermission("group.support")) {
                if (tablistFormatManager.getCustomSuffix(tabPlayer) == null
                        || !Objects.equals(tablistFormatManager.getCustomSuffix(tabPlayer), "#feca57 ✮")) {
                    tablistFormatManager.setSuffix(tabPlayer, "#feca57 ✮");
                }
            } else if (tablistFormatManager.getCustomSuffix(tabPlayer) != null
                    && Objects.equals(tablistFormatManager.getCustomSuffix(tabPlayer), "#feca57 ✮")) {
                tablistFormatManager.setSuffix(tabPlayer, null);
            }
        });
    }
}
