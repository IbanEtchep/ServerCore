package fr.iban.common.manager;

import fr.iban.common.data.sql.DbAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class GlobalLoggerManager {

    private static ExecutorService executor;

    public static void saveLog(String server, String message) {
        executor.execute(() -> {
            String sql = "INSERT INTO sc_logs (server, message) VALUES (?, ?);";

            try (Connection connection = DbAccess.getDataSource().getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, server);
                    ps.setString(2, message);
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void initLogger() {
        executor = Executors.newSingleThreadExecutor();
    }

    public static void shutdownLogger() {
        executor.shutdown();
    }

    public static class ConsoleLogHandler extends Handler {

        private final String serverName;

        public ConsoleLogHandler(String serverName) {
            this.serverName = serverName;
        }

        @Override
        public void publish(LogRecord record) {
            if (record.getLevel().intValue() <= Level.FINE.intValue()) {
                return; // Ignorer les niveaux de gravité FINE, FINER et FINEST
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
            String time = sdf.format(new Date(record.getMillis()));
            String level = record.getLevel().getName();

            String prefix = "[" + time + " " + level + "]: ";

            String formattedMessage = MessageFormat.format(record.getMessage(), record.getParameters());
            String strippedMessage = formattedMessage.replaceAll("§.", "");

            saveLog(serverName, prefix + strippedMessage);
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }
    }
}
