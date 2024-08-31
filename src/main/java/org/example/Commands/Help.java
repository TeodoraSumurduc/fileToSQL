package org.example.Commands;

import org.example.Commands.Command;
import org.example.Utils.UserTypes;

import java.util.ArrayList;
import java.util.List;

public class Help extends Command {
    private static final String helpAnonymous =
            """
            Commands:
            |--- help : Prints all the available commands for the user of the current session;
            |--- quit : Stop the app running;
            |--- register "username" "password" : Register a new user and be authenticated as him;
            |--- login "username" "password" : Login to your account.
            """;

    private static final String helpRegular =
            """
            Commands:
            |--- help : Prints all the available commands for the user of the current session;
            |--- quit : Stop the app running;
            |--- logout : Logout from your account;
            |--- load "fileName" "tableName" "statementType" : Load a CSV/JSON file and generate SQL statements;
            |--- audit "username"  : Display command history for a user;
            |--- execute "commandId" "username" : Execute a specific SQL command from history.
            """;

    private static final String helpAdmin =
            helpRegular +
                    """
                    |--- promote "username" : Make user "username" an admin;
                    |--- create table "tableName" "numberOfColumns" : Add a table in the data base;
                    |--- insert column "tableName" "columnName" "typeColumn" : Add a new line in the table with the column name and her type;
                    """;

    private UserTypes userType;
    private String successMessage;

    public Help(UserTypes userType, String[] args) {
        super(args);
        this.userType = userType;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    @Override
    public boolean execute() {

        switch (userType) {
            case ANONYMOUS -> successMessage = helpAnonymous;
            case REGULAR -> successMessage = helpRegular;
            case ADMIN -> successMessage = helpAdmin;
        }
        System.out.println(successMessage); // Afiseaza mesajul de ajutor
        return true;
    }

    @Override
    public List<String> getAllowedTypeUser() {
        List<String> users = new ArrayList<>();
        users.add("ADMIN");
        users.add("ANONYMOUS");
        users.add("REGULAR");
        return users;
    }
}
