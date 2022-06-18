package fr.iban.survivalcore;

import com.earth2me.essentials.Essentials;
import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.survivalcore.commands.*;
import fr.iban.survivalcore.listeners.*;
import fr.iban.survivalcore.utils.HourlyReward;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class SurvivalCorePlugin extends JavaPlugin {

	/*
	 * Classe principale du plugin.
	 */
	private static SurvivalCorePlugin instance;

	private HourlyReward hourlyReward;
	public static Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    private Economy econ = null;

	@Override
	public void onEnable() {
		instance = this;
        PluginMessageHelper.registerChannels(this);
		
		saveDefaultConfig();
        setupEconomy();

		registerEvent(new ServerListPingListener());
		registerEvent(new PlayerFishListener());
		registerEvent(new EntityDeathListener());
		registerEvent(new CommandListener());
		registerEvent(new VillagerEvents(this));
		registerEvent(new RaidTriggerListener());
		registerEvent(new PortalListeners());
		registerEvent(new PlaceBreakListeners());
		registerEvent(new DamageListeners());
		registerEvent(new InteractListeners(this));
		registerEvent(new PrepareResultListener());

		getCommand("addhomes").setExecutor(new HomesManageCMD());
		getCommand("dolphin").setExecutor(new DolphinCMD());
		getCommand("feed").setExecutor(new FeedCMD());
		getCommand("givetools").setExecutor(new GiveSpecialToolsCMD());
		getCommand("repair").setExecutor(new RepairCMD());
		getCommand("annonce").setExecutor(new AnnonceCMD(this));
		getCommand("pvp").setExecutor(new PvPCMD(CoreBukkitPlugin.getInstance()));
		getCommand("survivalcore").setExecutor(new SurvivalCoreCMD());

		this.hourlyReward = new HourlyReward(this);
		this.hourlyReward.init();
	}

	public static SurvivalCorePlugin getInstance() {
		return instance;
	}

    public Economy getEconomy() {
        return econ;
    }
	
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

	private void registerEvent(Listener listener) {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(listener, this);
	}

}