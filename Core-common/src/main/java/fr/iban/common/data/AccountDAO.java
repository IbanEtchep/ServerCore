package fr.iban.common.data;

import fr.iban.common.data.sql.DbAccess;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

public class AccountDAO {

    public Account getAccount(UUID uuid) {
        return getAccountFromDB(uuid);
    }

    private Account getAccountFromDB(UUID uuid) {
        DataSource ds = DbAccess.getDataSource();
        Account account = new Account(uuid);

        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM sc_players WHERE uuid = ?")) {
                ps.setString(1, uuid.toString());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        long lastseen = rs.getLong("lastseen");
                        account.setLastSeen(lastseen);

                        short maxclaims = rs.getShort("maxclaims");
                        account.setMaxClaims(maxclaims);
                    }
                }
            }
            account.setOptions(getOptionsFromDB(connection, uuid));
            account.setIgnoredPlayers(getIgnoredPlayersFromDB(connection, uuid));
            account.setBlackListedAnnounces(getBlackListedAnnouncesFromDB(connection, uuid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void sendAccountToDB(Account account) {
        UUID uuid = account.getUUID();
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO sc_players (uuid, name, lastseen, maxclaims) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=VALUES(name), lastseen=VALUES(lastseen), maxclaims=VALUES(maxclaims)")) {
                ps.setString(1, uuid.toString());
                ps.setString(2, (account.getName() == null ? "NonDefini" : account.getName()));
                ps.setLong(3, account.getLastSeen());
                ps.setInt(4, account.getMaxClaims());
                ps.executeUpdate();
            }
            saveOptionsToDB(account.getOptions(), connection, uuid);
            deleteOptionsFromDB(account.getOptions(), connection, uuid);
            saveBlackListedAnnouncesToDB(account.getBlackListedAnnounces(), connection, uuid);
            if (account.getIp() != null) {
                saveLoginToDb(account, connection, uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Option, Boolean> getOptionsFromDB(Connection connection, UUID uuid) throws SQLException {
        Map<Option, Boolean> options = new HashMap<>();
        try (PreparedStatement ps =
                     connection.prepareStatement(
                             "SELECT idOption "
                                     + "FROM sc_players, sc_options "
                                     + "WHERE uuid = ? "
                                     + "AND sc_players.id=sc_options.id")) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Option option = Option.valueOf(rs.getString("idOption"));
                    options.put(option, !option.getDefaultValue());
                }
            }
        }
        return options;
    }

    public Set<Integer> getBlackListedAnnouncesFromDB(Connection connection, UUID uuid) throws SQLException {
        Set<Integer> announces = new HashSet<>();
		String sql = "SELECT idAnnonce FROM sc_players, sc_annonces_blacklist WHERE uuid = ? AND sc_players.id=sc_annonces_blacklist.id;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    announces.add(rs.getInt("idAnnonce"));
                }
            }
        }
        return announces;
    }

    public Set<UUID> getIgnoredPlayersFromDB(Connection connection, UUID uuid) throws SQLException {
        Set<UUID> ignored = new HashSet<>();
		String sql = "SELECT uuidPlayer FROM sc_players, sc_ignored_players WHERE uuid = ? AND sc_players.id=sc_ignored_players.id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ignored.add(UUID.fromString(rs.getString("uuidPlayer")));
                }
            }
        }
        return ignored;
    }

    public void saveIgnoredPlayersToDB(Set<UUID> ignored, Connection connection, UUID uuid) throws SQLException {
        String INSERT_SQL = "INSERT INTO sc_ignored_players(id, uuidPlayer) VALUES ((SELECT id FROM sc_players WHERE uuid=?), ?) ON DUPLICATE KEY UPDATE id=VALUES(id);";
        for (UUID uuidP : ignored) {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
            ps.setString(1, uuid.toString());
            ps.setString(2, uuidP.toString());
            ps.executeUpdate();
            ps.close();
        }
    }

    public void saveOptionsToDB(Map<Option, Boolean> options, Connection connection, UUID uuid) throws SQLException {
        String INSERT_SQL = "INSERT INTO sc_options(id, idOption) VALUES ((SELECT id FROM sc_players WHERE uuid=?), ?) ON DUPLICATE KEY UPDATE id=VALUES(id);";
        for (Entry<Option, Boolean> entry : options.entrySet()) {
            Option option = entry.getKey();
            if (option.getDefaultValue() == entry.getValue().booleanValue()) continue;
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
            ps.setString(1, uuid.toString());
            ps.setString(2, option.toString());
            ps.executeUpdate();
            ps.close();
        }
    }

    public void deleteOptionsFromDB(Map<Option, Boolean> options, Connection connection, UUID uuid) throws SQLException {
        String DELETE_SQL = "DELETE FROM sc_options WHERE id = (SELECT id FROM sc_players WHERE uuid=?) AND idOption = ?;";
        for (Entry<Option, Boolean> entry : options.entrySet()) {
            Option option = entry.getKey();
            if (option.getDefaultValue() != entry.getValue().booleanValue()) continue;
            PreparedStatement ps = connection.prepareStatement(DELETE_SQL);
            ps.setString(1, uuid.toString());
            ps.setString(2, option.toString());
            ps.executeUpdate();
            ps.close();
        }
    }

    public void saveBlackListedAnnouncesToDB(Set<Integer> blacklist, Connection connection, UUID uuid) throws SQLException {
        String INSERT_SQL = "INSERT INTO sc_annonces_blacklist(id, idAnnonce) VALUES ((SELECT id FROM sc_players WHERE uuid=?), ?) ON DUPLICATE KEY UPDATE id=VALUES(id);";
        for (int idA : blacklist) {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
            ps.setString(1, uuid.toString());
            ps.setInt(2, idA);
            ps.executeUpdate();
            ps.close();
        }
    }

    public void saveLoginToDb(Account account, Connection connection, UUID uuid) throws SQLException {
        String INSERT_SQL = "INSERT INTO sc_logins(id, date_time, ip) VALUES ((SELECT id FROM sc_players WHERE uuid=?), ?, ?) ON DUPLICATE KEY UPDATE id=VALUES(id);";
        PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
        ps.setString(1, uuid.toString());
        ps.setTimestamp(2, new Timestamp(account.getLastSeen()));
        ps.setString(3, account.getIp());
        ps.executeUpdate();
        ps.close();
    }

}
