package fr.iban.common.manager;

import fr.iban.common.data.sql.DbAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PlayerManager {

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

    public CompletableFuture<UUID> getProxiedPlayerUUID(String name){
        return CompletableFuture.supplyAsync(() -> {
            for (Map.Entry<UUID, String> entry : getProxyPlayerNamesFromDB().entrySet()) {
                if(entry.getValue().equals(name)) {
                    return entry.getKey();
                }
            }
            return null;
        });
    }

    public void addOnlinePlayerToDB(UUID uuid) {
        String sql = "INSERT INTO sc_online_players (player_id) VALUES ((SELECT id FROM sc_players WHERE uuid=?));";
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
}
