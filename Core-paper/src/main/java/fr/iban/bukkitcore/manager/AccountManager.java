package fr.iban.bukkitcore.manager;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.data.Account;
import fr.iban.common.data.AccountDAO;
import fr.iban.common.messaging.CoreChannel;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountManager {

    private final CoreBukkitPlugin plugin;
    private final Map<UUID, Account> accounts = new ConcurrentHashMap<>();

    public AccountManager(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    public Account getAccount(UUID uuid) {
        return accounts.get(uuid);
    }

    public void reloadAccount(UUID uuid) {
        accounts.put(uuid, new AccountDAO().getAccount(uuid));
    }

    public void loadAccount(UUID uuid) {
        Account account = new AccountDAO().getAccount(uuid);
        accounts.put(uuid, account);
    }

    public void saveAccount(Account account) {
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.sendAccountToDB(account);
        plugin.getMessagingManager().sendMessage(CoreChannel.SYNC_ACCOUNT_CHANNEL, account.getUUID().toString());
    }

    public void saveAccountAsync(Account account) {
        plugin.getScheduler().runAsync(task -> saveAccount(account));
    }
}
