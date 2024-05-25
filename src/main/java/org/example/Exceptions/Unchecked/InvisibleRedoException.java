package org.example.Exceptions.Unchecked;

/**
 * Exception thrown to indicate a problem with an invisible redo operation during file to SQL conversion.
 * This exception is typically used when an invisible redo operation is encountered.
 */
public class InvisibleRedoException extends FileToSQLException {
    /**
     * Constructs a new invisible redo exception.
     */
    public InvisibleRedoException() {
        super("");
    }
}

