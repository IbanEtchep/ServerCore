package fr.iban.bungeecore.manager;

import fr.iban.bungeecore.CoreBungeePlugin;
import fr.iban.common.data.Account;
import fr.iban.common.data.AccountDAO;
import fr.iban.common.messaging.CoreChannel;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountManager {

    private final CoreBungeePlugin plugin;
    private final Map<UUID, Account> accounts = new ConcurrentHashMap<>();

    public AccountManager(CoreBungeePlugin plugin) {
        this.plugin = plugin;
    }

    public Account getAccount(UUID uuid) {
        return accounts.get(uuid);
    }

    public void reloadAccount(UUID uuid) {
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            accounts.put(uuid, new AccountDAO().getAccount(uuid));
        });
    }

    public void saveAccount(Account account) {
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.sendAccountToDB(account);
        plugin.getMessagingManager().sendMessage(CoreChannel.SYNC_ACCOUNT_CHANNEL, account.getUUID().toString());
    }
}
