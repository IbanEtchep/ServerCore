package fr.iban.velocitycore.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.List;
import java.util.Random;

public class ProxyPingListener {

    private final CoreVelocityPlugin plugin;
    private final Random random = new Random();

    public ProxyPingListener(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onPing(ProxyPingEvent e) {
        ServerPing serverPing = e.getPing().asBuilder().build();
        List<String> motd = plugin.getConfig().getStringList("motd");
        serverPing = serverPing.asBuilder()
                .maximumPlayers(100)
                .description(LegacyComponentSerializer.legacySection().deserialize(
                        motd.get(random.nextInt(motd.size()))
                ))
                .build();

        e.setPing(serverPing);
    }
}
