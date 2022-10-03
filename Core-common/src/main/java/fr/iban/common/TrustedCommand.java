package fr.iban.common;

import fr.iban.common.data.sql.DbAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TrustedCommand {

    private String command;
    private String senderType;
    private String context;

    public TrustedCommand(String command, String senderType, String context) {
        this.command = command;
        this.senderType = senderType;
        this.context = context;
    }

    public String getCommand() {
        return command;
    }

    public String getSenderType() {
        return senderType;
    }

    public String getContext() {
        return context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrustedCommand that = (TrustedCommand) o;
        return Objects.equals(command, that.command) && Objects.equals(senderType, that.senderType) && Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, senderType, context);
    }
}
