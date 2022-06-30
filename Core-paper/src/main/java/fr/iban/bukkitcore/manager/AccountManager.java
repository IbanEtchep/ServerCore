package fr.iban.bukkitcore.manager;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.common.data.Account;
import fr.iban.common.data.AccountDAO;
import org.bukkit.Bukkit;

import java.util.UUID;

public class AccountManager {

    private final CoreBukkitPlugin plugin;
    private final LoadingCache<UUID, Account> accounts = Caffeine.newBuilder().build(uuid -> new AccountDAO().getAccount(uuid));

    public AccountManager(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    public Account getAccount(UUID uuid) {
        return accounts.get(uuid);
    }

    public void reloadAccount(UUID uuid) {
        accounts.invalidate(uuid);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> accounts.get(uuid));
    }

    public void saveAccount(Account account) {
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.sendAccountToDB(account);
        plugin.getMessagingManager().sendMessage(CoreBukkitPlugin.SYNC_ACCOUNT_CHANNEL, account.getUUID().toString());
    }

    public void saveAccountAsync(Account account) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> saveAccount(account));
    }
}
