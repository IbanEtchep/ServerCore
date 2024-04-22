package fr.iban.velocitycore.manager;

import fr.iban.common.data.Account;
import fr.iban.common.data.AccountDAO;
import fr.iban.common.messaging.CoreChannel;
import fr.iban.velocitycore.CoreVelocityPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountManager {

    private final CoreVelocityPlugin plugin;
    private final Map<UUID, Account> accounts = new HashMap<>();

    public AccountManager(CoreVelocityPlugin plugin) {
        this.plugin = plugin;
    }

    public Account getAccount(UUID uuid) {
        if(!accounts.containsKey(uuid)){
            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.getAccount(uuid);
            accounts.put(uuid, account);
        }

        return accounts.get(uuid);
    }

    public void reloadAccount(UUID uuid) {
        accounts.remove(uuid);
        accounts.put(uuid, new AccountDAO().getAccount(uuid));
    }

    public void saveAccount(Account account) {
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.sendAccountToDB(account);
        plugin.getMessagingManager().sendMessage(CoreChannel.SYNC_ACCOUNT_CHANNEL, account.getUUID().toString());
    }
}
