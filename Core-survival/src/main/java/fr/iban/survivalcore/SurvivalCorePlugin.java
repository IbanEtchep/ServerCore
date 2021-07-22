package fr.iban.survivalcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import fr.iban.survivalcore.utils.PluginMessageHelper;
import net.milkbowl.vault.economy.Economy;

public final class SurvivalCorePlugin extends JavaPlugin {

	/*
	 * Classe principale du plugin.
	 */
	private static SurvivalCorePlugin instance;
		
	public static Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    private static Economy econ = null;


	private Registrar registrar;

	@Override
	public void onEnable() {
		instance = this;
        PluginMessageHelper.registerChannels(this);

		
		saveDefaultConfig();
		this.registrar = new Registrar(this);
		
        //Vault setup
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

	}
	
	@Override
	public void onDisable() {
	}

	public static SurvivalCorePlugin getInstance() {
		return instance;
	}

	public Registrar getRegistrar() {
		return registrar;
	}
	
    public static Economy getEconomy() {
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

}