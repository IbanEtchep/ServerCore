package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class MessageCMD extends Command implements TabExecutor {

    private CoreBungeePlugin plugin;

    public MessageCMD(String name, String permission, CoreBungeePlugin plugin, String name2, String name3, String name4, String name5, String name6) {
        super(name, permission, name2, name3, name4, name5, name6);
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0)
            sender.sendMessage(TextComponent.fromLegacyText("§e/msg [Joueur] [Message]"));
        if (args.length > 0)
            if (sender instanceof ProxiedPlayer player) {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(TextComponent.fromLegacyText("§c" + args[0] + " est hors-ligne !"));
                    return;
                }

                if (args.length > 1) {
                    StringBuilder sb = new StringBuilder("");
                    for (int i = 1; i < args.length; i++)
                        sb.append(args[i]).append(" ");
                    String msg = sb.toString();

                    plugin.getChatManager().sendMessage(player, target, msg);
                } else {
                    sender.sendMessage(TextComponent.fromLegacyText("§cVeuillez entrez un message."));
                }
            }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> playernames = new ArrayList<>();
        if (args.length == 1) {
            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (!sender.getName().equals(p.getName()) && p.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                    playernames.add(p.getName());
            }
        }
        return playernames;
    }

}
