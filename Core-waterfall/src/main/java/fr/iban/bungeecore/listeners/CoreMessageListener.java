package fr.iban.bungeecore.listeners;

import com.google.gson.Gson;
import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.event.CoreMessageEvent;
import fr.iban.common.messaging.Message;
import fr.iban.common.teleport.DeathLocation;
import fr.iban.common.teleport.EventAnnounce;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CoreMessageListener implements Listener {

    private final CoreBungeePlugin plugin;
    private final Gson gson = new Gson();

    public CoreMessageListener(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCoreMessage(CoreMessageEvent e) {
        Message message = e.getMessage();

        if (message.getServerFrom().equalsIgnoreCase("bungee")) {
            return;
        }

        switch (e.getMessage().getChannel()) {
            case "EventAnnounce" -> consumeAnnounceMessage(message);
            case "DeathLocation" -> consumeDeathLocationMessage(message);
        }
    }

    private void consumeDeathLocationMessage(Message message) {
        DeathLocation deathLocation = gson.fromJson(message.getMessage(), DeathLocation.class);
        plugin.getTeleportManager().getDeathLocations().put(deathLocation.getUuid(), deathLocation.getLocation());
    }

    private void consumeAnnounceMessage(Message message) {
        EventAnnounce announce = gson.fromJson(message.getMessage(), EventAnnounce.class);
        String key = announce.getName() + ":" + announce.getArena();

        if (announce.getLocation() == null) {
            if (plugin.getCurrentEvents().containsKey(key)) {
                plugin.getCurrentEvents().remove(key);
                return;
            }
        }

        if (!plugin.getCurrentEvents().containsKey(key)) {
            plugin.getCurrentEvents().put(key, announce.getLocation());
            plugin.getProxy().broadcast(new ComponentBuilder(getLine(30)).create());
            plugin.getProxy().broadcast(new ComponentBuilder("§5§l" + announce.getHostName() + " a lancé un event " + announce.getName()).create());
        }

        plugin.getProxy().broadcast(new ComponentBuilder(getCentered("§f §5§l" + announce.getName() + " ", 30)).create());
        plugin.getProxy().broadcast(TextComponent.fromLegacyText(announce.getDesc()));
        plugin.getProxy().broadcast(TextComponent.fromLegacyText("§fArene : " + announce.getArena()));
        plugin.getProxy().broadcast(new ComponentBuilder("§d§lCliquez pour rejoindre").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/joinevent " + key)).create());
        plugin.getProxy().broadcast(new ComponentBuilder(getLine(30)).create());
    }


    /*
    UTILS
     */

    private String getLine(int length) {
        StringBuilder sb = new StringBuilder("§8§m");
        for (int i = 0; i < length; i++) {
            sb.append("-");
        }
        return sb.toString();
    }

    private String getCentered(String string, int lineLength) {
        StringBuilder sb = new StringBuilder("§8§m");
        int line = (lineLength - string.length()) / 2 + 2;
        for (int i = 0; i < line; i++) {
            sb.append("-");
        }
        sb.append(string);
        sb.append("§8§m");
        for (int i = 0; i < line; i++) {
            sb.append("-");
        }
        return sb.toString();
    }

}
