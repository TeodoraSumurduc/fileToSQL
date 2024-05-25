package org.example.Users;

import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Utils.UserTypes;
/**
 * Factory class for creating instances of {@link User}.
 */
public class UserFactory {
    /**
     * Creates a user of the specified type with the given credentials.
     *
     * @param userType    The type of user to create
     * @param credentials An array containing the username and password
     * @return The created user instance
     * @throws ClassNotFoundException    If the specified user type is invalid
     * @throws DatabaseConnectionException If a database connection error occurs
     */
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
