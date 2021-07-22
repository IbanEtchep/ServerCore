package fr.iban.bungeecore.commands;

import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Option;
import fr.iban.spartacube.data.Account;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TptoggleCMD extends Command {

	public TptoggleCMD(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer)sender;
				AccountProvider ap = new AccountProvider(player.getUniqueId());
				Account account = ap.getAccount();
					account.toggleOption(Option.TP);
					ap.sendAccountToRedis(account);
					if(account.getOption(Option.TP)) {
						player.sendMessage(new TextComponent("§aVos demande de téléportation sont maintenant ouvertes"));
					} else {
						player.sendMessage(new TextComponent("§cVos demande de téléportation sont maintenant fermées"));
					}
            }
		}
    }