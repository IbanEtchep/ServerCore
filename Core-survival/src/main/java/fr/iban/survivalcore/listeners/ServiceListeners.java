package fr.iban.survivalcore.listeners;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.survivalcore.SurvivalCorePlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.event.server.ServiceUnregisterEvent;

public class ServiceListeners implements Listener {

    private SurvivalCorePlugin plugin;

    public ServiceListeners(SurvivalCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServiceRegister(ServiceRegisterEvent event) {
        if (!event.getProvider().getService().getName().equals("net.milkbowl.vault.economy.Economy")) {
            return;
        }
        plugin.setupEconomy();
    }

    @EventHandler
    public void onServiceUnregister(ServiceUnregisterEvent event) {
        if (!event.getProvider().getService().getName().equals("net.milkbowl.vault.economy.Economy")) {
            return;
        }
        plugin.setupEconomy();
    }

}
