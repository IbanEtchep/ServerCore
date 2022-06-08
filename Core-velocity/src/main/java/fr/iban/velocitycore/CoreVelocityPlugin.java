package fr.iban.velocitycore;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.iban.bungeecore.listeners.CommandListener;
import fr.iban.bungeecore.listeners.PluginMessageListener;
import fr.iban.bungeecore.listeners.ProxyJoinQuitListener;
import fr.iban.bungeecore.listeners.ProxyPingListener;
import org.slf4j.Logger;

@Plugin(id = "CoreVelocity", name = "CoreVelocity", version = "1.0-SNAPSHOT",
        description = "Core velocity plugin", authors = {"Iban"})
public class CoreVelocityPlugin {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public CoreVelocityPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("Hello there, it's a test plugin I made!");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Do some operation demanding access to the Velocity API here.
        // For instance, we could register an event:
        server.getEventManager().register(this, new ProxyJoinQuitListener(this));
        server.getEventManager().register(this, new ProxyPingListener(this));
        server.getEventManager().register(this, new PluginMessageListener(this));
        server.getEventManager().register(this, new CommandListener(this));
    }

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }
}
