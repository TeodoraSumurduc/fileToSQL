package org.example.Exceptions.Unchecked;

public class DatabaseQueryException extends RuntimeException {
    public DatabaseQueryException(String message) {
        super(message);
    }

    public DatabaseQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
