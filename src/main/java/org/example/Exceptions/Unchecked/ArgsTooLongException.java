package org.example.Exceptions.Unchecked;

public class ArgsTooLongException extends FileToSQLException {
    public ArgsTooLongException() {
        super("Input is too long. Try again.");
    }
}
