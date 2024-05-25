package org.example.Users;

import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Utils.UserTypes;

public class UserFactory {
    public static User create(UserTypes userType, String[] credentials)
            throws ClassNotFoundException, DatabaseConnectionException {
        switch (userType) {
            case REGULAR -> {
                return new RegularUser(credentials[0], credentials[1]);
            }
            case ADMIN -> {
                return new AdminUser(credentials[0], credentials[1]);
            }
            case ANONYMOUS -> {
                return new AnonymousUser();
            }
            default -> throw new ClassNotFoundException("Invalid type of user");
        }
    }
}
