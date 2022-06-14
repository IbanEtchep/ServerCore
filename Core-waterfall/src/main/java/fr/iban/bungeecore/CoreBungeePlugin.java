package fr.iban.bungeecore;

import fr.iban.bungeecore.chat.ChatManager;
import fr.iban.bungeecore.commands.*;
import fr.iban.bungeecore.event.CoreMessageEvent;
import fr.iban.bungeecore.listeners.*;
import fr.iban.bungeecore.runnables.SaveAccounts;
import fr.iban.bungeecore.teleport.*;
import fr.iban.bungeecore.utils.AnnoncesManager;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.common.data.redis.RedisCredentials;
import fr.iban.common.data.sql.DbAccess;
import fr.iban.common.data.sql.DbCredentials;
import fr.iban.common.data.sql.DbTables;
import fr.iban.common.messaging.AbstractMessenger;
import fr.iban.common.messaging.RedisMessenger;
import fr.iban.common.messaging.SqlMessenger;
import fr.iban.common.teleport.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.redisson.api.RMap;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class CoreBungeePlugin extends Plugin {

    private static final String RANKUP_CHANNEL = "survie:rankup";

    private static CoreBungeePlugin instance;
    private Configuration configuration;
    private AnnoncesManager announceManager;
    private AbstractMessenger messenger;
    private ChatManager chatManager;
    private TeleportManager teleportManager;

    private Map<String, SLocation> currentEvents;

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

        try {
            RedisAccess.init(new RedisCredentials(configuration.getString("redis.host"), configuration.getString("redis.password"), configuration.getInt("redis.port"), configuration.getString("redis.clientName")));
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().severe("Erreur lors de l'initialisation de la connexion redis.");
            getProxy().stop();
            return;
        }

        DbTables.createTables();

        startMessenger();

        announceManager = new AnnoncesManager();
        chatManager = new ChatManager(this);
        teleportManager = new TeleportManager(this);

        getProxy().registerChannel("proxy:chat");
        getProxy().registerChannel(RANKUP_CHANNEL);
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
                new AnnounceCMD("announce"),
                new IgnoreCMD("ignore"),
                new ChatCMD("chat"),
                new TptoggleCMD("tptoggle"),
                new IgnoreCMD("ignore"),
                new IgnoreListCMD("ignorelist"),
                new StaffChatToggle("sctoggle", "servercore.sctoggle", "staffchattoggle"),
                new MessageCMD("msg", "servercore.msg", "message", "m", "w", "tell", "t"),
                new ReplyCMD("reply", "servercore.reply", "r"),
                new SudoCMD("sudo", "servercore.sudo"),
                new SocialSpyCMD("socialspy", "servercore.socialspy"),
                new MsgToggleCMD("msgtoggle", "servercore.msgtoggle"),
                new BackCMD("back", "servercore.back.death", teleportManager),
                new JoinEventCMD("joinevent", this),
                new TabCompleteCMD("baddtabcomplete", "servercore.addtabcomplete", this),
                new AnnounceEventCMD("announceevent", this),
                new CoreCMD("bcore", "servercore.reload", this)
        );

        ProxyServer.getInstance().getScheduler().schedule(this, new SaveAccounts(), 0, 10, TimeUnit.MINUTES);

        RedissonClient redisClient = RedisAccess.getInstance().getRedissonClient();
        redisClient.getTopic("DeathLocation").addListener(DeathLocation.class, new DeathLocationListener(this));
        redisClient.getTopic("EventAnnounce").addListener(EventAnnounce.class, new EventAnnounceListener(this));
        RTopic tpToSlocTopic = redisClient.getTopic("TpToSLoc");
        tpToSlocTopic.addListener(TeleportToLocation.class, new TpToSLocListener(this));
        RTopic tpToPlayerTopic = redisClient.getTopic("TpToPlayer");
        tpToPlayerTopic.addListener(TeleportToPlayer.class, new TpToPlayerListener(this));
        RTopic tpRequestTopic = redisClient.getTopic("TpRequest");
        tpRequestTopic.addListener(TpRequest.class, new TpRequestListener(this));

    }

    @Override
    public void onDisable() {
        new SaveAccounts().run();
        RedisAccess.close();
        DbAccess.closePool();
        getProxy().unregisterChannel("proxy:chat");
        getProxy().unregisterChannel("proxy:annonce");
        getProxy().unregisterChannel("proxy:send");
        getProxy().unregisterChannel(RANKUP_CHANNEL);
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

    private void startMessenger() {
        switch (configuration.getString("messenger", "redis").toLowerCase()) {
            case "redis" -> {
                messenger = new RedisMessenger();
                messenger.setOnMessageListener(message -> {
                    if(!message.getServerFrom().equals("bungee")) {
                        getProxy().getPluginManager().callEvent(new CoreMessageEvent(message));
                    }
                });
            }
            case "sql" -> {
                messenger = new SqlMessenger() {
                    @Override
                    public void startPollTask() {
                        getProxy().getScheduler().schedule(instance, this::readNewMessages, 50L, 50L, TimeUnit.MILLISECONDS);
                    }

                    @Override
                    public void startCleanUpTask() {
                        getProxy().getScheduler().schedule(instance, this::readNewMessages, 1L, 1L, TimeUnit.MINUTES);
                    }
                };
                messenger.setOnMessageListener(message -> {
                    if(!message.getServerFrom().equals("bungee")) {
                        getProxy().getPluginManager().callEvent(new CoreMessageEvent(message));
                    }
                });
            }
        }
    }

    public AnnoncesManager getAnnounceManager() {
        return announceManager;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public TeleportManager getTeleportManager() {
        return teleportManager;
    }

    public Map<String, SLocation> getCurrentEvents() {
        return currentEvents;
    }

    public AbstractMessenger getMessenger() {
        return messenger;
    }

    public RMap<String, UUID> getProxyPlayer() {
        return RedisAccess.getInstance().getRedissonClient().getMap("ProxyPlayers");
    }
}
