package org.example;

import org.example.Commands.AuditCommand;
import org.example.Commands.*;
import org.example.Exceptions.Unchecked.FileToSQLException;
import org.example.Utils.UserTypes;

public class InputConverter {
    public static Command mapCommand(Application.CommandFormat commandFormat) throws FileToSQLException {
        String commandName = commandFormat.getCommand();
        String[] args = commandFormat.getArgs();

        switch (commandName.toLowerCase()) {
            case "insert" ->
            {
                if (args.length > 0 && "column".equals(args[0].toLowerCase())) {
                    return new InsertColumnCommand(args);
                }
            }
            case "create" ->
            {
                if (args.length > 0 && "table".equals(args[0].toLowerCase())) {
                    return new CreateTableCommand(args);
                }
            }
            case "login" ->
            {
                return new LoginCommand(args);
            }
            case "logout" ->
            {
                return new LogoutCommand(args);
            }
            case "help"->
            {
                if (Application.getuser().getType() == "ANONYMOUS") {
                    return new Help(UserTypes.ANONYMOUS, args);
                } else if (Application.getuser().getType() == "REGULAR") {
                    return new Help(UserTypes.REGULAR, args);
                } else if (Application.getuser().getType() == "ADMIN") {
                    return new Help(UserTypes.ADMIN, args);
                }
            }
            case "register" ->
            {
                return new RegisterCommand(args);
            }
            case "load" ->
            {
                return new LoadCommand(args);
            }
            case "audit" ->
            {
                return new AuditCommand(args);
            }
            case "quit" ->
            {
                return new QuitCommand(args);
            }
            case "execute" ->
            {
                return new ExecuteCommand(args);
            }
            case "promote" ->
            {
                return new PromoteCommand(args);
            }
            default ->
                throw new FileToSQLException("Unknown command: " + commandName);

        }
        return null;
    }
}
