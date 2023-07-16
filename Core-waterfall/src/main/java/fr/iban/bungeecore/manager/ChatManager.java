package fr.iban.bungeecore.manager;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.commands.ReplyCMD;
import fr.iban.bungeecore.commands.SocialSpyCMD;
import fr.iban.bungeecore.commands.StaffChatToggle;
import fr.iban.bungeecore.utils.HexColor;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.shared.TAB;
import me.neznamy.tab.shared.features.PlaceholderManagerImpl;
import me.neznamy.tab.shared.platform.TabPlayer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class ChatManager {

	private final CoreBungeePlugin plugin;
	private boolean isMuted = false;
	private final ChatColor pingColor;

	public ChatManager(CoreBungeePlugin plugin) {
		this.plugin = plugin;
		this.pingColor = ChatColor.of(plugin.getConfiguration().getString("ping-color"));
	}

	public void sendGlobalMessage(UUID uuid, String message) {
		ProxyServer server = ProxyServer.getInstance();
		server.getScheduler().runAsync(plugin, () -> {
			ProxiedPlayer player = server.getPlayer(uuid);
			String msg = message;

			if(player.hasPermission("servercore.colors")) {
				msg = HexColor.translateColorCodes(msg);
			}

			//vérif si le message est un message staff
			if(msg.startsWith("$") && player.hasPermission("servercore.staffchat")) {
				sendStaffMessage(player, msg.substring(1));
				return;
			}

			if(isMuted && !player.hasPermission("servercore.chatmanage")) {
				return;
			}

			AccountManager accountManager = plugin.getAccountManager();
			Account account = accountManager.getAccount(player.getUniqueId());

			String prefix =  plugin.getConfiguration().getString("chat-format");
			prefix = prefix.replace("%player%", player.getName());
			prefix = prefix.replace("%lp_prefix%", getPrefix(player));
			prefix = prefix.replace("%lp_suffix%", getSuffix(player));
			prefix = prefix.replace("%premium%", getPremiumString(player));
			prefix = HexColor.translateColorCodes(prefix);
			prefix = replacePlaceHolders(prefix, uuid);
			msg = prefix+msg;

			if(!account.getOption(Option.TCHAT)) {
				player.sendMessage(TextComponent.fromLegacyText("§cVous ne pouvez pas envoyer ce message car votre tchat est désactivé"));
				ProxyServer.getInstance().getLogger().info(translateColors(HexColor.translateColorCodes("§8[§CDÉSACTIVÉ§8]§r " + msg)));
				return;
			}

			//Envoi du message à chaque joueur
			for (ProxiedPlayer p: ProxyServer.getInstance().getPlayers()) {
				String pmessage = msg;
				Account account2 = accountManager.getAccount(p.getUniqueId());
				if(!account2.getOption(Option.TCHAT)) continue;

				//vérif si le joueur est mentionné dans le message.
				if (!p.getUniqueId().equals(player.getUniqueId()) && pmessage.toLowerCase().contains(p.getName().toLowerCase()) && account2.getOption(Option.MENTION)) {
					String ping = pingColor + "@" + p.getName() + "§r";
					pmessage = pmessage.replace(p.getName(), ping);
				}

				if(!account2.getIgnoredPlayers().contains(player.getUniqueId())){
					p.sendMessage(getComponentMessage(pmessage, uuid, player.getName(), p.getName()));
				}

			}

			//Envoi du message à la console
			ProxyServer.getInstance().getLogger().info(msg);

		});
	}

	private TextComponent getComponentMessage(String message, UUID uuid, String senderName, String targetName) {
		TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(message));
		String chatHover = plugin.getConfiguration().getString("chat-hover");
		if(!targetName.equals(senderName)) {
			String chatHoverSendMessage = plugin.getConfiguration().getString("chat-hover-send-message", "");
			if(chatHoverSendMessage.length() > 0) {
				chatHover += "\n" + chatHoverSendMessage;
			}
			textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + senderName + " "));
		}
		if(chatHover.length() > 0) {
			textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
					TextComponent.fromLegacyText(replacePlaceHolders(HexColor.translateColorCodes(chatHover), uuid))));
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
		ProxyServer server = ProxyServer.getInstance();
		ProxiedPlayer player = server.getPlayer(uuid);

		if(isMuted && !player.hasPermission("servercore.chatmanage")) {
			return;
		}

		ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(HexColor.translateColorCodes("#f07e71§lAnnonce de #fbb29e§l"+ player.getName() + " #f07e71➤ #7bc8fe§l" + annonce)));
	}

	private String translateColors(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	private void sendStaffMessage(ProxiedPlayer sender, String message) {
		ProxyServer.getInstance().getPlayers().forEach( p -> {
			if(p.hasPermission("servercore.staffchat")) {
				if (!StaffChatToggle.sc.contains(p)) {
					p.sendMessage(TextComponent.fromLegacyText(HexColor.translateColorCodes("§8[§3§lStaff§8] " + getSuffix(sender) + "§l" + getPrefix(sender) + "§l " + sender.getName() + " §8§l➤ " + getSuffix(sender) + "§l" + message)));
				}
			}
		});
		ProxyServer.getInstance().getLogger().info(HexColor.translateColorCodes("§8[§3§lStaff§8] " + getSuffix(sender) + "§l" + getPrefix(sender) + "§l " + sender.getName() + " §8§l➤ " + getSuffix(sender) + "§l" + message));
	}

	public void toggleChat(CommandSender sender) {
		isMuted = !isMuted;
		if(isMuted) {
			ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText("§cLe chat a été rendu muet par "+ sender.getName()+"."));
		}else {
			ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText("§aLe chat n'est plus muet."));
		}
	}

	public void sendMessage(ProxiedPlayer sender, ProxiedPlayer target, String msg) {
		UUID senderUUID = sender.getUniqueId();
		String senderName = sender.getName();
		String targetName = target.getName();

		ProxyServer.getInstance().getScheduler().runAsync(CoreBungeePlugin.getInstance(), () -> {
			String message = msg;
			if (sender.hasPermission("servercore.colors")) {
				message = HexColor.translateColorCodes(message);
			}

			AccountManager accountManager = plugin.getAccountManager();
			Account account = accountManager.getAccount(senderUUID);
			if (account.getOption(Option.MSG) || sender.hasPermission("servercore.msgtogglebypass")) {
				if (!account.getIgnoredPlayers().contains(senderUUID)) {

					for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
						if (SocialSpyCMD.sp.contains(p)) {
							p.sendMessage(TextComponent.fromLegacyText("§8[§cSocialSpy§8] §c" + senderName + " §7➔ " +  "§8" + targetName + " §6➤ " +  "§7 " + message));
						}
					}

					if (target.hasPermission("servercore.staff")) {
						sender.sendMessage(TextComponent.fromLegacyText("§8Moi §7➔ §8[§6Staff§8] §c" + targetName + " §6➤§7 " + message));
					} else {
						sender.sendMessage(TextComponent.fromLegacyText("§8Moi §7➔ §c" + targetName + " §6➤§7 " + message));
					}
					if (sender.hasPermission("servercore.staff")) {
						target.sendMessage(getSendMessageComponent("§8[§6Staff§8] §c" + senderName + " §7➔ §8Moi §6➤§7 " + message, senderName));
					}else {
						target.sendMessage(getSendMessageComponent("§c" + senderName + " §7➔ §8Moi §6➤§7 " + message, senderName));
					}
					ProxyServer.getInstance().getLogger().info("§c" + senderName + " §7 ➔  " + "§8" + targetName + " §6➤ " + "§7 " + message);
				}
			} else {
				sender.sendMessage(TextComponent.fromLegacyText("§c" + target.getName() + " a désactivé ses messages"));
			}

			ReplyCMD.getReplies().put(sender, target);
			ReplyCMD.getReplies().put(target, sender);
		});
	}


	private TextComponent getSendMessageComponent(String message, String senderName) {
		TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(message));
		String chatHoverSendMessage = plugin.getConfiguration().getString("chat-hover-send-message", "");
		textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + senderName + " "));
		if (chatHoverSendMessage.length() > 0) {
			textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
					TextComponent.fromLegacyText(HexColor.translateColorCodes(chatHoverSendMessage))));
		}
		return textComponent;
	}

	/*
	 * Luckperms
	 */

	private LuckPerms luckapi = LuckPermsProvider.get();

	private User loadUser(ProxiedPlayer player) {
		if (!player.isConnected())
			throw new IllegalStateException("Player is offline!");
		return luckapi.getUserManager().getUser(player.getUniqueId());
	}

	private CachedMetaData playerMeta(ProxiedPlayer player) {
		return loadUser(player).getCachedData().getMetaData(luckapi.getContextManager().getQueryOptions(player));
	}

	private String getPrefix(ProxiedPlayer player) {
		String prefix = playerMeta(player).getPrefix();
		return (prefix != null) ? prefix : "";
	}

	private String getSuffix(ProxiedPlayer player) {
		String suffix = playerMeta(player).getSuffix();
		return (suffix != null) ? suffix : "";
	}

	private String getPremiumString(ProxiedPlayer player){
		if(player.hasPermission("premium")){
			return "✮ ";
		}else{
			return "";
		}
	}

}