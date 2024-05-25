package org.example.Commands;

import org.example.DBManager.Credits;
import org.example.Exceptions.Checked.DatabaseConnectionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreateTableCommand extends Command {

    public CreateTableCommand(String[] args) {
        super(args);
    }

    @Override
    public boolean execute() throws DatabaseConnectionException {
        if (getArgs().length != 3) {
            System.out.println("Invalid number of arguments for create table command.");
            return false;
        }

        String tableName = getArgs()[1];
        int numberOfColumns;
        try {
            numberOfColumns = Integer.parseInt(getArgs()[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of columns: " + getArgs()[2]);
            return false;
        }

        return createTable(tableName, numberOfColumns);
    }

    @Override
    public List<String> getAllowedTypeUser() {
        List<String> users = new ArrayList<>();
        users.add("ADMIN");
        return users;
    }

    private boolean createTable(String tableName, int numberOfColumns) throws DatabaseConnectionException {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (numeColoana VARCHAR(255) NOT NULL, tipColoana VARCHAR(255) NOT NULL);";
        Boolean ok = false;
        try (Connection connection = DriverManager.getConnection(Credits.getJdbcUrl(), Credits.getUser(), Credits.getJPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute(sql);
            ok = true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ok;
    }
}
