package org.example.Exceptions.Checked;

import java.io.IOException;

/**
 * Excepție aruncată atunci când apare o eroare în timpul citirii dintr-un fișier.
 * Această excepție este o subclasă a IOException și este o excepție verificată.
 */
public class FileReadExceptions extends IOException {
    /**
     * Constructor pentru FileReadExceptions.
     *
     * @param message Mesajul care descrie eroarea.
     * @param cause   Cauza excepției.
     */
    public FileReadExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}

