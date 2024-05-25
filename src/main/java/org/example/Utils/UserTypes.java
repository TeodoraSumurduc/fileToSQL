package org.example.Utils;

public enum UserTypes
    {
        ANONYMOUS,
        REGULAR,
                ADMIN;

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
