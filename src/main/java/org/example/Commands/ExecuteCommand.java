package org.example.Commands;

import org.example.Application;

import org.example.DBManager.Credits;
import org.example.DBManager.DBManager;
import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Exceptions.Unchecked.ArgsTooLongException;
import org.example.Exceptions.Unchecked.FileToSQLException;
import org.example.InputConverter;
import org.example.InputParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class ExecuteCommand extends Command {
    private String username;
    public ExecuteCommand(String[] args) {
        super(args);

    }

    @Override
    public boolean execute() throws DatabaseConnectionException {
        if (getArgs().length != 2) {
            System.out.println("Invalid number of arguments for execute command.");
            return false;
        }
        int commandId = Integer.parseInt(getArgs()[0]);
         this.username = getArgs()[1];

        DBManager dbManager = new DBManager<>(Credits.getConnectionDB());
        if (!isAdmin(username)) {
            System.out.println("Access denied. Only administrators can execute this command.");
            return false;
        }
        String sql = "SELECT command FROM audit_service WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(Credits.getJdbcUrl(), Credits.getUser(), Credits.getJPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, commandId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String command = resultSet.getString("command");
                    executeCommand(command);
                    return true;
                } else {
                    System.out.println("No command found with ID: " + commandId);
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing command: " + e.getMessage());
            return false;
        }
    }

    public List<String> getAllowedTypeUser() {
        List<String> users = new ArrayList<>();
        users.add("REGULAR");
        users.add("ADMIN");
        return users;
    }

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


    private void executeCommand(String command) throws DatabaseConnectionException {
        Application.CommandFormat commandFormat = getInputCommand(command);

        Invoker invoker = new Invoker();
        Command mappedCommand;
        try {
            mappedCommand = InputConverter.mapCommand(commandFormat);
        } catch (FileToSQLException e) {
            System.out.println("Failed to map command: " + e.getMessage());
            return;
        }

        invoker.setCommand(mappedCommand);
        boolean status;
        try {
            status = invoker.runCommand();
            if (status) {
                DBManager dbManager = new DBManager(Credits.getConnectionDB());
                dbManager.logCommand(Application.getuser().getUsername(), commandFormat.getOriginalCommand());
                System.out.println("The command was executed successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
    }
    public Application.CommandFormat getInputCommand(String commandString) {
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(commandString, " ");

            String commandName = "";
            if (stringTokenizer.hasMoreTokens()) {
                commandName = stringTokenizer.nextToken();
            }
            if (commandName.length() > 255) {
                throw new ArgsTooLongException();
            }
            String s = "";
            String originalCommand = commandName;

            if (stringTokenizer.hasMoreTokens()) {
                s = stringTokenizer.nextToken("");
                originalCommand += s;
            }
            ArrayList<String> args = InputParser.getArgs(s);
            for (String arg : args) {
                if (arg.length() > 254) {
                    throw new ArgsTooLongException();
                }
            }
            Application.CommandFormat commandFormat = new Application.CommandFormat(commandName, args.toArray(new String[0]));
            commandFormat.setOriginalCommand(originalCommand);
            return commandFormat;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Application.CommandFormat();
    }
}