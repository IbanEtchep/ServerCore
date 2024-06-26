package fr.iban.velocitycore.manager;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import de.themoep.minedown.adventure.MineDown;
import de.themoep.minedown.adventure.MineDownParser;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import fr.iban.velocitycore.CoreVelocityPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.william278.papiproxybridge.api.PlaceholderAPI;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ChatManager {

    private final CoreVelocityPlugin plugin;
    private final AccountManager accountManager;
    private final ProxyServer server;
    private boolean isMuted = false;
    private final String pingPrefix;
    private final Map<Player, Player> replies = new HashMap<>();

    public ChatManager(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.accountManager = plugin.getAccountManager();
        this.pingPrefix = plugin.getConfig().getString("ping-prefix", "&e");
    }

    public void sendGlobalMessage(UUID senderUUID, String message) {
        Player sender = server.getPlayer(senderUUID).orElseThrow();

        if (message.startsWith("$") && sender.hasPermission("servercore.staffchat")) {
            sendStaffMessage(sender, message.substring(1));
            return;
        }

        if (isMuted && !sender.hasPermission("servercore.chatmanage")) {
            return;
        }

        if (sender.hasPermission("servercore.colors")) {
            message = componentToLegacy(parseMineDownInlineFormatting(message));
        }

        Account senderAccount = accountManager.getAccount(senderUUID);

        if (!senderAccount.getOption(Option.TCHAT)) {
            sender.sendMessage(MineDown.parse("&cVous ne pouvez pas envoyer ce message car votre tchat est désactivé"));
            logMessage(MineDown.parse("§8[§CDÉSACTIVÉ§8]§r " + message));
            return;
        }

        String finalMessage = message;
        replacePlaceHolders(plugin.getConfig().getString("chat-format").trim(), sender).thenAccept(chatFormat -> {
            Component prefixComponent = MineDown.parse(chatFormat);

            for (Player receiverPlayer : server.getAllPlayers()) {
                String pmessage = finalMessage;
                UUID receiverUUID = receiverPlayer.getUniqueId();
                Account receiverAccount = accountManager.getAccount(receiverUUID);
                String receiverUsername = receiverPlayer.getUsername();

                if (!receiverAccount.getOption(Option.TCHAT) || receiverAccount.getIgnoredPlayers().contains(sender.getUniqueId())) {
                    continue;
                }

                if (!receiverUUID.equals(senderUUID) && pmessage.toLowerCase().contains(receiverUsername.toLowerCase()) && receiverAccount.getOption(Option.MENTION)) {
                    String ping = pingPrefix + receiverUsername + "&r";
                    pmessage = pmessage.replace(receiverUsername, componentToLegacy(MineDown.parse(ping)));
                }

                Component finalPrefix = prefixComponent;
                if(receiverUUID.equals(senderUUID)) {
                    finalPrefix = prefixComponent.hoverEvent(null);
                    finalPrefix = finalPrefix.clickEvent(null);
                }

                Component finalMessageComponent = componentFromLegacy(pmessage);

                receiverPlayer.sendMessage(finalPrefix.append(finalMessageComponent));
            }

            logMessage(prefixComponent.append(componentFromLegacy(finalMessage)));
        });
    }

    private CompletableFuture<String> replacePlaceHolders(String message, Player sender) {
        final PlaceholderAPI api = PlaceholderAPI.createInstance();
        message = message.replace("%player%", sender.getUsername());
        message = message.replace("%premium%", getPremiumString(sender));

        return api.formatPlaceholders(message, sender.getUniqueId());

    }

    public void sendAnnonce(UUID uuid, String annonce) {
        Player player = server.getPlayer(uuid).orElse(null);

        if (player == null) {
            return;
        }

        if (isMuted && !player.hasPermission("servercore.chatmanage")) {
            return;
        }


        plugin.getServer().sendMessage(MineDown.parse("&#f07e71&&lAnnonce de &#fbb29e&&l" + player.getUsername() + " &#f07e71&➤ &#7bc8fe&&l" + annonce));
    }

    private void sendStaffMessage(Player sender, String message) {
        String prefix = plugin.getConfig().getString("staff-chat-format");
        replacePlaceHolders(prefix, sender).thenAccept(chatFormat -> {
            Component fullMessage = MineDown.parse(chatFormat + message);

            plugin.getServer().getAllPlayers().forEach(p -> {
                if (p.hasPermission("servercore.staffchat")) {
                    p.sendMessage(fullMessage);
                }
            });

            logMessage(fullMessage);
        });
    }

    public void toggleChat(Player sender) {
        isMuted = !isMuted;
        if (isMuted) {
            plugin.getServer().sendMessage(MineDown.parse("&cLe chat est désormais muet."));
        } else {
            plugin.getServer().sendMessage(MineDown.parse("&aLe chat n'est plus muet."));
        }
    }

    public void sendMessage(Player sender, Player target, String message) {
        UUID senderUUID = sender.getUniqueId();
        String senderName = sender.getUsername();
        String targetName = target.getUsername();
        Account account = accountManager.getAccount(senderUUID);

        if (account.getOption(Option.MSG) || sender.hasPermission("servercore.msgtogglebypass")) {
            if (!account.getIgnoredPlayers().contains(senderUUID)) {
                Component senderComponent;
                Component targetComponent;

                if (target.hasPermission("servercore.staff")) {
                    senderComponent = MineDown.parse("&8Moi &7➔ &8[&6Staff&8] &c" + targetName + " &6➤&7 " + message);
                } else {
                    senderComponent = MineDown.parse("&8Moi &7➔ &c" + targetName + " &6➤&7 " + message);
                }
                if (sender.hasPermission("servercore.staff")) {
                    targetComponent = MineDown.parse("&8[&6Staff&8] &c" + senderName + " &7➔ &8Moi &6➤&7 " + message);
                } else {
                    targetComponent = MineDown.parse("&c" + senderName + " &7➔ &8Moi &6➤&7 " + message);
                }

                targetComponent = targetComponent
                        .hoverEvent(HoverEvent.showText(Component.text("Cliquez pour répondre")))
                        .clickEvent(ClickEvent.suggestCommand("/msg " + senderName + " "));

                sender.sendMessage(senderComponent);
                target.sendMessage(targetComponent);
                logMessage(MineDown.parse("&c" + senderName + " &7 ➔  " + "&8" + targetName + " &6➤ " + "&7 " + message));
            }
        } else {
            sender.sendMessage(MineDown.parse("&c" + target.getUsername() + " a désactivé ses messages"));
        }

        replies.put(sender, target);
        replies.put(target, sender);
    }

    private String getPremiumString(Player player) {
        if (player.hasPermission("premium")) {
            return "✮ ";
        } else {
            return "";
        }
    }

    private Component parseMineDownInlineFormatting(String message, String... replacements) {
        return new MineDown(message)
                .disable(MineDownParser.Option.ADVANCED_FORMATTING)
                .disable(MineDownParser.Option.SIMPLE_FORMATTING)
                .replace(replacements)
                .toComponent();
    }

    private String componentToLegacy(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    private Component componentFromLegacy(String legacy) {
        return LegacyComponentSerializer.legacySection().deserialize(legacy);
    }

    private void logMessage(Component message) {
        server.getConsoleCommandSource().sendMessage(message);
    }

    @Nullable
    public Player getPlayerToReply(Player player) {
        return replies.get(player);
    }

    public void clearPlayerReplies(Player player) {
        replies.remove(player);

        for (Map.Entry<Player, Player> entry : replies.entrySet()) {
            if (entry.getValue().equals(player)) {
                replies.remove(entry.getKey());
            }
        }
    }
}