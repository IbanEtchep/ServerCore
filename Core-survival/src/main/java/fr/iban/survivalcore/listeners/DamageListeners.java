package fr.iban.survivalcore.listeners;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import fr.iban.common.data.AccountProvider;
import fr.iban.common.data.Option;

public class DamageListeners implements Listener {

	private LoadingCache<UUID, Boolean> pvpCache = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build(uuid -> new AccountProvider(uuid).getAccount().getOption(Option.PVP));

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDamage(EntityDamageEvent e) {
		if(e instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
			if(e.getEntity() instanceof Player) {
				Player damaged = (Player)e.getEntity();
				Player damager = getPlayerDamager(event);
				if(damager != null && !canPVP(damaged.getUniqueId(), damager.getUniqueId())) {
					e.setCancelled(true);
				}
			}
		}
	}

	private Player getPlayerDamager(EntityDamageByEntityEvent event) {
		Player player = null;
		if(event.getCause() == DamageCause.PROJECTILE && event.getDamager() instanceof Projectile) {
			Projectile projectile = (Projectile) event.getDamager();
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
		return pvpCache.get(p1).booleanValue() && pvpCache.get(p2).booleanValue();
	}
}