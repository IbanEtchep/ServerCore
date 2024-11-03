package fr.iban.common.manager;

import fr.iban.common.TrustedUser;
import fr.iban.common.data.sql.DbAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class TrustedUserManager {

    protected final List<TrustedUser> trustedUsers = new ArrayList<>();

    public void loadTrustedUsers() {
        trustedUsers.clear();
        String sql = "SELECT uuid, ip FROM sc_trusted_players;";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        UUID uuid = UUID.fromString(rs.getString("uuid"));
                        String ip = rs.getString("ip");
                        trustedUsers.add(new TrustedUser(uuid, ip));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void trustUser(TrustedUser user) {
        String sql = "INSERT INTO sc_trusted_players (uuid, ip) VALUES (?, ?);";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, user.getUuid().toString());
                ps.setString(2, user.getIp());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadTrustedUsers();
    }
}
