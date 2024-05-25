package org.example.Commands;

import org.example.DBManager.Credits;
import org.example.Exceptions.Checked.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertColumnCommand extends Command {

    public InsertColumnCommand(String[] args) {
        super(args);
    }

    @Override
    public boolean execute() throws DatabaseConnectionException {
        String[] args = getArgs();
        if (args.length != 4) {
            System.out.println("Invalid number of arguments for insert column command.");
            return false;
        }

        String tableName = args[1];
        String columnName = args[2];
        String typeColumn = args[3];

        String sql = "INSERT INTO " + tableName + "(numeColoana, tipColoana) VALUES ('" + columnName + "', '" + typeColumn+"');";

        try (Connection connection = DriverManager.getConnection(Credits.getJdbcUrl(), Credits.getUser(), Credits.getJPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            System.out.println("Column " + columnName + " of type " + typeColumn + " added to table " + tableName + " successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding column to table: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> getAllowedTypeUser() {
        return List.of("ADMIN");
    }
}
