package org.example.Exceptions.Unchecked;

/**
 * Exception thrown to indicate a problem while converting a file operation to a SQL operation.
 * This exception is typically used when an error occurs during the conversion process.
 */
public class FileToSQLException extends RuntimeException {
    /**
     * Constructs a new file to SQL exception with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public FileToSQLException(String message) {
        super(message);
    }
}

