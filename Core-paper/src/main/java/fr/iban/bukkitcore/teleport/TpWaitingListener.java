package fr.iban.bukkitcore.teleport;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.redisson.api.listener.MessageListener;

import java.util.UUID;

public class TpWaitingListener implements MessageListener<String> {

    private CoreBukkitPlugin plugin;

    public TpWaitingListener(CoreBukkitPlugin coreBukkitPlugin) {
        this.plugin = coreBukkitPlugin;
    }

    @Override
    public void onMessage(CharSequence channel, String name) {
        Player player = Bukkit.getPlayer(name);
        if(player != null){
            UUID uuid = player.getUniqueId();
            plugin.getTpWaiting().add(uuid);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.getTpWaiting().remove(uuid);
            }, 200L);
        }
    }

}
