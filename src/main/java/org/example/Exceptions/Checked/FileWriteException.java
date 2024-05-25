package org.example.Exceptions.Checked;

import java.io.IOException;

public class FileWriteException extends IOException {
    public FileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
