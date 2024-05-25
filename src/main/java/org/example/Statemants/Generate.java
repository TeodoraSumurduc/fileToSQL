package org.example.Statemants;

import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * Interface for generating SQL statements.
 */
public interface Generate {
    /**
     * Generates SQL statements based on the provided data.
     *
     * @param list      A map representing the data to be included in the SQL statements.
     * @param tableName The name of the table for which the SQL statements are generated.
     * @param data      A list of lists containing the data to be included in the SQL statements.
     * @return A list of strings representing the generated SQL statements.
     */
    List<String> generateStatements(Map<Object, Object> list, String tableName, List<List<String>> data);

    /**
     * Converts a string to its SQL representation.
     *
     * @param text The text to be converted.
     * @return The SQL representation of the input text.
     */
    String StringInSQL(String text);
}
