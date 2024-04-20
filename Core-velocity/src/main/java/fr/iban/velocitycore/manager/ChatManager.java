package fr.iban.velocitycore.manager;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import de.themoep.minedown.adventure.MineDown;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import fr.iban.velocitycore.CoreVelocityPlugin;
import fr.iban.velocitycore.command.ReplyCMD;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.shared.TAB;
import me.neznamy.tab.shared.features.PlaceholderManagerImpl;
import me.neznamy.tab.shared.platform.TabPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;

import java.util.UUID;

public class ChatManager {

    private final CoreVelocityPlugin plugin;
    private final ProxyServer server;
    private boolean isMuted = false;
    private final TextColor pingColor;
    private LuckPerms luckapi = LuckPermsProvider.get();

    public ChatManager(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.pingColor = TextColor.fromHexString(plugin.getConfig().getString("ping-color"));
    }

    public void sendGlobalMessage(UUID uuid, String message) {
        Player player = server.getPlayer(uuid).orElse(null);

        if (player == null) {
            return;
        }

        if(player.hasPermission("servercore.colors")){
//            message = HexColor.translateColorCodes(message);
        }

        if (message.startsWith("$") && player.hasPermission("servercore.staffchat")) {
            sendStaffMessage(player, message.substring(1));
            return;
        }

        if (isMuted && !player.hasPermission("servercore.chatmanage")) {
            return;
        }

        AccountManager accountManager = plugin.getAccountManager();
        Account account = accountManager.getAccount(player.getUniqueId());

        String prefix = plugin.getConfig().getString("chat-format");
        prefix = prefix.replace("%player%", player.getUsername());
        prefix = prefix.replace("%lp_prefix%", getPrefix(player));
        prefix = prefix.replace("%lp_suffix%", getSuffix(player));
        prefix = prefix.replace("%premium%", getPremiumString(player));
        prefix = replacePlaceHolders(prefix, uuid);
        message = prefix + message;

        if (!account.getOption(Option.TCHAT)) {
            player.sendMessage(MineDown.parse("&cVous ne pouvez pas envoyer ce message car votre tchat est désactivé"));
            plugin.getLogger().info("§8[§CDÉSACTIVÉ§8]§r " + message);
            return;
        }

        for (Player p : server.getAllPlayers()) {
            String pmessage = message;
            Account account2 = accountManager.getAccount(p.getUniqueId());
            if (!account2.getOption(Option.TCHAT)) continue;

            if (!p.getUniqueId().equals(player.getUniqueId()) && pmessage.toLowerCase().contains(p.getUsername().toLowerCase()) && account2.getOption(Option.MENTION)) {
                String ping = pingColor + "@" + p.getUsername() + "§r";
                pmessage = pmessage.replace(p.getUsername(), ping);
            }

            if (!account2.getIgnoredPlayers().contains(player.getUniqueId())) {
                p.sendMessage(getComponentMessage(pmessage, uuid, player.getUsername(), p.getUsername()));
            }

        }

        plugin.getLogger().info(message);
    }

    private Component getComponentMessage(String message, UUID uuid, String senderName, String targetName) {
        Component textComponent = Component.text(message);

        String chatHover = plugin.getConfig().getString("chat-hover");
        String chatHoverSendMessage = plugin.getConfig().getString("chat-hover-send-message", "");

        if (!targetName.equals(senderName)) {
            if (!chatHoverSendMessage.isEmpty()) {
                chatHover += "\n" + chatHoverSendMessage;
            }
            textComponent = textComponent.clickEvent(ClickEvent.suggestCommand("/msg " + senderName + " "));
        }

        if (!chatHover.isEmpty()) {
            String replacedHover = replacePlaceHolders(chatHover, uuid);
            textComponent = textComponent.hoverEvent(HoverEvent.showText(Component.text(replacedHover)));
        }

        return textComponent;
    }

    private String replacePlaceHolders(String message, UUID uuid) {
        TabAPI tabAPI = TabAPI.getInstance();
        TabPlayer tabPlayer = TAB.getInstance().getPlayer(uuid);
        PlaceholderManagerImpl placeholderManager = (PlaceholderManagerImpl) tabAPI.getPlaceholderManager();
        for (String placeholderIdentifier : placeholderManager.detectPlaceholders(message)) {
            message = message.replace(placeholderIdentifier, placeholderManager.getPlaceholder(placeholderIdentifier).getLastValue(tabPlayer));
        }
        return message;
    }

    public void sendAnnonce(UUID uuid, String annonce) {
        Player player = server.getPlayer(uuid).orElse(null);

        if (player == null) {
            return;
        }

        if (isMuted && !player.hasPermission("servercore.chatmanage")) {
            return;
        }


        plugin.getServer().sendMessage(MineDown.parse("#f07e71§lAnnonce de #fbb29e§l" + player.getUsername() + " #f07e71➤ #7bc8fe§l" + annonce));
    }

    private void sendStaffMessage(Player sender, String message) {
        plugin.getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission("servercore.staffchat")) {
                p.sendMessage(LegacyComponentSerializer.legacySection().deserialize("§8[§3§lStaff§8] " + getSuffix(sender) + "§l" + getPrefix(sender) + "§l " + sender.getUsername() + " §8§l➤ " + getSuffix(sender) + "§l" + message));
            }
        });
        plugin.getLogger().info("§8[§3§lStaff§8] " + getSuffix(sender) + "§l" + getPrefix(sender) + "§l " + sender.getUsername() + " §8§l➤ " + getSuffix(sender) + "§l" + message);
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

        AccountManager accountManager = plugin.getAccountManager();
        Account account = accountManager.getAccount(senderUUID);
        if (account.getOption(Option.MSG) || sender.hasPermission("servercore.msgtogglebypass")) {
            if (!account.getIgnoredPlayers().contains(senderUUID)) {
                if (target.hasPermission("servercore.staff")) {
                    sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize("§8Moi §7➔ §8[§6Staff§8] §c" + targetName + " §6➤§7 " + message));
                } else {
                    sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize("§8Moi §7➔ §c" + targetName + " §6➤§7 " + message));
                }
                if (sender.hasPermission("servercore.staff")) {
                    target.sendMessage(getSendMessageComponent("§8[§6Staff§8] §c" + senderName + " §7➔ §8Moi §6➤§7 " + message, senderName));
                } else {
                    target.sendMessage(getSendMessageComponent("§c" + senderName + " §7➔ §8Moi §6➤§7 " + message, senderName));
                }
                plugin.getLogger().info("§c" + senderName + " §7 ➔  " + "§8" + targetName + " §6➤ " + "§7 " + message);
            }
        } else {
            sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize("§c" + target.getUsername() + " a désactivé ses messages"));
        }

        ReplyCMD.getReplies().put(sender, target);
        ReplyCMD.getReplies().put(target, sender);
    }


    private Component getSendMessageComponent(String message, String senderName) {
        String chatHoverSendMessage = plugin.getConfig().getString("chat-hover-send-message", "");

        return MineDown.parse(
                "[{msg}](suggest_command:/msg {sender}, show_text={chat_hover_send_message}",
                "msg", message,
                "sender", senderName,
                "chat_hover_send_message", chatHoverSendMessage
        );
    }

    private User loadUser(Player player) {
        return luckapi.getUserManager().getUser(player.getUniqueId());
    }

    private CachedMetaData playerMeta(Player player) {
        return loadUser(player).getCachedData().getMetaData(luckapi.getContextManager().getQueryOptions(player));
    }

    private String getPrefix(Player player) {
        String prefix = playerMeta(player).getPrefix();
        return (prefix != null) ? prefix : "";
    }

    private String getSuffix(Player player) {
        String suffix = playerMeta(player).getSuffix();
        return (suffix != null) ? suffix : "";
    }

    private String getPremiumString(Player player) {
        if (player.hasPermission("premium")) {
            return "✮ ";
        } else {
            return "";
        }
    }

}