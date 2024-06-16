package fr.iban.survivalcore.listeners;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.manager.AccountManager;
import fr.iban.common.data.Account;
import fr.iban.common.data.Option;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.UUID;

public class DamageListeners implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDamage(EntityDamageEvent e) {
		if(e instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
			if(e.getEntity() instanceof Player damaged) {
                Player damager = getPlayerDamager(event);
				if(damager != null && !canPVP(damaged.getUniqueId(), damager.getUniqueId())) {
					e.setCancelled(true);
				}
			}
		}
	}

	private Player getPlayerDamager(EntityDamageByEntityEvent event) {
		Player player = null;
		if(event.getCause() == DamageCause.PROJECTILE && event.getDamager() instanceof Projectile projectile) {
            if(projectile.getShooter() instanceof Player) {
				player = (Player)projectile.getShooter();
			}
		}
		if(event.getDamager() instanceof Player) {
			player = (Player) event.getDamager();
		}
		return player;
	}

	private boolean canPVP(UUID p1, UUID p2) {
		AccountManager accountManager = CoreBukkitPlugin.getInstance().getAccountManager();
		Account account1 = accountManager.getAccount(p1);
		Account account2 = accountManager.getAccount(p2);
		return Boolean.TRUE.equals(account1.getOption(Option.PVP)) && Boolean.TRUE.equals(account2.getOption(Option.PVP));
	}
}