package org.example.Commands;

import org.example.Application;
import org.example.Users.AnonymousUser;

import java.util.ArrayList;
import java.util.List;

public class LogoutCommand extends Command {

    public LogoutCommand(String[] args) {
        super(args);
    }

    @Override
    public boolean execute() {
        Application.setuser(new AnonymousUser());
        System.out.println("User has been logged out successfully.");
        return true;
    }

    @Override
    public List<String> getAllowedTypeUser() {
        List<String> users = new ArrayList<>();
        users.add("ADMIN");
        users.add("REGULAR");
        return users;
    }

}
