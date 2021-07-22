package fr.iban.bungeecore.commands;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SocialSpyCMD extends Command  {
	
	
	    public static List<ProxiedPlayer> sp = new ArrayList<>();
	  
		public SocialSpyCMD(String name, String permission) {
			super(name, permission);
		}
	  @Override
		public void execute(CommandSender sender, String[] args) {;
	    if (sender instanceof ProxiedPlayer) {
	      ProxiedPlayer player = (ProxiedPlayer)sender;
	        if (sp.contains(player)) {
	          sp.remove(player);
	          player.sendMessage(TextComponent.fromLegacyText("§cSocialspy désactivé"));
	          return;
	        } 
	        sp.add(player);
	        player.sendMessage(TextComponent.fromLegacyText("§aSocialspy activé"));
	      } 
	    } 
	  }

