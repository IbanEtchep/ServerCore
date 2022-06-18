package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgToggleCMD extends Command {

	private CoreBungeePlugin plugin;
	
	public MsgToggleCMD(String name, String permission, CoreBungeePlugin plugin) {
		super(name, permission);
		this.plugin = plugin;
	}
	  
	public void execute(CommandSender sender, String[] args) {
	  if (sender instanceof ProxiedPlayer) {
	      ProxiedPlayer player = (ProxiedPlayer)sender;
			Account account = plugin.getAccountManager().getAccount(player.getUniqueId());
	      if (account.getOption(Option.MSG)) {
	    	account.setOption(Option.MSG, false);
		    player.sendMessage(TextComponent.fromLegacyText("§cVous ne pouvez plus recevoir les messages des joueurs"));
	    } else {
	    	account.setOption(Option.MSG, true);
		    player.sendMessage(TextComponent.fromLegacyText("§aVous pouvez à nouveau recevoir les messages des joueurs"));
	    }
	  } else {
	    sender.sendMessage(TextComponent.fromLegacyText("§cSeul un joueur peut faire cette commande"));
	  } 
	}
  }
