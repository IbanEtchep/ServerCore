package fr.iban.bungeecore.commands;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.data.Account;
import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Option;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StreamCMD extends Command {

    private final CoreBungeePlugin plugin;

    public StreamCMD(String name, String permission, CoreBungeePlugin plugin) {
        super(name, permission);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(args.length == 0 || args.length > 1) {
                p.sendMessage("§cSyntaxe: /stream <link>");
            }

            if(args.length == 1) {
                String link = args[0];
                p.sendMessage("§aAnnonce effectuée");
                for(ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()) {
                    AccountProvider ap = new AccountProvider(pl.getUniqueId());
                    Account account = ap.getAccount();
                    if(account.getOption(Option.STREAM)) {
                        pl.sendMessage("§e§l          ≫ Annonce Live ≪  ");
                        pl.sendMessage("");
                        pl.sendMessage("§7§l► §5§l" + p.getName() + "§f§lVient de lancer son live !");
                        pl.sendMessage("");
                        pl.sendMessage("§7§l► §c§lLien : §f§e" + link);
                        pl.sendMessage("");
                    }
                }
            }
        }
    }
}
