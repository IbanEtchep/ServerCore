package fr.iban.bukkitcore.plan;

import com.djrapitops.plan.query.CommonQueries;
import com.djrapitops.plan.query.QueryService;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public record PlanQueryAccessor(QueryService queryService) {

    final static String GET_SERVER_NAME_SQL = "SELECT * FROM plan_servers WHERE `uuid`=?;";
    final static String GET_SESSION_TOTAL_WITHIN_DAY_LIMIT_SQL = "SELECT SUM(" + "session_end" + '-' + "session_start" + ") as playtime" +
            " FROM plan_sessions" +
            " WHERE server_uuid" + "=?" +
            " AND session_end" + ">=?" +
            " AND session_start" + "<=?";

    public PlanQueryAccessor(QueryService queryService) {
        this.queryService = queryService;

        ensureDBSchemaMatch();
    }

    private void ensureDBSchemaMatch() {
        CommonQueries queries = queryService.getCommonQueries();
        if (
                !queries.doesDBHaveTable("plan_sessions")
                        || !queries.doesDBHaveTableColumn("plan_sessions", "uuid")
        ) {
            throw new IllegalStateException("Different table schema");
        }
    }

    public HashMap<String, Long> getPlayTimes() {
        Set<UUID> UUIDList = queryService.getCommonQueries().fetchServerUUIDs();
        final HashMap<String, Long> serverPlayTimes = new HashMap<>();
        for (UUID serverUUID : UUIDList) {
            final String serverName = queryService.query(GET_SERVER_NAME_SQL, preparedStatement -> {
                preparedStatement.setString(1, serverUUID.toString());
                try (ResultSet set = preparedStatement.executeQuery()) {
                    return set.next() ? set.getString("name") : null;
                }
            });
            // Don't bother fetching the proxy data (as it will always be zero)
            if (serverName.equalsIgnoreCase("proxy") || serverName.equalsIgnoreCase("bungee") || serverName.equalsIgnoreCase("waterfall") || serverName.equalsIgnoreCase("lavacord")) {
                continue;
            }
            long playtime = queryService.query(GET_SESSION_TOTAL_WITHIN_DAY_LIMIT_SQL, statement -> {
                statement.setString(1, serverUUID.toString());
                statement.setLong(2, (System.currentTimeMillis() - (TimeUnit.DAYS.toMillis(30))));
                statement.setLong(3, System.currentTimeMillis());
                try (ResultSet set = statement.executeQuery()) {
                    return set.next() ? set.getLong("playtime") : -1L;
                }
            });
            serverPlayTimes.put(serverName, playtime);
        }

        return serverPlayTimes;
    }
}
