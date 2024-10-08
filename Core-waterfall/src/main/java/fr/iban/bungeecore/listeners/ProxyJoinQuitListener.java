package fr.iban.bungeecore.listeners;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.commands.ReplyCMD;
import fr.iban.bungeecore.manager.AccountManager;
import fr.iban.bungeecore.utils.ChatUtils;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import fr.iban.common.messaging.CoreChannel;
import fr.iban.common.messaging.message.PlayerInfo;
import fr.iban.common.utils.ArrayUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ProxyJoinQuitListener implements Listener {

    private final CoreBungeePlugin plugin;
    private final String[] joinMessages =
            {
                    "%s s'est connecté !",
                    "%s est dans la place !",
                    "%s a rejoint le serveur !",
                    "Un %s sauvage apparaît ! ",
                    "Tout le monde, dites bonjour à %s !",
                    "%s vient d'arriver, faites place !"
            };

    private final String[] quitMessages =
            {
                    "%s nous a quitté :(",
                    "%s s'est déconnecté.",
                    "%s a disparu dans l'ombre.",
                    "Et voilà, %s est parti."
            };

    private final String[] longAbsenceMessages = {
            "%s est enfin de retour !",
            "Oh mon dieu, %s est revenu !",
            "Le prodige %s est de retour !",
            "%s, notre légende perdue est enfin de retour !",
            "Vous vous rappelez de %s ? Eh bien, devinez qui vient de revenir !"
    };

    private final String[] firstJoinMessages = {
            "Bienvenue %s, content de te voir pour la première fois !",
            "%s vient de franchir les portes du serveur pour la première fois !",
            "C'est la grande première de %s sur le serveur !",
            "Hey tout le monde, accueillons notre nouveau membre, %s !",
            "Un nouveau visage ! Salut %s, bienvenue sur le serveur !"
    };

    public ProxyJoinQuitListener(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onProxyJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        ProxyServer proxy = plugin.getProxy();

        proxy.getScheduler().schedule(plugin, () -> {
            if (!player.isConnected()) {
                return;
            }

            AccountManager accountManager = plugin.getAccountManager();
            Account account = accountManager.getAccount(uuid);
            account.setName(player.getName());
            account.setIp(player.getAddress().getHostString());

            if (account.getLastSeen() != 0) {
                if ((System.currentTimeMillis() - account.getLastSeen()) > 60000) {
                    String joinMessage = "§8[§a+§8] §8";
                    if ((System.currentTimeMillis() - account.getLastSeen()) > 2592000000L) {
                        joinMessage += String.format(ArrayUtils.getRandomFromArray(longAbsenceMessages), player.getName());
                    } else {
                        joinMessage += String.format(ArrayUtils.getRandomFromArray(joinMessages), player.getName());
                    }

                    TextComponent message = new TextComponent(joinMessage);
                    message.setHoverEvent(ChatUtils.getShowTextHoverEvent(ChatColor.GRAY + "Vu pour la dernière fois " + getLastSeen(account.getLastSeen())));

                    proxy.getPlayers().forEach(p -> {
                        Account account2 = accountManager.getAccount(p.getUniqueId());
                        if (account2.getOption(Option.JOIN_MESSAGE) && !account2.getIgnoredPlayers().contains(player.getUniqueId())) {
                            p.sendMessage(message);
                        }
                    });
                    proxy.getLogger().info(joinMessage);
                }
            } else {
                String firstJoinMessage = "§8≫ §7" + String.format(ArrayUtils.getRandomFromArray(firstJoinMessages), player.getName());
                BaseComponent[] welcomponent = new ComponentBuilder(firstJoinMessage).event(ChatUtils.getShowTextHoverEvent("§7Clic !")).event(ChatUtils.getSuggestCommandClickEvent("Bienvenue " + player.getName())).create();
                proxy.getPlayers().forEach(p -> p.sendMessage(welcomponent));
                proxy.getLogger().info("§8≫ §7" + player.getName() + " s'est connecté pour la première fois !");
            }

            account.setLastSeen(System.currentTimeMillis());
            accountManager.saveAccount(account);
            plugin.getPlayerManager().addOnlinePlayer(uuid);
            plugin.getMessagingManager().sendMessage(CoreChannel.PLAYER_JOIN_CHANNEL, new PlayerInfo(player.getUniqueId(), player.getName()));
        }, 100, TimeUnit.MILLISECONDS);

    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();
        Map<ProxiedPlayer, ProxiedPlayer> replies = ReplyCMD.getReplies();
        if (replies.containsKey(p)) {
            ProxiedPlayer target = replies.get(p);
            if (target != null && replies.get(target) == p)
                replies.remove(target);
            replies.remove(p);
        }
    }

    @EventHandler(priority = 64)
    public void bungeeChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (event.getMessage().toLowerCase().startsWith("/luckpermsbungee:") || event.getMessage().toLowerCase().startsWith("/bperm") || event.getMessage().toLowerCase().startsWith("/lpb") || event.getMessage().toLowerCase().startsWith("/luckpermsbungee")) {
            event.setCancelled(true);
            player.sendMessage(TextComponent.fromLegacyText("§cCette commande est désactivé"));
        }
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
        ProxiedPlayer player = e.getPlayer();
        ProxyServer proxy = plugin.getProxy();

        proxy.getScheduler().runAsync(CoreBungeePlugin.getInstance(), () -> {
            AccountManager accountManager = plugin.getAccountManager();
            Account account = accountManager.getAccount(player.getUniqueId());

            if ((System.currentTimeMillis() - account.getLastSeen()) > 60000) {
                proxy.getPlayers().forEach(p -> {
                    Account account2 = accountManager.getAccount(p.getUniqueId());
                    if (account2.getOption(Option.LEAVE_MESSAGE) && !account2.getIgnoredPlayers().contains(player.getUniqueId())) {
                        p.sendMessage(TextComponent.fromLegacyText("§8[§c-§8] §8" + String.format(ArrayUtils.getRandomFromArray(quitMessages), player.getName())));
                    }
                });
                proxy.getLogger().info("§8[§c-§8] §8" + String.format(ArrayUtils.getRandomFromArray(quitMessages), player.getName()));
            }

            account.setLastSeen(System.currentTimeMillis());
            accountManager.saveAccount(account);
            plugin.getPlayerManager().removeOnlinePlayer(player.getUniqueId());
            plugin.getMessagingManager().sendMessage(CoreChannel.PLAYER_QUIT_CHANNEL, player.getUniqueId().toString());
        });
    }

    private String getLastSeen(long time) {
        if (time == 0) return "jamais";
        PrettyTime prettyTime = new PrettyTime(new Locale("fr"));
        return prettyTime.format(new Date(time));
    }

}