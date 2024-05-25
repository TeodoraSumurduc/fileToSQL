package org.example.Commands;

import org.example.Application;
import org.example.DBManager.Credits;
import org.example.DBManager.DBManager;
import org.example.Exceptions.Checked.DatabaseConnectionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PromoteCommand extends Command {

    public PromoteCommand(String[] args) {
        super(args);
    }

    @Override
    public boolean execute() throws DatabaseConnectionException {
        if (getArgs().length != 1) {
            System.out.println("Invalid number of arguments for promote command.");
            return false;
        }

        String username = getArgs()[0];

        if (!isAdmin(Application.getUser().getUsername())) {
            System.out.println("Access denied. Only administrators can promote users.");
            return false;
        }

        return promoteUser(username);
    }

    @Override
    public List<String> getAllowedTypeUser() {
        List<String> users = new ArrayList<>();
        users.add("ADMIN");
        return users;
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
        return isAdmin;
    }
    /**
     * Promovează un utilizator la rolul de administrator în baza de date.
     *
     * @param username Numele utilizatorului care urmează să fie promovat.
     * @return {@code true} dacă promovarea a fost realizată cu succes, {@code false} altfel.
     */
    private boolean promoteUser(String username) {
        String sql = "UPDATE users SET type = 'ADMIN' WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(Credits.getJdbcUrl(), Credits.getUser(), Credits.getJPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User " + username + " has been promoted to ADMIN.");
                return true;
            } else {
                System.out.println("User " + username + " not found.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error promoting user: " + e.getMessage());
            return false;
        }
    }
}
