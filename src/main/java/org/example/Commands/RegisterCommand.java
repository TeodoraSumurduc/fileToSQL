package org.example.Commands;

import org.example.DBManager.Credits;
import org.example.DBManager.DBManager;
import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Exceptions.Unchecked.FileToSQLException;
import org.example.Users.User;
import org.example.Utils.UserTypes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterCommand extends Command {
    private String username;
    private String password;

    public RegisterCommand(String[] args) {
        super(args);
    }

    @Override
    public boolean execute() throws DatabaseConnectionException {
        String[] args = getArgs();
        if (args.length != 2) {
            throw new FileToSQLException("Invalid number of arguments for login command.");
        }

        String username = args[0];
        String password = args[1];

        User newUser = new User(username, password);
        DBManager dbManager = new DBManager(Credits.getConnectionDB());
        newUser.setType(UserTypes.valueOf("REGULAR"));

        try {
            if(dbManager.isUserExists(username,password))
                System.out.println("This user already exist.Please login with this user.");
            else
            {
                dbManager.insertIntoDB(newUser);
                System.out.println("User \"" + username + "\" registered successfully!");
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to register user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> getAllowedTypeUser() {
        List<String> users = new ArrayList<>();
        users.add("ANONYMOUS");
        return users;
    }
}
