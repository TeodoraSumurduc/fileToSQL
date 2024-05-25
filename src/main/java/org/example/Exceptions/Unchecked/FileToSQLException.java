package org.example.Exceptions.Unchecked;

public class FileToSQLException extends RuntimeException{
    public FileToSQLException(String message) {
        super(message);
    }
}
