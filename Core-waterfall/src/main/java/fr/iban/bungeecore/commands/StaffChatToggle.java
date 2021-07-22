package fr.iban.bungeecore.commands;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatToggle extends Command {
	
	public StaffChatToggle(String name, String permission, String name2) {
		super(name, permission, name2);
	}
	public static List<ProxiedPlayer> sc = new ArrayList<>();
	  
	public void execute(CommandSender sender, String[] args) {
	  if (sender instanceof ProxiedPlayer) {
	      ProxiedPlayer player = (ProxiedPlayer)sender;
	      if (sc.contains(player)) {
	        sc.remove(player);
	        player.sendMessage(TextComponent.fromLegacyText("§aVous pouvez à nouveau recevoir des messages du staff"));
	        return;
	    } 
	      sc.add(player);
	      player.sendMessage(TextComponent.fromLegacyText("§cVous ne pouvez plus recevoir les messages du staff"));
	    } else {
	    sender.sendMessage(TextComponent.fromLegacyText("§cSeul la console peut faire cette commande"));
	  } 
	}
  }
