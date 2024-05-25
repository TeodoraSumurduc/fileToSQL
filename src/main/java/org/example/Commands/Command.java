package org.example.Commands;

import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Exceptions.Checked.FileReadExceptions;
import org.example.Exceptions.Checked.FileWriteException;

import java.sql.SQLException;
import java.util.List;

/**
 * An abstract base class representing a command that can be executed.
 * Subclasses must implement the {@link #execute()} and {@link #getAllowedTypeUser()} methods.
 */
public abstract class Command {
    private String[] args;

    /**
     * Constructs a Command with the specified arguments.
     *
     * @param args the arguments for the command
     */
    public Command(String[] args) {
        this.args = args;
    }

    /**
     * Returns the arguments for the command.
     *
     * @return an array of arguments
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Executes the command.
     *
     * @return true if the command executed successfully, false otherwise
     * @throws SQLException if a database access error occurs
     * @throws FileReadExceptions if an error occurs while reading a file
     * @throws FileWriteException if an error occurs while writing to a file
     */
    public abstract boolean execute() throws SQLException, FileReadExceptions, FileWriteException;

    /**
     * Gets the list of user types allowed to execute this command.
     *
     * @return a list of allowed user types
     */
    public abstract List<String> getAllowedTypeUser();
}
