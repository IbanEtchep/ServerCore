package fr.iban.bungeecore;

import fr.iban.bungeecore.commands.*;
import fr.iban.bungeecore.listeners.*;
import fr.iban.bungeecore.manager.*;
import fr.iban.bungeecore.utils.TabHook;
import fr.iban.common.data.sql.DbAccess;
import fr.iban.common.data.sql.DbCredentials;
import fr.iban.common.data.sql.DbTables;
import fr.iban.common.manager.GlobalLoggerManager;
import fr.iban.common.manager.PlayerManager;
import fr.iban.common.teleport.SLocation;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import revxrsal.commands.bungee.BungeeCommandHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

public final class CoreBungeePlugin extends Plugin {
    private static CoreBungeePlugin instance;
    private Configuration configuration;
    private AnnoncesManager announceManager;
    private ChatManager chatManager;
    private TeleportManager teleportManager;
    private PlayerManager playerManager;
    private TabHook tabHook;
    private TreeMap<String, SLocation> currentEvents;
    private MessagingManager messagingManager;
    private AccountManager accountManager;
    private GlobalLoggerManager.ConsoleLogHandler consoleLogHandler;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadConfig();
        currentEvents = new TreeMap<>();

        try {
            DbAccess.initPool(new DbCredentials(configuration.getString("database.host"), configuration.getString("database.user"), configuration.getString("database.password"), configuration.getString("database.dbname"), configuration.getInt("database.port")));
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().severe("Erreur lors de l'initialisation de la connexion sql.");
            getProxy().stop();
            return;
        }

        DbTables.createTables();

        accountManager = new AccountManager(this);
        messagingManager = new MessagingManager(this);
        messagingManager.init();
        announceManager = new AnnoncesManager(this);
        chatManager = new ChatManager(this);
        teleportManager = new TeleportManager(this);
        playerManager = new PlayerManager();
        playerManager.clearOnlinePlayersFromDB();

        getProxy().registerChannel("proxy:chat");
        getProxy().registerChannel("proxy:annonce");
        getProxy().registerChannel("proxy:send");

        registerEvents(
                new ProxyJoinQuitListener(this),
                new ProxyPingListener(this),
                new PluginMessageListener(),
                new CommandListener(this),
                new CoreMessageListener(this)
        );

        registerCommands(
                new AnnounceCMD("announce", this),
                new ChatCMD("chat"),
                new TptoggleCMD("tptoggle", this),
                new IgnoreListCMD("ignorelist", this),
                new StaffChatToggle("sctoggle", "servercore.sctoggle", "staffchattoggle"),
                new MessageCMD("msg", "servercore.msg", this, "message", "m", "w", "tell", "t"),
                new ReplyCMD("reply", "servercore.reply", "r", this),
                new SudoCMD("sudo", "servercore.sudo"),
                new SocialSpyCMD("socialspy", "servercore.socialspy"),
                new MsgToggleCMD("msgtoggle", "servercore.msgtoggle", this),
                new JoinEventCMD("joinevent", this),
                new TabCompleteCMD("baddtabcomplete", "servercore.addtabcomplete", this),
                new AnnounceEventCMD("announceevent", this),
                new CoreCMD("bcore", "servercore.reload", this)
        );

        tabHook = new TabHook(this);
        tabHook.enable();

        initLogger();
    }

    @Override
    public void onDisable() {
        closeLogger();
        messagingManager.close();
        DbAccess.closePool();
        getProxy().unregisterChannel("proxy:chat");
        getProxy().unregisterChannel("proxy:annonce");
        getProxy().unregisterChannel("proxy:send");
        tabHook.disable();
    }

    public void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getProxy().getPluginManager().registerListener(this, listener);
        }
    }

    public void registerCommands(Command... commands) {
        for (Command command : commands) {
            getProxy().getPluginManager().registerCommand(this, command);
        }

        BungeeCommandHandler commandHandler = BungeeCommandHandler.create(this);
        commandHandler.register(new CoreCommands(this));
        commandHandler.register(new IgnoreCommand(this));
        commandHandler.register(new TeleportCommands(this));
        commandHandler.register(new MiscellaneousCommands());
    }

    public static CoreBungeePlugin getInstance() {
        return instance;
    }

    public void loadConfig() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");


        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initLogger() {
        this.consoleLogHandler = new GlobalLoggerManager.ConsoleLogHandler(getServerName());
        GlobalLoggerManager.initLogger();
        Logger globalLogger = ProxyServer.getInstance().getLogger();
        globalLogger.addHandler(consoleLogHandler);
    }

    public void closeLogger() {
        Logger globalLogger = ProxyServer.getInstance().getLogger();
        globalLogger.removeHandler(consoleLogHandler);
        GlobalLoggerManager.shutdownLogger();
    }

    public String getServerName() {
        return "bungee";
    }

    public AnnoncesManager getAnnounceManager() {
        return announceManager;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public TeleportManager getTeleportManager() {
        return teleportManager;
    }

    public MessagingManager getMessagingManager() {
        return messagingManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public TreeMap<String, SLocation> getCurrentEvents() {
        return currentEvents;
    }

    public List<String> getMultipaperServers() {
        return getConfiguration().getStringList("multipaper-servers");
    }

    public boolean isMultiPaperServer(String server) {
        List<String> servers = getMultipaperServers();
        return servers != null && servers.contains(server);
    }
}
