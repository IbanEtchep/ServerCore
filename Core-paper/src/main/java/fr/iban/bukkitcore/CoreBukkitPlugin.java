package fr.iban.bukkitcore;

import com.earth2me.essentials.Essentials;
import fr.iban.bukkitcore.commands.*;
import fr.iban.bukkitcore.commands.teleport.*;
import fr.iban.bukkitcore.listeners.*;
import fr.iban.bukkitcore.manager.MessagingManager;
import fr.iban.bukkitcore.manager.RessourcesWorldManager;
import fr.iban.bukkitcore.rewards.RewardsDAO;
import fr.iban.bukkitcore.teleport.TeleportManager;
import fr.iban.bukkitcore.teleport.TeleportToLocationListener;
import fr.iban.bukkitcore.teleport.TeleportToPlayerListener;
import fr.iban.bukkitcore.teleport.TpWaitingListener;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.bukkitcore.utils.TextCallback;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.data.redis.RedisCredentials;
import fr.iban.common.data.sql.DbAccess;
import fr.iban.common.data.sql.DbCredentials;
import fr.iban.common.messaging.AbstractMessenger;
import fr.iban.common.teleport.TeleportToLocation;
import fr.iban.common.teleport.TeleportToPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.api.RMap;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public final class CoreBukkitPlugin extends JavaPlugin {

	private static CoreBukkitPlugin instance;
	private TeleportManager teleportManager;
	private RedissonClient redisClient;
	private Map<UUID, TextCallback> textInputs;
	private List<UUID> tpWaiting;
	private Essentials essentials;
	private RessourcesWorldManager ressourcesWorldManager;
	private MessagingManager messagingManager;
	private RTopic teleportToPlayerRTopic;
	private RTopic teleportToLocationRTopic;
	private TeleportToPlayerListener teleportToPlayerListener;
	private TeleportToLocationListener teleportToLocationListener;
	private RTopic tpWaitingTopic;
	private TpWaitingListener tpWaitingListener;

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
		tpWaiting = new ArrayList<>();
        
        this.teleportManager = new TeleportManager(this);
        this.ressourcesWorldManager = new RessourcesWorldManager();
		this.messagingManager = new MessagingManager(this);

        registerListeners(
        		new HeadDatabaseListener(),
        		new InventoryListener(),
        		new AsyncChatListener(this),
        		new JoinQuitListeners(this),
        		new PlayerMoveListener(this),
        		new DeathListener(this),
        		new CommandsListener(this),
				new AsyncTabCompleteListener(this)
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
        
    	redisClient = RedisAccess.getInstance().getRedissonClient();
		teleportToPlayerRTopic = redisClient.getTopic("TeleportToPlayer");
		teleportToPlayerListener = new TeleportToPlayerListener();
		teleportToPlayerRTopic.addListener(TeleportToPlayer.class, teleportToPlayerListener);
		teleportToLocationRTopic = redisClient.getTopic("TeleportToLocation");
		teleportToLocationListener = new TeleportToLocationListener();
		teleportToLocationRTopic.addListener(TeleportToLocation.class, teleportToLocationListener);

		tpWaitingTopic = redisClient.getTopic("tpWaiting");
		tpWaitingListener = new TpWaitingListener(this);
		tpWaitingTopic.addListener(String.class, tpWaitingListener);
    }

    @Override
    public void onDisable() {
        RedisAccess.close();
        DbAccess.closePool();
		tpWaitingTopic.removeListener(tpWaitingListener);
		teleportToLocationRTopic.removeListener(teleportToLocationListener);
		teleportToPlayerRTopic.removeListener(teleportToPlayerListener);
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

	public static CoreBukkitPlugin getInstance() {
		return instance;
	}

	public String getServerName() {
		return getConfig().getString("servername");
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

	public RMap<String, String> getProxyPlayers(){
		return RedisAccess.getInstance().getRedissonClient().getMap("ProxyPlayers");
	}

	public RessourcesWorldManager getRessourcesWorldManager() {
		return ressourcesWorldManager;
	}

	public List<UUID> getTpWaiting() {
		return tpWaiting;
	}

	public CompletableFuture<UUID> getProxiedPlayerUUID(String name){
		return CompletableFuture.supplyAsync(() -> {
			String uuidString = getProxyPlayers().get(name);
			if(uuidString == null){
				return null;
			}
			return UUID.fromString(uuidString);
		});
	}

	public MessagingManager getMessagingManager() {
		return messagingManager;
	}
}