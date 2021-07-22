package fr.iban.bungeecore.commands;

import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Option;
import fr.iban.spartacube.data.Account;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgToggleCMD extends Command {
	
	public MsgToggleCMD(String name, String permission) {
		super(name, permission);
	}
	  
	public void execute(CommandSender sender, String[] args) {
	  if (sender instanceof ProxiedPlayer) {
	      ProxiedPlayer player = (ProxiedPlayer)sender;
			AccountProvider ap = new AccountProvider(player.getUniqueId());
			Account account = ap.getAccount();
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
