package fr.iban.bungeecore.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.iban.bungeecore.CoreBungeePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SudoCMD extends Command implements TabExecutor {
	
	public SudoCMD(String name, String permission) {
		super(name, permission);
	}

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("bungeesudo.use")) {
            if (args.length == 0) {
                sender.sendMessage(new ComponentBuilder("§cVeuillez utilisez: ").append("§c/sudo§c message du joueur").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder("Copiez!").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "§c/sudo§c ")).color(ChatColor.GOLD).create()))).create());
            } else if (args.length == 1) {
                sender.sendMessage(new ComponentBuilder("§cVeuillez utilisez: ").append("§c/sudo§c " + args[0] + "§c message").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder("Copiez!").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "§c/sudo§c " + args[0] + "§c ")).color(ChatColor.GOLD).create()))).create());
            } else {
                ProxiedPlayer player = CoreBungeePlugin.getInstance().getProxy().getPlayer(args[0]);
                if (player.isConnected()) {
                    if (player.hasPermission("sudo.bypass") && !sender.hasPermission("sudo.bypassbypass")) {
                        sender.sendMessage(new ComponentBuilder("§cDésolé, ce joueura a la permission de bypass. Vous ne pouvez pas le sudo.").color(ChatColor.RED).create());
                    } else {
                        StringBuilder message = new StringBuilder();

                        for (int i = 1; i != args.length; i++) {
                            if (message.toString().equals("")) {
                                message.append(args[i]);
                            } else {
                                message.append(" ").append(args[i]);
                            }
                        }

                        player.chat(message.toString());
                        String sudo = "&c" + sender.getName() + " &6a utilisé le sudo pour faire dire ou utiliser " + "&8" + message.toString() + " &6à " + "&c" + player.getName();
                        System.out.println((ChatColor.translateAlternateColorCodes('&', sudo)));
                    }
                } else {
                    sender.sendMessage(new ComponentBuilder("§cDésolé, ce joueur n'est pas connecté.").color(ChatColor.RED).create());
                }
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completion = new ArrayList<>();

        if (sender.hasPermission("bungeesudo.use")) {
            List<String> players = new ArrayList<>();

            for (ProxiedPlayer player : CoreBungeePlugin.getInstance().getProxy().getPlayers()) {
                players.add(player.getName());
            }

            if (args.length == 1) {
                for (String string : players)
                    if (string.toLowerCase().startsWith(args[0].toLowerCase())) completion.add(string);
            }

            Collections.sort(players);
        }

        return completion;
    }
}
