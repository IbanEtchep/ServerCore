package fr.iban.bungeecore.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Option;
import fr.iban.spartacube.data.Account;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class ReplyCMD extends Command implements TabExecutor {

	private static HashMap<ProxiedPlayer, ProxiedPlayer> replies = new HashMap<>();

	public ReplyCMD(String name, String permission, String name2) {
		super(name, permission, name2);
	}
	

	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0)
			sender.sendMessage(TextComponent.fromLegacyText( "§e/r [Message]" + ChatColor.RESET));  
		if (args.length > 0)
			if (sender instanceof ProxiedPlayer) {
				ProxiedPlayer player = (ProxiedPlayer)sender;
				if (getReplies().containsKey(player)) {
					ProxiedPlayer target = getReplies().get(player);
					if (target == null) {
						player.sendMessage(TextComponent.fromLegacyText("§c" + (getReplies().get(player)).getName() + " est hors-ligne!" + ChatColor.RESET));
						return;
					} 
					StringBuilder sb = new StringBuilder("");
					for (int i = 0; i < args.length; i++)
						sb.append(args[i]).append(" "); 
					String msg = sb.toString();
					ProxyServer.getInstance().getPlayers().forEach( p -> {    
						if (SocialSpyCMD.sp.contains(p)) {
							p.sendMessage(TextComponent.fromLegacyText("§8[§cSocialSpy§8] §c" + player.getName() + " §7➔ " +  "§8" + target.getName() + " §6➤ " +  "§7 " + msg));
						}  
					});
					ProxyServer.getInstance().getScheduler().runAsync(CoreBungeePlugin.getInstance(), () -> {
						Account account = new AccountProvider(target.getUniqueId()).getAccount();
						if (account.getOption(Option.MSG) || player.hasPermission("spartacube.msgtogglebypass")) {
							 if(target.hasPermission("spartacube.staff")) {
								 player.sendMessage(TextComponent.fromLegacyText("§8Moi §7➔ §8[§6Staff§8] §c" + target.getName() + " §6➤§7 " + msg));
							 } else {
								 player.sendMessage(TextComponent.fromLegacyText("§8Moi §7➔ §c" + target.getName() + " §6➤§7 " + msg));
							 }
							 if(player.hasPermission("spartacube.staff")) {
								 target.sendMessage(TextComponent.fromLegacyText("§8[§6Staff§8] §c" + player.getName() + " §7➔ §8Moi §6➤§7 " + msg));
							 } else {
								 target.sendMessage(TextComponent.fromLegacyText("§c" + player.getName() + " §7➔ §8Moi §6➤§7 " + msg));
							 }
							System.out.println("§c" + player.getName() + " §7 ➔  " +  "§8" + target.getName() + " §6➤ " +  "§7 " + msg);
						} else {
							player.sendMessage(TextComponent.fromLegacyText("§c" + target.getName() + " a désactivé ses messages"));
						}  
					});
				} else {
					sender.sendMessage(TextComponent.fromLegacyText("§cTu ne peux pas répondre, car personne ne t'a écrit." + ChatColor.RESET));
				} 
			} 

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

	public static Map<ProxiedPlayer, ProxiedPlayer> getReplies() {
		return replies;
	}
}
