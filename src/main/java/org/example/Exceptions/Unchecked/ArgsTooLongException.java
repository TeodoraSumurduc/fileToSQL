package org.example.Exceptions.Unchecked;

/**
 * Excepție aruncată atunci când lungimea argumentelor depășește limita permisă.
 * Această excepție este o subclasă a FileToSQLException și este o excepție verificată.
 */
public class ArgsTooLongException extends FileToSQLException {
    /**
     * Constructor pentru ArgsTooLongException.
     */
    public ArgsTooLongException() {
        super("Input is too long. Try again.");
    }
}
