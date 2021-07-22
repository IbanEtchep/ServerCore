package fr.iban.survivalcore;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import fr.iban.survivalcore.commands.ActionBarCMD;
import fr.iban.survivalcore.commands.BoostCMD;
import fr.iban.survivalcore.commands.DolphinCMD;
import fr.iban.survivalcore.commands.FeedCMD;
import fr.iban.survivalcore.commands.GiveSpecialToolsCMD;
import fr.iban.survivalcore.commands.HomesManageCMD;
import fr.iban.survivalcore.commands.LevelsCMD;
import fr.iban.survivalcore.commands.RepairCMD;
import fr.iban.survivalcore.commands.SimpleCommands;
import fr.iban.survivalcore.commands.TopLevelsCMD;
import fr.iban.survivalcore.listeners.CommandListener;
import fr.iban.survivalcore.listeners.DamageListeners;
import fr.iban.survivalcore.listeners.EntityDeathListener;
import fr.iban.survivalcore.listeners.InteractListeners;
import fr.iban.survivalcore.listeners.InventoryListener;
import fr.iban.survivalcore.listeners.PlaceBreakListeners;
import fr.iban.survivalcore.listeners.PlayerExpChangeListener;
import fr.iban.survivalcore.listeners.PlayerFishListener;
import fr.iban.survivalcore.listeners.PortalListeners;
import fr.iban.survivalcore.listeners.RaidTriggerListener;
import fr.iban.survivalcore.listeners.ServerListPingListener;
import fr.iban.survivalcore.listeners.VillagerEvents;
import fr.iban.survivalcore.utils.papi.SpartaCubePlaceHolder;

public class Registrar {

	private SurvivalCorePlugin main;

	public Registrar(SurvivalCorePlugin main) {
		this.main = main;
		registerAll();
	}

	private void registerAll() {
		registerEvent(new ServerListPingListener());
		registerEvent(new InventoryListener());
		registerEvent(new DamageListeners());
		registerEvent(new PlayerFishListener());
		registerEvent(new EntityDeathListener());
		registerEvent(new PlayerExpChangeListener());
		registerEvent(new CommandListener());
		registerEvent(new VillagerEvents());
		registerEvent(new RaidTriggerListener());
		registerEvent(new PortalListeners());
		registerEvent(new PlaceBreakListeners());
		registerEvent(new InteractListeners(main));

		
		new SpartaCubePlaceHolder(main).register();
		
		main.getCommand("site").setExecutor(new SimpleCommands());
		main.getCommand("discord").setExecutor(new SimpleCommands());
		main.getCommand("vote").setExecutor(new SimpleCommands());
		main.getCommand("map").setExecutor(new SimpleCommands());
		main.getCommand("boutique").setExecutor(new SimpleCommands());
		main.getCommand("tutoriel").setExecutor(new SimpleCommands());
		main.getCommand("stoptuto").setExecutor(new SimpleCommands());
		main.getCommand("abbc").setExecutor(new ActionBarCMD());
		main.getCommand("level").setExecutor(new LevelsCMD());
		main.getCommand("addhomes").setExecutor(new HomesManageCMD());
		main.getCommand("grades").setExecutor(new SimpleCommands());
		main.getCommand("pvp").setExecutor(new SimpleCommands());
		main.getCommand("boost").setExecutor(new BoostCMD());
		main.getCommand("dolphin").setExecutor(new DolphinCMD());
		main.getCommand("feed").setExecutor(new FeedCMD());
		main.getCommand("classement").setExecutor(new TopLevelsCMD());
		main.getCommand("givetools").setExecutor(new GiveSpecialToolsCMD());
        main.getCommand("repair").setExecutor(new RepairCMD());


	}

	private void registerEvent(Listener listener) {
		PluginManager pm = main.getServer().getPluginManager();
		pm.registerEvents(listener, main);
	}

}