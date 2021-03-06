package fr.iban.bungeecore;

import fr.iban.bungeecore.commands.*;
import fr.iban.bungeecore.listeners.*;
import fr.iban.bungeecore.manager.*;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.data.redis.RedisCredentials;
import fr.iban.common.data.sql.DbAccess;
import fr.iban.common.data.sql.DbCredentials;
import fr.iban.common.data.sql.DbTables;
import fr.iban.common.manager.PlayerManager;
import fr.iban.common.teleport.SLocation;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public final class CoreBungeePlugin extends Plugin {
    private static CoreBungeePlugin instance;
    private Configuration configuration;
    private AnnoncesManager announceManager;
    private ChatManager chatManager;
    private TeleportManager teleportManager;
    private PlayerManager playerManager;

    private Map<String, SLocation> currentEvents;
    private MessagingManager messagingManager;
    private AccountManager accountManager;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadConfig();
        currentEvents = new HashMap<>();

        try {
            DbAccess.initPool(new DbCredentials(configuration.getString("database.host"), configuration.getString("database.user"), configuration.getString("database.password"), configuration.getString("database.dbname"), configuration.getInt("database.port")));
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().severe("Erreur lors de l'initialisation de la connexion sql.");
            getProxy().stop();
            return;
        }

        if(getConfiguration().getString("messenger", "sql").equals("redis")) {
            try {
                RedisAccess.init(new RedisCredentials(configuration.getString("redis.host"), configuration.getString("redis.password"), configuration.getInt("redis.port"), configuration.getString("redis.clientName")));
            } catch (Exception e) {
                e.printStackTrace();
                getLogger().severe("Erreur lors de l'initialisation de la connexion redis.");
                getProxy().stop();
                return;
            }
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
                new IgnoreCMD("ignore", this),
                new ChatCMD("chat"),
                new TptoggleCMD("tptoggle", this),
                new IgnoreListCMD("ignorelist", this),
                new StaffChatToggle("sctoggle", "servercore.sctoggle", "staffchattoggle"),
                new MessageCMD("msg", "servercore.msg", this, "message", "m", "w", "tell", "t"),
                new ReplyCMD("reply", "servercore.reply", "r", this),
                new SudoCMD("sudo", "servercore.sudo"),
                new SocialSpyCMD("socialspy", "servercore.socialspy"),
                new MsgToggleCMD("msgtoggle", "servercore.msgtoggle", this),
                new BackCMD("back", "servercore.back.death", teleportManager),
                new JoinEventCMD("joinevent", this),
                new TabCompleteCMD("baddtabcomplete", "servercore.addtabcomplete", this),
                new AnnounceEventCMD("announceevent", this),
                new CoreCMD("bcore", "servercore.reload", this),
                new LastRtpCMD(this, "lastrtp")
        );
    }

    @Override
    public void onDisable() {
        messagingManager.close();
        if(getConfiguration().getString("messenger", "sql").equals("redis")) {
            RedisAccess.close();
        }
        DbAccess.closePool();
        getProxy().unregisterChannel("proxy:chat");
        getProxy().unregisterChannel("proxy:annonce");
        getProxy().unregisterChannel("proxy:send");
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

    public Map<String, SLocation> getCurrentEvents() {
        return currentEvents;
    }
}
