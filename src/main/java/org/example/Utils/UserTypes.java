package org.example.Utils;
/**
 * Enumeration representing different types of users.
 */
public enum UserTypes
    {
        ANONYMOUS,
        REGULAR,
                ADMIN;
        /**
         * Maps a string representation of user type to the corresponding enum value.
         *
         * @param userType The string representation of the user type
         * @return The corresponding {@link UserTypes} enum value
         * @throws ClassNotFoundException If the string does not match any known user type
         */
        public static UserTypes mapper (String userType) throws ClassNotFoundException {
        switch (userType) {
            case "ANONYMOUS" -> {
                return ANONYMOUS;
            }
            case "REGULAR" -> {
                return REGULAR;
            }
            case "ADMIN" -> {
                return ADMIN;
            }
            default -> {
                try {
                    throw new ClassNotFoundException();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    }
