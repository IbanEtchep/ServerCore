package fr.iban.common.manager;

import fr.iban.common.TrustedCommand;
import fr.iban.common.data.sql.DbAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TrustedCommandsManager {

    private final List<TrustedCommand> trustedCommands = new ArrayList<>();

    public void loadTrustedCommands() {
        trustedCommands.clear();
        String sql = "SELECT command, senderType, `context` FROM sc_trusted_commands;";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String command = rs.getString("command");
                        String senderType = rs.getString("senderType");
                        String context = rs.getString("context");
                        trustedCommands.add(new TrustedCommand(command, senderType, context));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTrustedCommand(TrustedCommand command) {
        String sql = "INSERT INTO sc_trusted_commands (command, senderType, `context`) VALUES (?, ? ,?);";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, command.getCommand());
                ps.setString(2, command.getSenderType());
                ps.setString(3, command.getContext());
                ps.executeUpdate();
                trustedCommands.add(command);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTrustedCommand(TrustedCommand command) {
        String sql = "DELETE FROM sc_trusted_commands WHERE command=? AND senderType=? AND `context`=?;";
        try (Connection connection = DbAccess.getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, command.getCommand());
                ps.setString(2, command.getSenderType());
                ps.setString(3, command.getContext());
                ps.executeUpdate();
                trustedCommands.remove(command);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<String> getBukkitPlayerCommands() {
        return trustedCommands.stream()
                .filter(command -> command.getSenderType().equals("player"))
                .filter(command -> command.getContext().equals("global") || command.getContext().equals("bukkit"))
                .map(TrustedCommand::getCommand)
                .toList();
    }

    public List<String> getProxyPlayerCommands() {
        return trustedCommands.stream()
                .filter(command -> command.getSenderType().equals("player"))
                .filter(command -> command.getContext().equals("global") || command.getContext().equals("proxy"))
                .map(TrustedCommand::getCommand)
                .toList();
    }

    public List<String> getBukkitStaffCommands() {
        return trustedCommands.stream()
                .filter(command -> command.getSenderType().equals("staff"))
                .filter(command -> command.getContext().equals("global") || command.getContext().equals("bukkit"))
                .map(TrustedCommand::getCommand)
                .toList();
    }

    public List<String> getProxyStaffCommands() {
        return trustedCommands.stream()
                .filter(command -> command.getSenderType().equals("staff"))
                .filter(command -> command.getContext().equals("global") || command.getContext().equals("proxy"))
                .map(TrustedCommand::getCommand)
                .toList();
    }

    public List<String> getTrustedBukkitCommands() {
        return trustedCommands.stream()
                .filter(command -> command.getContext().equals("global") || command.getContext().equals("bukkit"))
                .map(TrustedCommand::getCommand)
                .toList();
    }

    public List<TrustedCommand> getTrustedCommands() {
        return trustedCommands;
    }
}
