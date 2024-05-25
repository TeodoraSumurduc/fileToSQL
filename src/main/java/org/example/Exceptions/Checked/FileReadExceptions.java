package org.example.Exceptions.Checked;

import java.io.IOException;

public class FileReadExceptions extends IOException {
    public FileReadExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
