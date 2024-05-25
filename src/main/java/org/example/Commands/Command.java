package org.example.Commands;

import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Exceptions.Checked.FileReadExceptions;
import org.example.Exceptions.Checked.FileWriteException;

import java.sql.SQLException;
import java.util.List;

public abstract class Command {
    private String[] args;

    public Command(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public abstract boolean execute() throws SQLException, FileReadExceptions, FileWriteException;

    public abstract List<String> getAllowedTypeUser();
}
