package org.example.Commands;

import org.example.Exceptions.Checked.DatabaseConnectionException;
import org.example.Exceptions.Checked.FileReadExceptions;
import org.example.Exceptions.Checked.FileWriteException;

import java.sql.SQLException;

public class Invoker {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Rulează comanda asociată.
     *
     * @return true dacă comanda a fost executată cu succes, altfel false.
     */
    public boolean runCommand() throws SQLException, FileWriteException, FileReadExceptions {
        if (command == null) {
            System.out.println("Nu există comandă de executat.");
            return false;
        }

        return command.execute();
    }
}
