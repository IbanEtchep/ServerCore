package fr.iban.bungeecore.runnables;

import fr.iban.common.data.AccountProvider;
import net.md_5.bungee.api.ProxyServer;

public class SaveAccounts implements Runnable {

	@Override
	public void run() {
		ProxyServer.getInstance().getPlayers().forEach(p -> {
			AccountProvider ap = new AccountProvider(p.getUniqueId());
			ap.sendAccountToDB(ap.getAccount());
		});
	}

}
