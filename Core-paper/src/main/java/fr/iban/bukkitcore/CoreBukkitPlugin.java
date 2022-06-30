package fr.iban.bukkitcore;

import com.earth2me.essentials.Essentials;
import fr.iban.bukkitcore.commands.*;
import fr.iban.bukkitcore.commands.teleport.*;
import fr.iban.bukkitcore.listeners.*;
import fr.iban.bukkitcore.manager.*;
import fr.iban.bukkitcore.rewards.RewardsDAO;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.bukkitcore.utils.TextCallback;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.data.redis.RedisCredentials;
import fr.iban.common.data.sql.DbAccess;
import fr.iban.common.data.sql.DbCredentials;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CoreBukkitPlugin extends JavaPlugin {

    public static final String SYNC_ACCOUNT_CHANNEL = "SyncAccount";
	public static final String REMOVE_PENDING_TP_CHANNEL = "RemovePendingTeleport";
	public static final String ADD_PENDING_TP_CHANNEL = "AddPendingTeleport";
	public static final String REMOVE_TP_REQUEST_CHANNEL = "RemoveTeleportRequest";
	public static final String ADD_TP_REQUEST_CHANNEL = "AddTeleportRequest";
	private static CoreBukkitPlugin instance;
	private TeleportManager teleportManager;
	private Map<UUID, TextCallback> textInputs;
	private Essentials essentials;
	private RessourcesWorldManager ressourcesWorldManager;
	private MessagingManager messagingManager;
	private AccountManager accountManager;
	private BukkitPlayerManager playerManager;

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

		if(getConfig().getString("messenger", "sql").equals("redis")) {
			try {
				RedisAccess.init(new RedisCredentials(getConfig().getString("redis.host"), getConfig().getString("redis.password"), getConfig().getInt("redis.port"), getConfig().getString("redis.clientName")));
			}catch (Exception e) {
				getLogger().severe("Erreur lors de l'initialisation de la connexion redis.");
				Bukkit.shutdown();
			}
		}

        RewardsDAO.createTables();
        
        textInputs = new HashMap<>();

		this.accountManager = new AccountManager(this);
        this.teleportManager = new TeleportManager(this);
        this.ressourcesWorldManager = new RessourcesWorldManager();
		this.messagingManager = new MessagingManager(this);
		messagingManager.init();
		this.playerManager = new BukkitPlayerManager();

        registerListeners(
        		new HeadDatabaseListener(),
        		new InventoryListener(),
        		new AsyncChatListener(this),
        		new JoinQuitListeners(this),
        		new PlayerMoveListener(this),
        		new DeathListener(this),
        		new CommandsListener(this),
				new AsyncTabCompleteListener(this),
				new CoreMessageListener(this)
        		);

		getCommand("core").setExecutor(new CoreCMD(this));
		getCommand("serveur").setExecutor(new ServeurCMD());
		getCommand("survie").setExecutor(new SurvieCMD());
		getCommand("ressources").setExecutor(new RessourcesCMD());
		getCommand("ressources").setTabCompleter(new RessourcesCMD());

		getCommand("abbc").setExecutor(new ActionBarCMD());
		getCommand("options").setExecutor(new OptionsCMD());
        getCommand("recompenses").setExecutor(new RecompensesCMD());
        getCommand("recompenses").setTabCompleter(new RecompensesCMD());
        getCommand("addtabcomplete").setExecutor(new AddTabCompleteCMD(this));
		getCommand("site").setExecutor(new SimpleCommands(this));
		getCommand("discord").setExecutor(new SimpleCommands(this));
		getCommand("vote").setExecutor(new SimpleCommands(this));
		getCommand("bungeebroadcast").setExecutor(new BungeeBroadcastCMD());

		getCommand("tp").setExecutor(new TpCMD(this));
		getCommand("tpa").setExecutor(new TpaCMD(this));
		getCommand("tpahere").setExecutor(new TpahereCMD(this));
		getCommand("tpyes").setExecutor(new TpyesCMD(this));
		getCommand("tpno").setExecutor(new TpnoCMD(this));
		getCommand("tphere").setExecutor(new TphereCMD(this));

        PluginMessageHelper.registerChannels(this);
    }

    @Override
    public void onDisable() {
		messagingManager.close();
		if(getConfig().getString("messenger", "sql").equals("redis")) {
			RedisAccess.close();
		}
        DbAccess.closePool();
    }
    
	private void registerListeners(Listener... listeners) {

		PluginManager pm = Bukkit.getPluginManager();

		for (Listener listener : listeners) {
			pm.registerEvents(listener, this);
		}

	}
	public static CoreBukkitPlugin getInstance() {
		return instance;
	}

	public String getServerName() {
		return getConfig().getString("servername");
	}

	public Map<UUID, TextCallback> getTextInputs() {
		return textInputs;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public TeleportManager getTeleportManager() {
		return teleportManager;
	}
	
	public Essentials getEssentials() {
		return essentials;
	}

	public RessourcesWorldManager getRessourcesWorldManager() {
		return ressourcesWorldManager;
	}

	public MessagingManager getMessagingManager() {
		return messagingManager;
	}

	public BukkitPlayerManager getPlayerManager() {
		return playerManager;
	}
}