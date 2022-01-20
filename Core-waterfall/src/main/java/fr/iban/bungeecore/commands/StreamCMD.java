package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.data.Account;
import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Option;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StreamCMD extends Command {

    private final CoreBungeePlugin plugin;
    private Map<UUID, Long> cooldowns = new HashMap<>();

    public StreamCMD(String name, String permission, CoreBungeePlugin plugin) {
        super(name, permission);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(args.length != 1) {
                p.sendMessage("§cSyntaxe: /stream <link>");
            }

            if(args.length == 1) {

                if(!cooldowns.isEmpty() && cooldowns.containsKey(p.getUniqueId())) {
                    int secondsLeft = (int) (Integer.parseInt(plugin.getConfiguration().get("cooldowns.stream").toString()) * 60 * 1000 - (System.currentTimeMillis() - cooldowns.get(p.getUniqueId()))) / 1000;
                    if(secondsLeft <= 0) {
                        cooldowns.remove(p.getUniqueId());
                    }else if(!p.hasPermission("spartacube.stream.bypasscooldown")){
                        p.sendMessage("§cVous devez attendre " + secondsLeft +" secondes avant de réutiliser cette commande !");
                        return;
                    }
                }

                String link = args[0];
                for (ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()) {
                    AccountProvider ap = new AccountProvider(pl.getUniqueId());
                    Account account = ap.getAccount();
                    if (account.getOption(Option.STREAM)) {
                        pl.sendMessage("§e§l          ≫ Annonce Live ≪  ");
                        pl.sendMessage("");
                        pl.sendMessage("§7§l► §5§l" + p.getName() + "§f§lVient de lancer son live !");
                        pl.sendMessage("");
                        pl.sendMessage("§7§l► §c§lLien : §f§e" + link);
                        pl.sendMessage("");
                    }
                }

                if(!p.hasPermission("spartacube.stream.bypasscooldown")){
                    cooldowns.put(p.getUniqueId(), System.currentTimeMillis());
                }
            }
        }
    }
}
