package org.example.Commands;

import org.example.Application;
import org.example.Exceptions.Unchecked.FileToSQLException;
import org.example.Users.User;
import org.example.DBManager.DBManager;
import org.example.DBManager.Credits;
import org.example.Exceptions.Checked.DatabaseConnectionException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginCommand extends Command {

    public LoginCommand(String[] args) {
        super(args);
    }

    @Override
    public boolean execute() {
        String[] args = getArgs();
        if (args.length != 2) {
            throw new FileToSQLException("Invalid number of arguments for login command.");
        }

        String username = args[0];
        String password = args[1];

        try {
            if (authenticateUser(username, password)) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Login failed. Invalid username or password.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during login: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> getAllowedTypeUser() {
        List<String> users = new ArrayList<>();
        users.add("ANONYMOUS");
        return users;
    }

    private boolean authenticateUser(String username, String password) throws SQLException, DatabaseConnectionException {
        DBManager<User> dbManager = new DBManager<>(Credits.getConnectionDB());
        User user = dbManager.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            Application.setuser(user);
            return true;
        } else {
            return false;
        }
    }
}
