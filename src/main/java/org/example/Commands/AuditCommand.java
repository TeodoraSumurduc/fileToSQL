package org.example.Commands;

import org.example.Commands.Command;
import org.example.DBManager.Credits;
import org.example.Exceptions.Checked.DatabaseConnectionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuditCommand extends Command {

    /**
     * Constructs a new AuditCommand with the specified arguments.
     *
     * @param args the command arguments
     */
    public AuditCommand(String[] args) {
        super(args);
    }

    @Override
    public boolean execute() {
        if (getArgs().length != 1) {
            System.out.println("Invalid number of arguments for audit command.");
            return false;
        }

        String username = getArgs()[0];

        executeAudit(username);
        return true;
    }

    @Override
    public List<String> getAllowedTypeUser() {
        List<String> users = new ArrayList<>();
        users.add("REGULAR");
        users.add("ADMIN");
        return users;
    }
    /**
     * Executes an audit for a given username.
     * This method retrieves the command history for the specified user and prints it to the console.
     * Access to this method is restricted to administrators.
     *
     * @param currentUsername The username for which to execute the audit.
     */
    public void executeAudit(String currentUsername) {
        if (!isAdmin(currentUsername)) {
            System.out.println("Access denied. Only administrators can execute this command.");
        }

        List<String> commandHistory = getCommandHistory(currentUsername, 3, 5);
        if (commandHistory.isEmpty()) {
            System.out.println("No commands found for user: " + currentUsername);
        } else {
            for (String command : commandHistory) {
                System.out.println(command);
            }
        }
    }
    /**
     * Retrieves the command history for a specific user.
     * This method queries the database for the command history of the given user,
     * retrieves the commands and their timestamps, and returns them as a list of strings.
     *
     * @param username  The username for which to retrieve the command history.
     * @param page      The page number of the command history (pagination).
     * @param pageSize  The size of each page in the command history (pagination).
     * @return A list of strings representing the command history for the specified user.
     */
    public List<String> getCommandHistory(String username, int page, int pageSize) {
        List<String> commands = new ArrayList<>();

        String sql = "SELECT command, timestamp FROM audit_service WHERE nameUser = '" + username + "' ORDER BY timestamp DESC LIMIT " + pageSize ;
        try (Connection connection = DriverManager.getConnection(Credits.getJdbcUrl(),Credits.getUser(),Credits.getJPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.println(sql);
            statement.execute(sql);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String command = resultSet.getString("command");
                    String timestamp = resultSet.getString("timestamp");
                    commands.add(timestamp + " - " + command);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commands;
    }
    /**
     * Verifică dacă un utilizator este un administrator.
     * Această metodă interoghează baza de date pentru a determina dacă utilizatorul specificat este un administrator.
     *
     * @param username Numele utilizatorului pentru care să se verifice statutul de administrator.
     * @return {@code true} dacă utilizatorul este un administrator, {@code false} în caz contrar.
     */
    private boolean isAdmin(String username) {
        Boolean isAdmin = false;
        String sql = "SELECT type FROM users where username = '" + username + "' ;";
        try (Connection connection = DriverManager.getConnection(Credits.getJdbcUrl(),Credits.getUser(),Credits.getJPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute(sql);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String typeUser = resultSet.getString("type");
                    System.out.println(typeUser);
                    if(Objects.equals(typeUser, "ADMIN"))
                        isAdmin = true;
                    else isAdmin = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(isAdmin);
        return isAdmin;
    }
}
