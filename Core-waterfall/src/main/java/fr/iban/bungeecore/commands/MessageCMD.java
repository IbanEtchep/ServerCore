package fr.iban.bungeecore.commands;

import java.util.ArrayList;
import java.util.List;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.bungeecore.manager.AccountManager;
import fr.iban.bungeecore.utils.HexColor;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class MessageCMD extends Command implements TabExecutor {

	private CoreBungeePlugin plugin;

	public MessageCMD(String name, String permission, CoreBungeePlugin plugin, String name2, String name3, String name4, String name5, String name6) {
		super(name, permission, name2, name3, name4, name5, name6);
		this.plugin = plugin;
	}	

	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0)
			sender.sendMessage(TextComponent.fromLegacyText("§e/msg [Joueur] [Message]" + ChatColor.RESET));  
		if (args.length > 0)
			if (sender instanceof ProxiedPlayer) {
				ProxiedPlayer player = (ProxiedPlayer)sender;
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if (target == null) {
					player.sendMessage(TextComponent.fromLegacyText("§c" + args[0] + " est hors-ligne!" + ChatColor.RESET));
					return;
				} 

				if (args.length > 1) {
					StringBuilder sb = new StringBuilder("");
					for (int i = 1; i < args.length; i++)
						sb.append(args[i]).append(" "); 
					String msg = sb.toString();

					ProxyServer.getInstance().getScheduler().runAsync(CoreBungeePlugin.getInstance(), () -> {
						String message = msg;
						if(player.hasPermission("spartacube.colors")) {
							message = HexColor.translateColorCodes(message);
						}
						AccountManager accountManager = plugin.getAccountManager();
						Account account = accountManager.getAccount(player.getUniqueId());
						Account account2 = accountManager.getAccount(target.getUniqueId());
						if (account.getOption(Option.MSG) || player.hasPermission("spartacube.msgtogglebypass")) {
							if(!account2.getIgnoredPlayers().contains(player.getUniqueId())) {
								if(target.hasPermission("spartacube.staff")) {
									player.sendMessage(TextComponent.fromLegacyText("§8Moi §7➔ §8[§6Staff§8] §c" + target.getName() + " §6➤§7 " + message));
								} else {
									player.sendMessage(TextComponent.fromLegacyText("§8Moi §7➔ §c" + target.getName() + " §6➤§7 " + message));
								}
								if(player.hasPermission("spartacube.staff")) {
									target.sendMessage(TextComponent.fromLegacyText("§8[§6Staff§8] §c" + player.getName() + " §7➔ §8Moi §6➤§7 " + message));
								} else {
									target.sendMessage(TextComponent.fromLegacyText("§c" + player.getName() + " §7➔ §8Moi §6➤§7 " + message));
								}
								ProxyServer.getInstance().getLogger().info("§c" + player.getName() + " §7 ➔  " + "§8" + target.getName() + " §6➤ " +  "§7 " + message);
							}
						} else {
							player.sendMessage(TextComponent.fromLegacyText("§c" + target.getName() + " a désactivé ses messages"));
						}
						ReplyCMD.getReplies().put(player, target);
						ReplyCMD.getReplies().put(target, player);

						for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {    
							if (SocialSpyCMD.sp.contains(p)) {
								p.sendMessage(TextComponent.fromLegacyText("§8[§cSocialSpy§8] §c" + player.getName() + " §7➔ " +  "§8" + target.getName() + " §6➤ " +  "§7 " + message));
							}
						}
					});
				} else {
					sender.sendMessage(TextComponent.fromLegacyText("§cVeuillez entrez un message." + ChatColor.RESET));
				} 
			} 
	} 

	private String translateColors(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}


	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
		List<String> playernames = new ArrayList<>();
		if(args.length == 1) {
			for(ProxiedPlayer p: ProxyServer.getInstance().getPlayers()){
				if(!sender.getName().equals(p.getName()) && p.getName().toLowerCase().startsWith(args[0].toLowerCase()))
					playernames.add(p.getName());
			}
		}
		return playernames;
	}

}
