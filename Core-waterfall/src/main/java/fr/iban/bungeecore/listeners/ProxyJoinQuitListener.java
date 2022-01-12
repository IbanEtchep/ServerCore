package fr.iban.bungeecore.listeners;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.event.ServerConnectedEvent;
import org.ocpsoft.prettytime.PrettyTime;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.commands.ReplyCMD;
import fr.iban.bungeecore.utils.ChatUtils;
import fr.iban.common.data.Account;
import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Option;
import fr.iban.common.data.redis.RedisAccess;
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

public class ProxyJoinQuitListener implements Listener {

	private CoreBungeePlugin plugin;
	private String[] joinMessages =
		{
				"%s s'est connecté !",
				"%s est dans la place !",
				"%s a rejoint le serveur !",
				"Un %s sauvage apparaît ! "
		};

	private String[] quitMessages =
		{
				"%s nous a quitté :(",
				"%s s'est déconnecté."
		};


	public ProxyJoinQuitListener(CoreBungeePlugin plugin) {
		this.plugin = plugin;
	}


	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProxyJoin(PostLoginEvent e) {
		ProxiedPlayer player = e.getPlayer();
		UUID uuid = player.getUniqueId();
		ProxyServer proxy = ProxyServer.getInstance();
		if(player.hasPermission("premium") && !player.hasPermission("group.support")){
			proxy.getPluginManager().dispatchCommand(proxy.getConsole(), "btab player "+player.getName()+" tabsuffix #feca57 ✮");
		}
		proxy.getScheduler().runAsync(plugin, () -> {
			AccountProvider accountProvider = new AccountProvider(uuid);
			Account account = accountProvider.getAccount();
			account.setName(player.getName());
			account.setIp(player.getAddress().getHostString());
			accountProvider.sendAccountToRedis(account);

			if(accountProvider.hasPlayedBefore()) {
				if((System.currentTimeMillis() - account.getLastSeen()) > 60000) {
					TextComponent message = new TextComponent(new StringBuilder().append("§8[§a+§8] §8").append(String.format(ArrayUtils.getRandomFromArray(joinMessages), player.getName())).toString());
					message.setHoverEvent(ChatUtils.getShowTextHoverEvent(ChatColor.GRAY+"Vu pour la dernière fois " + getLastSeen(account.getLastSeen())));

					ProxyServer.getInstance().getPlayers().forEach( p -> {
						AccountProvider ap = new AccountProvider(p.getUniqueId());
						Account account2 = ap.getAccount();
						if (account2.getOption(Option.JOIN_MESSAGE) && !account2.getIgnoredPlayers().contains(player.getUniqueId())) {
							p.sendMessage(message);
						}
					});
					ProxyServer.getInstance().getLogger().info("§8[§a+§8] §8" + String.format(ArrayUtils.getRandomFromArray(joinMessages), player.getName()));
				}
			}else {
				BaseComponent[] welcomponent = new ComponentBuilder("§8≫ §7" + player.getName() + " s'est connecté pour la première fois !").event(ChatUtils.getShowTextHoverEvent("§7Clic !")).event(ChatUtils.getSuggestCommandClickEvent("Bienvenue " + player.getName())).create();
				ProxyServer.getInstance().getPlayers().forEach( p -> p.sendMessage(welcomponent));
				ProxyServer.getInstance().getLogger().info("§8≫ §7" + player.getName() + " s'est connecté pour la première fois !");
			}
		});

	}

	@EventHandler
	public void onServerConnect(ServerConnectedEvent e){
		ProxiedPlayer player = e.getPlayer();
		RedisAccess.getInstance().getRedissonClient().getMap("ProxyPlayers").fastPut(player.getName(), player.getUniqueId().toString());
	}

	//	@EventHandler
	//	public void serverConnectEvent(ServerConnectEvent e) {
	//		if(!e.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)){
	//			return;
	//		}
	//		//e.setTarget(ProxyServer.getInstance().getServerInfo("Lobby"));
	//	}

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
		ProxiedPlayer player = (ProxiedPlayer)event.getSender();
		if(event.getMessage().toLowerCase().startsWith("/luckpermsbungee:") || event.getMessage().toLowerCase().startsWith("/bperm") || event.getMessage().toLowerCase().startsWith("/lpb") || event.getMessage().toLowerCase().startsWith("/luckpermsbungee")) {
			event.setCancelled(true);
			player.sendMessage(TextComponent.fromLegacyText("§6SpartaCube §8➤ §cCette commande est désactivé"));
		} 
	}

	@EventHandler
	public void onDisconnect(PlayerDisconnectEvent e) {
		ProxiedPlayer player = e.getPlayer();

		ProxyServer proxy = ProxyServer.getInstance();
		proxy.getPluginManager().dispatchCommand(proxy.getConsole(), "btab player "+player.getName()+" remove");

		ProxyServer.getInstance().getScheduler().runAsync(CoreBungeePlugin.getInstance(), () -> {
			AccountProvider accountProvider = new AccountProvider(player.getUniqueId());
			Account account = accountProvider.getAccount();

			if((System.currentTimeMillis() - account.getLastSeen()) > 60000) {
				ProxyServer.getInstance().getPlayers().forEach( p -> {
					AccountProvider ap = new AccountProvider(p.getUniqueId());
					Account account2 = ap.getAccount();
					if (account2.getOption(Option.LEAVE_MESSAGE) && !account2.getIgnoredPlayers().contains(player.getUniqueId())) {
						p.sendMessage(TextComponent.fromLegacyText("§8[§c-§8] §8" + String.format(ArrayUtils.getRandomFromArray(quitMessages), player.getName())));
					}
				});
				ProxyServer.getInstance().getLogger().info("§8[§c-§8] §8" + String.format(ArrayUtils.getRandomFromArray(quitMessages), player.getName()));
			}

			account.setLastSeen(System.currentTimeMillis());
			accountProvider.sendAccountToDB(account);
			accountProvider.removeAccountFromRedis();
			RedisAccess.getInstance().getRedissonClient().getMap("PendingTeleports").fastRemove(player.getUniqueId().toString());
			RedisAccess.getInstance().getRedissonClient().getMap("ProxyPlayers").fastRemove(player.getName());
		});
	}

	private String getLastSeen(long time){
		if(time == 0) return "jamais";
		PrettyTime prettyTime = new PrettyTime(new Locale("fr"));
		return prettyTime.format(new Date(time));
	}

}