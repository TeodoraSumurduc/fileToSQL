package org.example;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an interface for mapping table classes.
 * It provides methods to retrieve data about the user and the columns of a table.
 */
public interface TableClassesMapper {

    /**
     * Retrieves data about the user.
     *
     * @return A HashMap containing user data
     * @throws SQLException If an SQL exception occurs while retrieving the data
     */
    HashMap<Object, Object> getDataUser() throws SQLException;

    /**
     * Retrieves information about the columns of a table.
     *
     * @return A Map containing column details
     */
    Map<Object, Object> getColumns();
}
