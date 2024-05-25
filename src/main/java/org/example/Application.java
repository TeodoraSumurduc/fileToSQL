package org.example;


import org.example.Commands.Command;
import org.example.Commands.Invoker;
import org.example.Commands.QuitCommand;
import org.example.DBManager.DBManager;
import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Exceptions.Checked.FileReadExceptions;
import org.example.Exceptions.Checked.FileWriteException;
import org.example.Exceptions.Unchecked.ArgsTooLongException;
import org.example.Exceptions.Unchecked.FileToSQLException;
import org.example.Exceptions.Unchecked.InvisibleRedoException;
import org.example.Users.AnonymousUser;
import org.example.Users.User;
import org.example.DBManager.Credits;

import javax.naming.NoPermissionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Application {
    private static User user;

    public Application() {
        user = new AnonymousUser();
    }

    public static User getuser() {
        return user;
    }

    public static void setuser(User user) {
        Application.user = user;

    }

    public static void main(String[] args) {
        try {
            new Application().startApp();
        } catch (SQLException e) {
            System.err.println("Failed to initialize the application: " + e.getMessage());
        }
    }

    public static User getUser() {
        return user;
    }

    public void startApp() throws SQLException {
        DBManager dbManager = new DBManager(Credits.getConnectionDB());
        dbManager.createTables();

        startSession();
    }

    private void startSession() throws DatabaseConnectionException {
        setuser(new AnonymousUser());
        listenForCommands(new InputStreamReader(System.in));
    }

    private void listenForCommands(Reader reader) throws DatabaseConnectionException {
        Command command = null;
        BufferedReader buf = new BufferedReader(reader);
        CommandFormat commandFormat = getInputCommand(buf);

        try {
            command = InputConverter.mapCommand(commandFormat);
        } catch (FileToSQLException e) {
            System.out.println(e.getMessage());
        }

        while (!(command instanceof QuitCommand)) {
            try {
                //System.out.println(command.getAllowedTypeUser());
                //System.out.println(getuser().getType());
                if (command != null && command.getAllowedTypeUser().contains(getuser().getType())) {
                    runCommand(command, commandFormat);
                } else {
                    if (command != null && !command.getAllowedTypeUser().contains(getuser().getType())) {
                        saveCommand(getuser(), commandFormat.getOriginalCommand(), false);
                        throw new NoPermissionException();
                    }
                }
            } catch (InvisibleRedoException ignored) {
                command = new QuitCommand(commandFormat.getArgs());
                break;
            } catch (FileToSQLException | DatabaseConnectionException e) {
                System.out.println(e.getMessage());
            } catch (NoPermissionException e) {
                throw new RuntimeException(e);
            }

            try {
                commandFormat = getInputCommand(buf);
                command = InputConverter.mapCommand(commandFormat);
            } catch (FileToSQLException e) {
                System.out.println(e.getMessage());
                command = null;
            }
        }
        runCommand(command, commandFormat);
    }

    private void saveCommand(User user, String command, Boolean flag) throws DatabaseConnectionException {
        DBManager dbManager = new DBManager(Credits.getConnectionDB());
        dbManager.saveCommand(user, command, flag);
    }

    public void runCommand(Command command, CommandFormat commandNameAndArgs) throws DatabaseConnectionException {
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        boolean status = false;
        try {
            status = invoker.runCommand();
        } catch (InvisibleRedoException ignored) {
            throw new InvisibleRedoException();
        } catch (FileToSQLException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error\n" + e);
        } catch (FileWriteException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileReadExceptions e) {
            throw new RuntimeException(e);
        }
        if(status)
        {
            DBManager dbManager = new DBManager(Credits.getConnectionDB());
            dbManager.logCommand(getuser().getUsername(), commandNameAndArgs.getOriginalCommand());
            System.out.println("The command was executed successfully!");
        }

        saveCommand(getuser(), commandNameAndArgs.getOriginalCommand(), status);
    }

    public CommandFormat getInputCommand(BufferedReader bufferedReader) {
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine(), " ");
            String commandString = "";
            if (stringTokenizer.hasMoreTokens()) {
                commandString = stringTokenizer.nextToken();
            }
            if (commandString.length() > 255) {
                throw new ArgsTooLongException();
            }
            String s = "";
            String originalCommand = commandString;

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
            CommandFormat commandFormat = new CommandFormat(commandString, args.toArray(new String[0]));
            commandFormat.setOriginalCommand(originalCommand);
            return commandFormat;
        } catch (IOException e) {
            System.err.println("System error\n" + e);
        }

        return new CommandFormat();
    }

    public static class CommandFormat {
        private String command;
        private String[] args;
        private String originalCommand;

        public CommandFormat() {
        }

        public CommandFormat(String command, String[] args) {
            this.command = command;
            this.args = args;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String[] getArgs() {
            return args;
        }

        public void setArgs(String[] args) {
            this.args = args;
        }

        public String getOriginalCommand() {
            return originalCommand;
        }

        public void setOriginalCommand(String originalCommand) {
            this.originalCommand = originalCommand;
        }
    }
}
