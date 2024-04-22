package fr.iban.velocitycore.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import de.themoep.minedown.adventure.MineDown;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import fr.iban.common.messaging.CoreChannel;
import fr.iban.common.messaging.message.PlayerInfo;
import fr.iban.common.utils.ArrayUtils;
import fr.iban.velocitycore.CoreVelocityPlugin;
import fr.iban.velocitycore.command.ReplyCMD;
import fr.iban.velocitycore.manager.AccountManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ProxyJoinQuitListener {

    private final CoreVelocityPlugin plugin;
    private final AccountManager accountManager;

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

    public ProxyJoinQuitListener(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
        this.accountManager = plugin.getAccountManager();
    }


    @Subscribe
    public void onProxyJoin(PostLoginEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        ProxyServer proxy = plugin.getServer();

        proxy.getScheduler().buildTask(plugin, () -> {
            Account account = accountManager.getAccount(uuid);
            account.setName(player.getUsername());
            account.setIp(player.getRemoteAddress().getHostString());

            if (account.getLastSeen() != 0) {
                if ((System.currentTimeMillis() - account.getLastSeen()) > 60000) {
                    String joinMessage = "&8[&a+&8] &8";
                    if ((System.currentTimeMillis() - account.getLastSeen()) > 2592000000L) {
                        joinMessage += String.format(ArrayUtils.getRandomFromArray(longAbsenceMessages), player.getUsername());
                    } else {
                        joinMessage += String.format(ArrayUtils.getRandomFromArray(joinMessages), player.getUsername());
                    }

                    Component message = MineDown.parse(joinMessage).hoverEvent(HoverEvent.showText(Component.text("Vu pour la dernière fois " + getLastSeen(account.getLastSeen()), NamedTextColor.GRAY)));

                    proxy.getAllPlayers().forEach(p -> {
                        Account receiverAccount = accountManager.getAccount(p.getUniqueId());
                        if (receiverAccount.getOption(Option.JOIN_MESSAGE) && !receiverAccount.getIgnoredPlayers().contains(player.getUniqueId())) {
                            p.sendMessage(message);
                        }
                    });

                    plugin.getServer().getConsoleCommandSource().sendMessage(message);
                }
            } else {
                String firstJoinMessage = "&8≫ &7" + String.format(ArrayUtils.getRandomFromArray(firstJoinMessages), player.getUsername());
                Component welcomeComponent = Component.text(firstJoinMessage)
                        .hoverEvent(HoverEvent.showText(Component.text("Clic !")))
                        .clickEvent(ClickEvent.suggestCommand(" Bienvenue " + player.getUsername()));

                proxy.sendMessage(welcomeComponent);
                plugin.getServer().getConsoleCommandSource().sendMessage(welcomeComponent);
            }

            account.setLastSeen(System.currentTimeMillis());
            accountManager.saveAccount(account);
            plugin.getPlayerManager().addOnlinePlayerToDB(uuid);
            plugin.getMessagingManager().sendMessage(CoreChannel.PLAYER_JOIN_CHANNEL, new PlayerInfo(player.getUniqueId(), player.getUsername()));
        }).delay(100, TimeUnit.MILLISECONDS).schedule();
    }

    @Subscribe
    public void bungeeChat(PlayerChatEvent event) {
        if (event.getPlayer() == null) {
            return;
        }

        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();
        if (message.startsWith("/luckpermsbungee:") || message.startsWith("/bperm") || message.startsWith("/lpb") || message.startsWith("/luckpermsbungee")) {
            event.setResult(PlayerChatEvent.ChatResult.denied());
            player.sendMessage(Component.text("Cette commande est désactivée", NamedTextColor.RED));
        }
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent e) {
        Player player = e.getPlayer();
        ProxyServer proxy = plugin.getServer();
        Account account = accountManager.getAccount(player.getUniqueId());
        String quitMessage = "&8[&c-&8] &8" + String.format(ArrayUtils.getRandomFromArray(quitMessages), player.getUsername());
        Component quitMessageComponent = MineDown.parse(quitMessage);

        if ((System.currentTimeMillis() - account.getLastSeen()) > 60000) {
            proxy.getAllPlayers().forEach(p -> {
                Account account2 = accountManager.getAccount(p.getUniqueId());
                if (account2.getOption(Option.LEAVE_MESSAGE) && !account2.getIgnoredPlayers().contains(player.getUniqueId())) {
                    p.sendMessage(quitMessageComponent);
                }
            });
            plugin.getServer().getConsoleCommandSource().sendMessage(quitMessageComponent);
        }

        account.setLastSeen(System.currentTimeMillis());
        accountManager.saveAccount(account);
        plugin.getPlayerManager().removeOnlinePlayerFromDB(player.getUniqueId());
        plugin.getMessagingManager().sendMessage(CoreChannel.PLAYER_QUIT_CHANNEL, player.getUniqueId().toString());
        plugin.getChatManager().clearPlayerReplies(player);
    }

    private String getLastSeen(long time) {
        if (time == 0) return "jamais";
        PrettyTime prettyTime = new PrettyTime(new Locale("fr"));
        return prettyTime.format(new Date(time));
    }

}