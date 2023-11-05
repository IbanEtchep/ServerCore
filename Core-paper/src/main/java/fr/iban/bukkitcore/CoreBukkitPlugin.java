package fr.iban.bukkitcore;

import com.earth2me.essentials.Essentials;
import fr.iban.bukkitcore.commands.*;
import fr.iban.bukkitcore.listeners.*;
import fr.iban.bukkitcore.manager.*;
import fr.iban.bukkitcore.plan.PlanDataManager;
import fr.iban.bukkitcore.rewards.RewardsDAO;
import fr.iban.bukkitcore.utils.PluginMessageHelper;
import fr.iban.bukkitcore.utils.TextCallback;
import fr.iban.common.data.sql.DbAccess;
import fr.iban.common.data.sql.DbCredentials;
import fr.iban.common.manager.GlobalLoggerManager;
import fr.iban.common.manager.TrustedCommandsManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public final class CoreBukkitPlugin extends JavaPlugin {

    private static CoreBukkitPlugin instance;
    private String serverName;
    private CoreCommandHandlerVisitor coreCommandHandlerVisitor;
    private TeleportManager teleportManager;
    private Map<UUID, TextCallback> textInputs;
    private Essentials essentials;
    private RessourcesWorldManager ressourcesWorldManager;
    private MessagingManager messagingManager;
    private AccountManager accountManager;
    private BukkitPlayerManager playerManager;
    private TrustedCommandsManager trustedCommandManager;
    private BukkitTrustedUserManager trustedUserManager;
    private ApprovalManager approvalManager;
    private PlanDataManager planDataManager;
    private ServerManager serverManager;
    private GlobalLoggerManager.ConsoleLogHandler consoleLogHandler;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.serverName = getConfig().getString("servername");

        if (getServer().getPluginManager().isPluginEnabled("Essentials")) {
            essentials = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
            getServer().getPluginManager().registerEvents(new EssentialsListeners(this), this);
        }

        try {
            DbAccess.initPool(new DbCredentials(getConfig().getString("database.host"), getConfig().getString("database.user"), getConfig().getString("database.password"), getConfig().getString("database.dbname"), getConfig().getInt("database.port")));
        } catch (Exception e) {
            getLogger().severe("Erreur lors de l'initialisation de la connexion sql.");
            Bukkit.shutdown();
        }

        RewardsDAO.createTables();

        textInputs = new HashMap<>();

        this.accountManager = new AccountManager(this);
        this.teleportManager = new TeleportManager(this);
        this.ressourcesWorldManager = new RessourcesWorldManager(this);
        this.messagingManager = new MessagingManager(this);
        this.trustedCommandManager = new TrustedCommandsManager();
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> getTrustedCommandManager().loadTrustedCommands());
        messagingManager.init();
        this.playerManager = new BukkitPlayerManager(this);
        this.trustedUserManager = new BukkitTrustedUserManager(this);
        this.approvalManager = new ApprovalManager(this, messagingManager, trustedUserManager);
        this.planDataManager = new PlanDataManager(this);
        this.serverManager = new ServerManager(this);

        registerListeners(
                new HeadDatabaseListener(),
                new InventoryListener(),
                new AsyncChatListener(this),
                new JoinQuitListeners(this),
                new PlayerMoveListener(this),
                new DeathListener(this),
                new CommandsListener(this),
                new CoreMessageListener(this)
        );

        registerCommands();
        initLogger();

        PluginMessageHelper.registerChannels(this);
    }

    @Override
    public void onDisable() {
        closeLogger();
        messagingManager.close();
        DbAccess.closePool();
    }

    private void registerCommands() {
        BukkitCommandHandler commandHandler = BukkitCommandHandler.create(this);
        this.coreCommandHandlerVisitor = new CoreCommandHandlerVisitor(this);
        commandHandler.accept(coreCommandHandlerVisitor);
        commandHandler.register(new TeleportCommands(this));
        commandHandler.register(new TrustCommandsCMD(this));
        commandHandler.register(new ServerSwitchCommands(this));
        commandHandler.registerBrigadier();

        getCommand("core").setExecutor(new CoreCMD(this));
        getCommand("abbc").setExecutor(new ActionBarCMD());
        getCommand("options").setExecutor(new OptionsCMD());
        getCommand("recompenses").setExecutor(new RecompensesCMD());
        getCommand("recompenses").setTabCompleter(new RecompensesCMD());
        getCommand("bungeebroadcast").setExecutor(new BungeeBroadcastCMD());
    }

    private void registerListeners(Listener... listeners) {

        PluginManager pm = Bukkit.getPluginManager();

        for (Listener listener : listeners) {
            pm.registerEvents(listener, this);
        }

    }

    public void initLogger() {
        this.consoleLogHandler = new GlobalLoggerManager.ConsoleLogHandler(serverName);
        GlobalLoggerManager.initLogger();
        Logger globalLogger = getServer().getLogger();
        globalLogger.addHandler(consoleLogHandler);
    }

    public void closeLogger() {
        Logger globalLogger = getServer().getLogger();
        globalLogger.removeHandler(consoleLogHandler);
        GlobalLoggerManager.shutdownLogger();
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

    public TrustedCommandsManager getTrustedCommandManager() {
        return trustedCommandManager;
    }

    public MessagingManager getMessagingManager() {
        return messagingManager;
    }

    public BukkitPlayerManager getPlayerManager() {
        return playerManager;
    }

    public CoreCommandHandlerVisitor getCommandHandlerVisitor() {
        return coreCommandHandlerVisitor;
    }

    public BukkitTrustedUserManager getTrustedUserManager() {
        return trustedUserManager;
    }

    public ApprovalManager getApprovalManager() {
        return approvalManager;
    }

    public PlanDataManager getPlanDataManager() {
        return planDataManager;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }
}