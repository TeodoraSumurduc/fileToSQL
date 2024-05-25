package org.example.Exceptions.Unchecked;

/**
 * Exception thrown to indicate a problem with executing a database query.
 * This exception is typically used to wrap SQL exceptions or other database-related errors.
 */
public class DatabaseQueryException extends RuntimeException {
    /**
     * Constructs a new database query exception with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public DatabaseQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}

