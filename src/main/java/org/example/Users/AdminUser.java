package org.example.Users;

import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Utils.UserTypes;

public class AdminUser extends User {
    public AdminUser(String username, String password) throws DatabaseConnectionException {
        super(username, password);
        setType(UserTypes.ADMIN);
    }
}
