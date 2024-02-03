package fr.iban.common.manager;

import fr.iban.common.data.sql.DbAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlayerManager {

    protected final List<UUID> vanishedPlayers = new ArrayList<>();

    public Map<UUID, String> getProxyPlayerNamesFromDB() {
        String sql = "SELECT uuid, name FROM sc_players P JOIN sc_online_players OP ON P.id=OP.player_id;";
        Map<UUID, String> proxyPlayers = new HashMap<>();
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        UUID uuid = UUID.fromString(rs.getString("uuid"));
                        String name = rs.getString("name");
                        proxyPlayers.put(uuid, name);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proxyPlayers;
    }

    public Map<String, UUID> getPlayerNamesFromDb() {
        String sql = "SELECT uuid, name FROM sc_players;";
        Map<String, UUID> proxyPlayers = new HashMap<>();
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        try {
                            UUID uuid = UUID.fromString(rs.getString("uuid"));
                            String name = rs.getString("name");
                            proxyPlayers.put(name, uuid);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Core : UUID invalide :" + rs.getString("uuid"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proxyPlayers;
    }

    public void addOnlinePlayerToDB(UUID uuid) {
        String sql = "INSERT INTO sc_online_players (player_id) VALUES ((SELECT id FROM sc_players WHERE uuid=?)) ON DUPLICATE KEY UPDATE player_id=player_id;";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeOnlinePlayerFromDB(UUID uuid) {
        String sql = "DELETE FROM sc_online_players WHERE player_id=(SELECT id FROM sc_players WHERE uuid=?);";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearOnlinePlayersFromDB() {
        String sql = "DELETE FROM sc_online_players;";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isVanished(UUID uuid) {
        return vanishedPlayers.contains(uuid);
    }

    public void setVanished(UUID uuid, boolean newValue) {
        if (newValue) {
            vanishedPlayers.add(uuid);
        } else {
            vanishedPlayers.remove(uuid);
        }
    }
}
