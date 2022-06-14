package fr.iban.common.messaging;

import fr.iban.common.data.sql.DbAccess;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class SqlMessenger extends AbstractMessenger {

    private DataSource ds = DbAccess.getDataSource();
    private long lastId;

    public SqlMessenger() {
        CompletableFuture.runAsync(this::init).thenRun(() -> {
           startPollTask();
           startCleanUpTask();
        });
    }

    public abstract void startPollTask();

    public abstract void startCleanUpTask();

    public void init() {
        try (Connection connection = ds.getConnection()) {

            String createStatement = "CREATE TABLE IF NOT EXISTS sc_messenger(" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "createdAt TIMESTAMP NOT NULL," +
                    "serverFrom VARCHAR(255)," +
                    "channel VARCHAR(255)," +
                    "msg TEXT NOT NULL" +
                    ");";

            try (PreparedStatement preparedStatement = connection.prepareStatement(createStatement)) {
                preparedStatement.executeUpdate();
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT MAX(`id`) as latest FROM sc_messenger;")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        this.lastId = rs.getLong("latest");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(Message message) {
        String insertStatement = "INSERT INTO sc_messenger (createdAt, serverFrom, channel, msg) VALUES (NOW(), ?, ?, ?);";
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertStatement)) {
                ps.setString(1, message.getServerFrom());
                ps.setString(2, message.getChannel());
                ps.setString(3, message.getMessage());
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readNewMessages() {
        String selectStatement = "SELECT id, serverFrom, channel, msg FROM sc_messenger WHERE id > ? AND (NOW() - createdAt < 30) ";
        List<Message> messages = new ArrayList<>();

        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectStatement)) {
                ps.setLong(1, this.lastId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        long id = rs.getLong("id");
                        this.lastId = Math.max(this.lastId, id);

                        String channel = rs.getString("channel");
                        String serverFrom = rs.getString("serverFrom");
                        String message = rs.getString("msg");
                        messageConsumer.accept(new Message(channel, serverFrom, message));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cleanOldMessages() {
        String deleteStatement = "DELETE FROM sc_messenger WHERE (NOW() - createdAt > 60)";
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(deleteStatement)) {
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
