package org.example.Exceptions.Checked;

import java.sql.SQLException;

/**
 * Excepție aruncată atunci când apare o eroare în timpul conectării la baza de date.
 * Această excepție este o subclasă a SQLException.
 */
public class DatabaseConnectionException extends SQLException {
    /**
     * Constructor pentru DatabaseConnectionException.
     *
     * @param message Mesajul care descrie eroarea.
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
