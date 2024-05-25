package org.example.Users;

import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Utils.UserTypes;

public class RegularUser extends User {
    public RegularUser(String username, String password) throws DatabaseConnectionException {
        super(username, password);
        setType(UserTypes.REGULAR);
    }
}
