package fr.iban.survivalcore;

import fr.iban.survivalcore.commands.*;
import fr.iban.survivalcore.listeners.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class Registrar {

	private SurvivalCorePlugin main;

	public Registrar(SurvivalCorePlugin main) {
		this.main = main;
		registerAll();
	}

	private void registerAll() {
		registerEvent(new ServerListPingListener());
		registerEvent(new InventoryListener());
		registerEvent(new PlayerFishListener());
		registerEvent(new EntityDeathListener());
		registerEvent(new CommandListener());
		registerEvent(new VillagerEvents());
		registerEvent(new RaidTriggerListener());
		registerEvent(new PortalListeners());
		registerEvent(new PlaceBreakListeners());
		registerEvent(new DamageListeners());
		registerEvent(new InteractListeners(main));

		main.getCommand("abbc").setExecutor(new ActionBarCMD());
		main.getCommand("addhomes").setExecutor(new HomesManageCMD());
		main.getCommand("dolphin").setExecutor(new DolphinCMD());
		main.getCommand("feed").setExecutor(new FeedCMD());
		main.getCommand("givetools").setExecutor(new GiveSpecialToolsCMD());
        main.getCommand("repair").setExecutor(new RepairCMD());


	}

	private void registerEvent(Listener listener) {
		PluginManager pm = main.getServer().getPluginManager();
		pm.registerEvents(listener, main);
	}

}