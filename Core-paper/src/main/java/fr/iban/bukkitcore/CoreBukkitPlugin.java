package fr.iban.bukkitcore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.api.RedissonClient;

import com.earth2me.essentials.Essentials;

import fr.iban.bukkitcore.commands.AddTabCompleteCMD;
import fr.iban.bukkitcore.commands.AnnonceCMD;
import fr.iban.bukkitcore.commands.OptionsCMD;
import fr.iban.bukkitcore.commands.RecompensesCMD;
import fr.iban.bukkitcore.commands.RessourceCMD;
import fr.iban.bukkitcore.commands.ServeurCMD;
import fr.iban.bukkitcore.commands.SurvieCMD;
import fr.iban.bukkitcore.listeners.AsyncChatListener;
import fr.iban.bukkitcore.listeners.CommandsListener;
import fr.iban.bukkitcore.listeners.DeathListener;
import fr.iban.bukkitcore.listeners.HeadDatabaseListener;
import fr.iban.bukkitcore.listeners.InventoryListener;
import fr.iban.bukkitcore.listeners.JoinQuitListeners;
import fr.iban.bukkitcore.listeners.PlayerMoveListener;
import fr.iban.bukkitcore.rewards.RewardsDAO;
import fr.iban.bukkitcore.teleport.TeleportManager;
import fr.iban.bukkitcore.teleport.TeleportToLocationListener;
import fr.iban.bukkitcore.teleport.TeleportToPlayerListener;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.bukkitcore.utils.TextCallback;
import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.data.redis.RedisCredentials;
import fr.iban.common.data.sql.DbAccess;
import fr.iban.common.data.sql.DbCredentials;
import net.milkbowl.vault.economy.Economy;

public final class CoreBukkitPlugin extends JavaPlugin {

	private static CoreBukkitPlugin instance;
	private TeleportManager teleportManager;
	private RedissonClient redisClient;
	private String serverName = "null";
	private Map<UUID, TextCallback> textInputs;
	private Essentials essentials;
	
	private Economy econ = null;

    @Override
    public void onEnable() {
    	instance = this;
    	saveDefaultConfig();
    	
		if(getServer().getPluginManager().isPluginEnabled("Essentials")) {
			essentials = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
		}
    	
    	try {
    		DbAccess.initPool(new DbCredentials(getConfig().getString("database.host"), getConfig().getString("database.user"), getConfig().getString("database.password"), getConfig().getString("database.dbname"), getConfig().getInt("database.port")));
    	}catch (Exception e) {
    		getLogger().severe("Erreur lors de l'initialisation de la connexion sql.");
			Bukkit.shutdown();
		}
    	
    	try {
            RedisAccess.init(new RedisCredentials(getConfig().getString("redis.host"), getConfig().getString("redis.password"), getConfig().getInt("redis.port"), getConfig().getString("redis.clientName")));
    	}catch (Exception e) {
    		getLogger().severe("Erreur lors de l'initialisation de la connexion redis.");
			Bukkit.shutdown();
		}
        
        
        RewardsDAO.createTables();
        
        textInputs = new HashMap<>();
        
        this.teleportManager = new TeleportManager(this);
        
        registerListeners(
        		new HeadDatabaseListener(),
        		new InventoryListener(),
        		new AsyncChatListener(this),
        		new JoinQuitListeners(this),
        		new PlayerMoveListener(),
        		new DeathListener(this),
        		new CommandsListener(this)
        		);
        
        getCommand("serveur").setExecutor(new ServeurCMD());

        getCommand("options").setExecutor(new OptionsCMD());
        getCommand("annonce").setExecutor(new AnnonceCMD(this));
        getCommand("survie").setExecutor(new SurvieCMD());
        getCommand("ressource").setExecutor(new RessourceCMD());
        getCommand("recompenses").setExecutor(new RecompensesCMD());
        getCommand("recompenses").setTabCompleter(new RecompensesCMD());
        getCommand("addtabcomplete").setExecutor(new AddTabCompleteCMD(this));

        setupEconomy();
        
        PluginMessageHelper.registerChannels(this);
        
    	redisClient = RedisAccess.getInstance().getRedissonClient();
        redisClient.getTopic("TeleportToPlayer").addListener(new TeleportToPlayerListener());
        redisClient.getTopic("TeleportToLocation").addListener(new TeleportToLocationListener());
        
        if(!Bukkit.getOnlinePlayers().isEmpty()) {
        	for(Player player : Bukkit.getOnlinePlayers()) {
        		PluginMessageHelper.askServerName(player);
        		break;
        	}
        }
    }

    @Override
    public void onDisable() {
        RedisAccess.close();
        DbAccess.closePool();
    }
    
	private void registerListeners(Listener... listeners) {

		PluginManager pm = Bukkit.getPluginManager();

		for (Listener listener : listeners) {
			pm.registerEvents(listener, this);
		}

	}
	
	public RedissonClient getRedisClient() {
		return redisClient;
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

	public Economy getEcon() {
		return econ;
	}
	
	public static CoreBukkitPlugin getInstance() {
		return instance;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Map<UUID, TextCallback> getTextInputs() {
		return textInputs;
	}
	
	public TeleportManager getTeleportManager() {
		return teleportManager;
	}
	
	public Essentials getEssentials() {
		return essentials;
	}

}