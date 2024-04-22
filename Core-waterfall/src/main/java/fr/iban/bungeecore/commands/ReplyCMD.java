package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplyCMD extends Command implements TabExecutor {

    private static final HashMap<ProxiedPlayer, ProxiedPlayer> replies = new HashMap<>();
    private final CoreBungeePlugin plugin;

    public ReplyCMD(String name, String permission, String name2, CoreBungeePlugin plugin) {
        super(name, permission, name2);
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0)
            sender.sendMessage(TextComponent.fromLegacyText("§e/r [Message]"));
        if (args.length > 0)
            if (sender instanceof ProxiedPlayer player) {
                if (getReplies().containsKey(player)) {
                    ProxiedPlayer target = getReplies().get(player);
                    if (target == null) {
                        player.sendMessage(TextComponent.fromLegacyText("§c" + (getReplies().get(player)).getName() + " est hors-ligne!"));
                        return;
                    }
                    StringBuilder sb = new StringBuilder("");
                    for (String arg : args) sb.append(arg).append(" ");
                    String msg = sb.toString();

                    plugin.getChatManager().sendMessage(player, target, msg);
                } else {
                    sender.sendMessage(TextComponent.fromLegacyText("§cTu ne peux pas répondre, car personne ne t'a écrit."));
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

    public static Map<ProxiedPlayer, ProxiedPlayer> getReplies() {
        return replies;
    }
}
