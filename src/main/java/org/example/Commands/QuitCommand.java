package org.example.Commands;

import java.util.ArrayList;
import java.util.List;

public class QuitCommand extends Command {

    public QuitCommand(String[] args) {
        super(args);
    }

    @Override
    public boolean execute() {
        System.out.println("Executing Quit Command");
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
