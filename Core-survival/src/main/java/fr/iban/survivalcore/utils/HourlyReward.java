package fr.iban.survivalcore.utils;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.rewards.RewardsDAO;
import fr.iban.common.data.redis.RedisAccess;
import fr.iban.survivalcore.SurvivalCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.redisson.api.RMap;

public class HourlyReward {

    private SurvivalCorePlugin plugin;

    public HourlyReward(SurvivalCorePlugin plugin) {
        this.plugin = plugin;
    }

    public void startTask(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if(player.hasPermission("premium")){
                    int minutes = getOnlineTime(player);
                    if(minutes >= 60){
                        setOnlineTime(player,0);
                        player.sendMessage("§a§lVous avez reçu 750§e⛃§a§l pour avoir joué 1 heure.");
                        if(CoreBukkitPlugin.getInstance().getServerName().equals("Survie")) {
                            plugin.getEconomy().depositPlayer(player, 750);
                        }else {
                            player.sendMessage("§a§lLa récompense vous attend dans /recompenses.");
                            RewardsDAO.addRewardAsync(player.getUniqueId().toString(), "750§e⛃§r", "Survie", "eco give {player} " + 750);
                        }                    }else{
                        setOnlineTime(player, minutes+1);
                    }
                }
            }
        }, 1200L, 1200L);

    }

    public int getOnlineTime(Player player){
        RMap<String, Integer> premiumPlaytime = RedisAccess.getInstance().getRedissonClient().getMap("premiumPlaytime");
        return premiumPlaytime.getOrDefault(player.getUniqueId().toString(), 0);
    }

    public void setOnlineTime(Player player, int minutes){
        RedisAccess.getInstance().getRedissonClient().getMap("premiumPlaytime").fastPut(player.getUniqueId(), minutes);
    }

}
