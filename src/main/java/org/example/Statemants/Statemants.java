package org.example.Statemants;

import org.example.Exceptions.Checked.FileReadExceptions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Statemants {
    /**
     * Reads data from a CSV or JSON file and returns it as a list of lists.
     *
     * @return A list of lists containing the data read from the file.
     * @throws FileReadExceptions If an error occurs while reading the file.
     */
    public List<List<String>> readCSVorJSON() throws FileReadExceptions;
    /**
     * Retrieves column details from the database.
     *
     * @return A map representing the column details obtained from the database.
     * @throws SQLException If an SQL error occurs while retrieving the column details.
     */
    public Map<Object, Object> getColumnDetailsFromDatabase() throws SQLException;
}
