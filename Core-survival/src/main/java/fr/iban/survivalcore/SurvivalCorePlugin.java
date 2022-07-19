package fr.iban.survivalcore;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.survivalcore.commands.*;
import fr.iban.survivalcore.listeners.*;
import fr.iban.survivalcore.utils.HourlyReward;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class SurvivalCorePlugin extends JavaPlugin implements Listener {

    private static SurvivalCorePlugin instance;
    private Economy econ;


    @Override
    public void onEnable() {
        instance = this;
        PluginMessageHelper.registerChannels(this);

        saveDefaultConfig();
        setupEconomy();

        registerEvents(
                new ServerListPingListener(),
                new PlayerFishListener(),
                new EntityDeathListener(),
                new CommandListener(),
                new VillagerEvents(this),
                new RaidTriggerListener(),
                new PortalListeners(),
                new PlaceBreakListeners(),
                new DamageListeners(),
                new InteractListeners(this),
                new PrepareResultListener(),
                new ServiceListeners(this)
        );

        getCommand("addhomes").setExecutor(new HomesManageCMD());
        getCommand("dolphin").setExecutor(new DolphinCMD());
        getCommand("feed").setExecutor(new FeedCMD());
        getCommand("givetools").setExecutor(new GiveSpecialToolsCMD());
        getCommand("repair").setExecutor(new RepairCMD());
        getCommand("annonce").setExecutor(new AnnonceCMD(this));
        getCommand("pvp").setExecutor(new PvPCMD(CoreBukkitPlugin.getInstance()));
        getCommand("survivalcore").setExecutor(new SurvivalCoreCMD());

        HourlyReward hourlyReward = new HourlyReward(this);
        hourlyReward.init();
    }

    public static SurvivalCorePlugin getInstance() {
        return instance;
    }

    public void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().info("No economy provider found.");
            return;
        }
        getLogger().info("Using " + rsp.getProvider().getName() + " economy.");
        econ = rsp.getProvider();
    }

    public Economy getEconomy() {
        return econ;
    }
}