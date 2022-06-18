package fr.iban.survivalcore.utils;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import fr.iban.bukkitcore.rewards.RewardsDAO;
import fr.iban.common.data.sql.DbAccess;
import fr.iban.survivalcore.SurvivalCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HourlyReward {

    private SurvivalCorePlugin plugin;

    public HourlyReward(SurvivalCorePlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        String createStatement = "CREATE TABLE IF NOT EXISTS sc_hourly_rewards_time (" +
                "uuid VARCHAR(36) PRIMARY KEY , playtime INTEGER);";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(createStatement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("premium")) {
                    addPlayTime(player.getUniqueId(), 1);
                }
            }
        }, 1200L, 1200L);

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            getTimeBiggerThanOneHour().forEach(uuid -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    if (CoreBukkitPlugin.getInstance().getServerName().equals("Survie")) {
                        plugin.getEconomy().depositPlayer(player, 750);
                    } else {
                        player.sendMessage("§a§lLa récompense vous attend dans /recompenses.");
                        RewardsDAO.addRewardAsync(player.getUniqueId().toString(), "750§e⛃§r", "Survie", "eco give {player} " + 750);
                    }
                    removePlayTime(uuid, 60);
                }
            });
        }, 12000L, 12000L);
    }


    public List<UUID> getTimeBiggerThanOneHour() {
        String sql = "SELECT uuid FROM sc_hourly_rewards_time WHERE playtime > 60;";
        List<UUID> uuidList = new ArrayList<>();
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        UUID uuid = UUID.fromString(rs.getString("uuid"));
                        uuidList.add(uuid);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uuidList;
    }

    public void addPlayTime(UUID uuid, int minutes) {
        String sql = "INSERT INTO sc_hourly_rewards_time (uuid, playtime) VALUES (?, ?) ON DUPLICATE KEY UPDATE playtime=(playtime-VALUES(playtime));";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, uuid.toString());
                ps.setInt(2, minutes);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePlayTime(UUID uuid, int minutes) {
        String sql = "UPDATE sc_hourly_rewards_time SET playtime=(playtime-?) WHERE uuid=?;";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, minutes);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
