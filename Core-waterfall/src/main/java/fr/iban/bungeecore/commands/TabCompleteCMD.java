package fr.iban.bungeecore.commands;

import java.util.List;

import fr.iban.bungeecore.CoreBungeePlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class TabCompleteCMD extends Command{

	private CoreBungeePlugin plugin;

	public TabCompleteCMD(String name, String perm, CoreBungeePlugin coreBungeePlugin) {
		super(name, perm);
		this.plugin = coreBungeePlugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length <= 1) {
			sender.sendMessage(TextComponent.fromLegacyText("/addtabcomplete global/moderation commandeSansSlash"));
		}else if(args.length == 2){
			switch (args[0]) {
			case "global":
			{
				String path = "tabcomplete.global";
				List<String> list = plugin.getConfiguration().getStringList("tabcomplete.global");
				list.add(args[1]);
				plugin.getConfiguration().set(path, list);
				sender.sendMessage(TextComponent.fromLegacyText(args[1] + " ajouté au groupe " + args[0]));
				plugin.saveConfig();
				break;
			}
			case "moderation":
			{
				String path = "tabcomplete.moderation";
				List<String> list = plugin.getConfiguration().getStringList("tabcomplete.global");
				list.add(args[1]);
				plugin.getConfiguration().set(path, list);
				sender.sendMessage(TextComponent.fromLegacyText(args[1] + " ajouté au groupe " + args[0]));
				plugin.saveConfig();
				break;
			}
			default:
				break;
			}
		}
	}



}
