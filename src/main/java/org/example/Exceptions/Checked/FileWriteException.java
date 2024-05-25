package org.example.Exceptions.Checked;

import java.io.IOException;

/**
 * Excepție aruncată atunci când apare o eroare în timpul scrierii într-un fișier.
 * Această excepție este o subclasă a IOException și este o excepție verificată.
 */
public class FileWriteException extends IOException {
    /**
     * Constructor pentru FileWriteException.
     *
     * @param message Mesajul care descrie eroarea.
     * @param cause   Cauza excepției.
     */
    public FileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
